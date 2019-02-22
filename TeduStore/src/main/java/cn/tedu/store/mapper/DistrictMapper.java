package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

public interface DistrictMapper {
	
	/**
	 * 省的父级代号
	 */
	String PROVINCE_PARENT = "86";
	
	/**
	 * 获取省市区的列表
	 * @param parent 父级的代号：例如获取市的列表应该使用省的代号 如获取省的列表，该使用 #PROVINCE_PARENT 作为代号
	 * @return 
	 * @see #PROVINCE_PARENT
	 */
	List<District> getList(String parent);
	
	/**
	 * 获取某个省市区的详细信息
	 * @param code
	 * @return 获取某个省市区的信息，如果有匹配的则返回，否则返回null
	 */
	District getInfo(String code);
}
