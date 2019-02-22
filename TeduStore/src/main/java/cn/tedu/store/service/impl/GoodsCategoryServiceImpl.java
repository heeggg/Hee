package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.GoodsCateGory;
import cn.tedu.store.mapper.GoodsCategoryMapper;
import cn.tedu.store.service.IGoodsCategoryService;

@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl implements IGoodsCategoryService {
	
	@Autowired
	private GoodsCategoryMapper goodsCategodyMapper;
	
	public List<GoodsCateGory> getListByParent(Long id) {
		return goodsCategodyMapper.getListByParent(id);
	}

}
