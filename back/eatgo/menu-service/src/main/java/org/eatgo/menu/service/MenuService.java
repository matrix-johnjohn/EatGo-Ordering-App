package org.eatgo.menu.service;

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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuService {
    public List<DishCategorize> cateList();//分类列表

    public List<DishTag> selectTagsByCateId(DishCategorize dishCategorize);//标签列表

    public List<Dish> dishListByCateAndTag(DishQuery dishQuery);

    public void plusCount(CollectionQuery collectionQuery);

    public void minusCount(CollectionQuery collectionQuery);

    public List<Dish> dishesListByids(List<Integer>list);

    public List<Dish> recommandList(PageQuery pageQuery);

    public Dish findById(Integer id);

    public List<Dish> dishList();


    /*
    后台管理
    */
    // 分类管理
    public void deleteDishCateById(DishCategorize dishCategorize);//删除分类

    public void deleteDishCateByIds(List<Integer> ids);//批量删除分类

    public void addCate(String name, MultipartFile icon,MultipartFile []banner);//添加分类

    public void removeBanner(DishCategorize dishCategorize,Integer index);//删除轮播

    public void updateCate(DishCategorize dishCategorize,MultipartFile icon,MultipartFile []banner);// 更新分类

    public List<DishCategorize> searchCateList(String subString);// 搜索分类

    // 标签管理
    public List<DishTagVo> DishTagVoList();

    public List<DishTagVo> SearchDishTagVoList(String subString,Integer cateId);

    public void insertDishTag(String name,Integer cateId);

    public void deleteDishTagById(Integer tagId);

    public void BatchDeleteDishTag(List<Integer> ids);

    public void updateDishTagById(UpdateDishTagQuery query);

    /**
    * 菜品管理
    * */
    // 菜品数据
    public List<DishVo> dishVoList();
    // 菜品搜索
    public List<DishVo> searchDishVoList(DishSearchForm form);
    // 添加菜品
    public void addDish(MultipartFile dishImg,DishDto dto);
    // 删除菜品
    public void deleteDish(DishVo vo);
    // 批量删除数据
    public void BatchDeleteDish(List<DishVo> dishList);
    // 更新菜品
    public void updateDish(MultipartFile file,DishVo vo);
}
