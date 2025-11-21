package org.eatgo.menu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.dto.DishDto;
import org.eatgo.common.domain.form.DishSearchForm;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.common.domain.query.PageQuery;
import org.eatgo.common.domain.query.UpdateDishTagQuery;
import org.eatgo.common.domain.vo.DishTagVo;
import org.eatgo.common.domain.vo.DishVo;
import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.eatgo.menu.util.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    private final JedisPool jedisPool;

    private final MinioUtil minioUtil;

    @Override
    public List<DishCategorize> cateList() {//获取分类列表
        return menuMapper.cateList();
    }

    @Override
    public List<DishTag> selectTagsByCateId(DishCategorize dishCategorize) {//根据分类Id获取标签列表
        return menuMapper.tagList(dishCategorize);
    }

    @Override
    public List<Dish> dishListByCateAndTag(DishQuery dishQuery) {//首页菜品列表是数据
        return menuMapper.dishList(dishQuery);
    }

    @Override
    public void plusCount(CollectionQuery collectionQuery) {
        menuMapper.plusCount(collectionQuery);
    }

    @Override
    public void minusCount(CollectionQuery collectionQuery) {
        menuMapper.minusCount(collectionQuery);
    }

    @Override
    public List<Dish>dishesListByids(List<Integer> list) {
        return menuMapper.dishesByIds(list);
    }

    @Override
    public List<Dish> recommandList(PageQuery pageQuery) {

        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());

        List<Dish>dishes=menuMapper.recommendList();

        Page<Dish> pageData=(Page<Dish>)(dishes);

        return pageData.getResult();
    }

    @Override
    public Dish findById(Integer dishId) {
        return menuMapper.getDishById(dishId);
    }

    @Override
    public List<Dish> dishList() {
        return menuMapper.dishList(new DishQuery());
    }

    @Override
    public void deleteDishCateById(DishCategorize dishCategorize) {
        // 数据库删除数据
        menuMapper.deleteDishCateById(dishCategorize);

        // 数据库缓存删除数据
        Jedis jedis = jedisPool.getResource();
        jedis.del("cate:dish:"+dishCategorize.getId());
        jedis.close();
    }

    @Override
    public void deleteDishCateByIds(List<Integer> ids) {
        // 数据库删除数据
        menuMapper.deleteDishCateByIds(ids);

        // 数据库缓存删除数据
        Jedis jedis = jedisPool.getResource();
        for(Integer id : ids){
            jedis.del("cate:dish:"+(id));
        }
        jedis.close();
    }

    @Override
    public void addCate(String name, MultipartFile icon,MultipartFile []banner) {
        // 上传icon
        String iconPath=uploadIcon(icon);

        // 上传banner
        String bannerPath=uploadBanner(banner);

        // 数据拼接
        DishCategorize dishCategorize=new DishCategorize();
        dishCategorize.setName(name);
        dishCategorize.setIcon(iconPath);
        dishCategorize.setBanner(bannerPath);

        // 写入数据库
        menuMapper.addCate(dishCategorize);

        // 写入数据库缓存
        Jedis jedis=jedisPool.getResource();
        DishCategorize dishCate = menuMapper.getDishCategoryByName(name);
        jedis.set("cate:dish:"+dishCate.getId(),JSONUtil.toJsonStr(dishCate));
        jedis.close();
    }

    @Override
    public void removeBanner(DishCategorize dishCategorize, Integer index) {
        String bannerListJSON=dishCategorize.getBanner();

        List<String>bannerList=JSONUtil.toList(bannerListJSON, String.class);

        bannerList.remove(index.intValue());

        String result=JSONUtil.toJsonStr(bannerList);

        dishCategorize.setBanner(result);

        menuMapper.updateCateBanner(dishCategorize);
    }

    @Override
    public void updateCate(DishCategorize dishCategorize, MultipartFile icon,MultipartFile []banner) {
        // 要上传icon
        if (dishCategorize.getIcon().isEmpty()){
            String iconPath=uploadIcon(icon);

            dishCategorize.setIcon(iconPath);
        }
        // 要上传banner
        if(!ObjectUtil.isEmpty(banner)){
            String bannerPathJSON=uploadBanner(banner);

            String bannerListJSON=dishCategorize.getBanner();

            // 初始数据
            List<String> bannerList=JSONUtil.toList(bannerListJSON, String.class);

            // 需要添加的数据
            List<String> supplementData = JSONUtil.toList(bannerPathJSON, String.class);

            // Ryan Supplement Explain:数据量不可能超过220MB,此处可以直接暴力
            bannerList.addAll(supplementData);

            String result=JSONUtil.toJsonStr(bannerList);

            dishCategorize.setBanner(result);
        }

        // 数据库缓存写入数据
        Jedis jedis=jedisPool.getResource();

        jedis.set("cate:dish:"+dishCategorize.getId(),JSONUtil.toJsonStr(dishCategorize));

        jedis.close();

        // 数据库写入数据
        menuMapper.updateCate(dishCategorize);

    }

    @Override
    public List<DishCategorize> searchCateList(String subString) {
        return menuMapper.searchCateList(subString);
    }

    public String uploadIcon(MultipartFile icon) {
        // icon基础存储路径
        String iconBasePath="http://192.168.174.130:9000/eatgo/cate/icon/";

        String iconPath=UUID.randomUUID().toString().substring(0, 10)+"/"+icon.getOriginalFilename();

        // 图片上传至服务器对象存储中
        minioUtil.upload(icon,"/cate/icon/"+iconPath);

        // 拼接完整插入数据,写入数据库中;
        return (iconBasePath+iconPath);
    }

    public String uploadBanner(MultipartFile[] banner) {
        ArrayList<String>list=new ArrayList<>();
        // 上传海报列表
        String BaseBannerPath="http://192.168.174.130:9000/eatgo";

        String supplementPath="/cate/banner/"+UUID.randomUUID().toString().substring(0, 10)+"/";

        // 批量上传banner处理
        for(MultipartFile b:banner){
            minioUtil.upload(b,supplementPath+b.getOriginalFilename());//上传
            list.add(BaseBannerPath+supplementPath+b.getOriginalFilename());
        }

        return JSONUtil.toJsonStr(list);
    }

    @Override
    public List<DishTagVo> DishTagVoList() {
        // 数据库读取分类信息
        List<DishTagVo>dishTag=menuMapper.DishTagList();

        Jedis jedis=jedisPool.getResource();

        for (DishTagVo dishTagVo : dishTag){
            // 数据库缓存根据分类id获取分类名
            String dishCateJSON=jedis.get("cate:dish:" + dishTagVo.getCategorizeId());
            DishCategorize dishCate=JSONUtil.toBean(dishCateJSON, DishCategorize.class);
            dishTagVo.setCateName(dishCate.getName());
        }

        dishTag.forEach(System.out::println);

        // 回收jedis
        jedis.close();

        return dishTag;
    }

    @Override
    public List<DishTagVo> SearchDishTagVoList(String subString,Integer cateId) {
        List<DishTagVo>dishTag=menuMapper.SearchDishTagList(subString,cateId);

        Jedis jedis=jedisPool.getResource();

        for (DishTagVo dishTagVo : dishTag){
            // 数据库缓存根据分类id获取分类名
            String dishCateJSON=jedis.get("cate:dish:" + dishTagVo.getCategorizeId());
            DishCategorize dishCate=JSONUtil.toBean(dishCateJSON, DishCategorize.class);
            dishTagVo.setCateName(dishCate.getName());
        }

        dishTag.forEach(System.out::println);

        jedis.close();

        return dishTag;
    }

    @Override
    public void insertDishTag(String name, Integer cateId) {
        // 数据库写入数据
        menuMapper.insertDishTag(name,cateId);

        // 数据库缓存写入数据
        Jedis jedis=jedisPool.getResource();

        DishTagVo tag=menuMapper.getDishTagById(name);

        String key="cate:dish:"+cateId;

        String json=jedis.get(key);

        DishCategorize dishCate=JSONUtil.toBean(json, DishCategorize.class);

        tag.setCateName(dishCate.getName());

        String dataStr=JSONUtil.toJsonStr(tag);

        jedis.set("tag:dish:"+tag.getId(), dataStr);

        jedis.close();
    }

    @Override
    public void deleteDishTagById(Integer tagId) {
        Jedis jedis=jedisPool.getResource();
        if(!ObjectUtil.isEmpty(tagId)){
            // 数据库删除数据逻辑
            menuMapper.deleteDishTagById(tagId);
            // 数据库缓存删除数据逻辑
            jedis.del("tag:dish:"+tagId);
        }
        jedis.close();
    }

    @Override
    public void BatchDeleteDishTag(List<Integer> ids) {
        menuMapper.BatchDeleteDishTagByIds(ids);
    }

    @Override
    public void updateDishTagById(UpdateDishTagQuery query) {
        // 数据库写入数据
        menuMapper.updateDishTagById(query);

        // 数据库缓存写入数据
        Jedis jedis=jedisPool.getResource();

        // 获取当前标签缓存数据
        String DishTagDataJSON=jedis.get("tag:dish:" + query.getId());

        String DishCateDataJSON=jedis.get("cate:dish:" + query.getCateId());

        DishTagVo DishTagData=JSONUtil.toBean(DishTagDataJSON, DishTagVo.class);

        DishCategorize DishCateData=JSONUtil.toBean(DishCateDataJSON, DishCategorize.class);

        DishTagData.setName(query.getName()); //修改标签名称

        DishTagData.setCategorizeId(query.getCateId()); //修改分类

        DishTagData.setCateName(DishCateData.getName()); //修改分类名

        String jsonStr=JSONUtil.toJsonStr(DishTagData);

        jedis.set("tag:dish:"+query.getId(), jsonStr);

        jedis.close();
    }

    @Override
    public List<DishVo> dishVoList() {
        List<DishVo> dishList=menuMapper.dishDetailList();

        Jedis jedis=jedisPool.getResource();

        for(DishVo dishVo:dishList){
            Integer cateId=dishVo.getCategorizeId();
            Integer tagId=dishVo.getTagId();

            List<String>keys=jedis.mget("cate:dish:"+cateId, "tag:dish:"+tagId);

            DishTag dishTag=JSONUtil.toBean(keys.get(1), DishTag.class);
            String tagName=dishTag.getName();
            DishCategorize dishCate=JSONUtil.toBean(keys.get(0), DishCategorize.class);
            String cateName=dishCate.getName();

            dishVo.setCateName(cateName);
            dishVo.setTagName(tagName);
        }
        jedis.close();
        return dishList;
    }

    @Override
    public List<DishVo> searchDishVoList(DishSearchForm form) {

        List<DishVo>dishList=menuMapper.searchDishDetailList(form);

        Jedis jedis=jedisPool.getResource();

        for(DishVo dishVo:dishList){
            Integer cateId=dishVo.getCategorizeId();
            Integer tagId=dishVo.getTagId();

            List<String>keys=jedis.mget("cate:dish:"+cateId, "tag:dish:"+tagId);

            DishTag dishTag=JSONUtil.toBean(keys.get(1), DishTag.class);
            String tagName=dishTag.getName();
            DishCategorize dishCate=JSONUtil.toBean(keys.get(0), DishCategorize.class);
            String cateName=dishCate.getName();

            dishVo.setCateName(cateName);
            dishVo.setTagName(tagName);
        }

        jedis.close();

        return dishList;
    }

    @Override
    public void addDish(MultipartFile dishImg,DishDto dto){

        String path=uploadDish(dishImg);
        dto.setImage(path);
        // 数据库写入数据
        menuMapper.insertDish(dto);

        // 数据库缓存写入数据
        Jedis jedis=jedisPool.getResource();
        Dish dish=menuMapper.getDishByTitle(dto.getTitle());
        String dishJSON=JSONUtil.toJsonStr(dish);
        jedis.set("dish:dish:"+dish.getId(), dishJSON);
        jedis.close();
    }

    @Override
    public void deleteDish(DishVo vo) {

        String[] path=vo.getImage().split("/eatgo");

        // 数据库删除数据
        menuMapper.deleteDishById(vo);

        // 数据库缓存删除数据
        Jedis jedis=jedisPool.getResource();
        jedis.del("dish:dish:"+vo.getId());
        jedis.close();
        // 删除oss中的图片
        minioUtil.removeObject(path[1]);
    }

    @Override
    public void BatchDeleteDish(List<DishVo> dishList) {
        // 主键列表
        List<Integer>ids=dishList.stream().map(DishVo::getId).collect(Collectors.toList());

        List<String>imgList=dishList.stream().map(DishVo::getImage).toList();

        // 数据库数据删除
        menuMapper.BatchDeleteDishByIds(ids);

        Jedis jedis=jedisPool.getResource();
        // 数据库缓存删除数据
        for(Integer id:ids){
            jedis.del("dish:dish:"+id);
        }
        jedis.close();

        // 对象存储数据删除
        for(String path:imgList){
            minioUtil.removeObject(path.split("/eatgo")[1]);
        }
    }

    @Override
    public void updateDish(MultipartFile file, DishVo vo) {
        System.out.println(vo);

        // 上传文件
        if(ObjectUtil.isNotNull(file)){
            String path=uploadDish(file);
            vo.setImage(path);
        }

        // 写入数据库
        menuMapper.updateDish(vo);


        Jedis jedis=jedisPool.getResource();

        // 操作对象存储
        String element=jedis.get("dish:dish:" + vo.getId());

        DishVo currentEdit=JSONUtil.toBean(element, DishVo.class);

        String image=currentEdit.getImage();

        String delPath=image.split("/eatgo")[1];

        minioUtil.removeObject(delPath);

        // 写入数据库缓存
        jedis.set("dish:dish:"+vo.getId(),JSONUtil.toJsonStr(vo));

        jedis.close();
    }

    public String uploadDish(MultipartFile dishImg){
        String result= "http://192.168.174.130:9000/eatgo/dish/";

        String key=UUID.randomUUID().toString().substring(0, 8);

        String uploadPath="/dish/"+key+"/"+dishImg.getOriginalFilename();

        minioUtil.upload(dishImg,uploadPath);

        result+=key+"/"+dishImg.getOriginalFilename();

        return result;
    }
}
