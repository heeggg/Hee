package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.ArgumentException;
import cn.tedu.store.service.ex.InsertDataException;

public interface IAddressService {
	
	/**
	 * 添加新的收货地址信息
	 * @param address 收货地址信息
	 * @return 成功添加的收货地址信息，包括id
	 * @throws InsertDataException
	 */
	Address addnew(String currentUser,Address address) throws InsertDataException ;
	
	/**
	 * 根据当前用户id查询该用户的收货地址的数量
	 * @param uid 当前用户id
	 * @return 受影响的行数
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * 根据用户id查询用户所有收货地址
	 * @param uid 用户id
	 * @return 用户的所有收货地址
	 */
	List<Address> getList(Integer uid);
	
	/**
	 * 设置用户的某条收货地址为默认收货地址
	 * @param uid 用户id
	 * @param id 被设置为默认收货地址的id
	 */
	void setDefaultAddress(Integer uid,Integer id);
	
	/**
	 * 根据当前用户删除地址信息
	 * @param uid 用户id
	 * @param id 地址id
	 * @throws AddressNotFoundException 地址不存在
	 * @throws ArgumentException 地址不匹配
	 */
	void deleteById(Integer uid,Integer id) throws AddressNotFoundException,ArgumentException;
	
	/**
	 * 根据地址id查询地址信息
	 * @param id 地址id
	 * @return 地址信息
	 */
	Address getAddressById(Integer id);
	
	/**
	 * 根据用户id查询用户收货地址
	 * @param uid 用户id
	 * @return 用户信息
	 */
	Address getLatestAddress(Integer uid);
	
}
