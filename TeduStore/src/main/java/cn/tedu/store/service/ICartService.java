package cn.tedu.store.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;

public interface ICartService {
	
	/**
	 * 每页显示的数据的数量
	 */
	Integer COUNT_PER_PAGE = 5;
	
	/**
	 * 添加到购物车
	 * @param cart 购物车数据
	 */
	void addToCart(Cart cart);
	
	/**
	 * 根据用户和商品id查询购物车中的数据量
	 * @param uid 用户id
	 * @param goodsId 商品id
	 * @return 匹配的数据的数量
	 */
	Integer getCountByUidAndGoodsId(
			Integer uid, Long goodsId);
	
	/**
	 * 修改购物车中商品的数量
	 * @param num 增量
	 * @param uid 用户id
	 * @param goodsId 商品id
	 * @return 受影响的行数
	 */
	void changeGoodsNum(
			Integer num, Integer uid, Long goodsId);
	
	/**
	 * 获取购物车数据列表
	 * @param uid 用户id
	 * @param page 页数
	 * @return 购物车数据列表
	 */
	List<Cart> getList(Integer uid, Integer page);

	/**
	 * 获取某用户的购物车数据的数量
	 * @param uid 用户的id
	 * @return 用户的购物车数据的数量
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * 获取某用户的购物车的最大页数
	 * @param uid
	 * @return
	 */
	Integer getMaxPage(Integer uid);
	
	/**
	 * 获取某个用户的购物车中所有商品
	 * @param uid
	 * @param ids
	 * @return
	 */
	List<Cart> getListByIds(@Param("uid") Integer uid,@Param("ids") Integer[] ids);
}





