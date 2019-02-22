package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

public interface AddressMapper {
	/**
	 * 插入收货地址数据
	 * @param address 收货地址数据
	 * @return 受影响的行数
	 */
	Integer insert(Address address);
	
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

	/**
	 * 将用户的所有收货地址设置为非默认收货地址
	 * @param uid 用户id
	 * @return 受影响的行数
	 */
	Integer setNonDefault(Integer uid);
	
	/**
	 * 将用户的收货地址设置为默认收货地址
	 * @param uid 用户id
	 * @param id 地址的id
	 * @return 受影响的行数
	 */
	Integer setDefalut(@Param("uid") Integer uid,@Param("id") Integer id);
	
	/**
	 * 删除用户收货地址
	 * @param id 收货地址id
	 * @return
	 */
	Integer delete(Integer id);
	
}
