package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.GoodsCateGory;

public interface GoodsCategoryMapper {
	/**
	 * 根据父级分类，获取分类列表
	 * @param id 商品id
	 * @return 子级分类列表
	 */
	List<GoodsCateGory> getListByParent(Long id);
}
