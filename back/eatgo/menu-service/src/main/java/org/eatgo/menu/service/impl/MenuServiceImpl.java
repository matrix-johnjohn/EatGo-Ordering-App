package org.eatgo.menu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.eatgo.common.domain.po.Dish;
import org.eatgo.common.domain.po.DishCategorize;
import org.eatgo.common.domain.po.DishTag;
import org.eatgo.common.domain.query.CollectionQuery;
import org.eatgo.common.domain.query.DishQuery;
import org.eatgo.common.domain.query.PageQuery;
import org.eatgo.menu.mapper.MenuMapper;
import org.eatgo.menu.service.MenuService;
import org.eatgo.menu.util.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

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
        menuMapper.deleteDishCateById(dishCategorize);
    }

    @Override
    public void deleteDishCateByIds(List<Integer> ids) {
        menuMapper.deleteDishCateByIds(ids);
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

            bannerList.addAll(supplementData);

            String result=JSONUtil.toJsonStr(bannerList);

            dishCategorize.setBanner(result);
        }

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
}
