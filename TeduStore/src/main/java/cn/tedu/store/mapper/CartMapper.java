package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;

public interface CartMapper {
	
	/**
	 * 加入购物车功能
	 * @param cart 商品信息
	 * @return 受影响的条数
	 */
	Integer insert(Cart cart);
	
	/**
	 * 根据用户uid和商品id获取购物车的数据量
	 * @param uid 用户id
	 * @param goodsId 商品id
	 * @return 匹配的数据数量
	 */
	Integer getCountByUidAndGoodsId(@Param("uid") Integer uid,@Param("goodsId") Long goodId);
	
	/**
	 * 修改购物车中商品的数量
	 * @param num 商品数量
	 * @param uid 用户id
	 * @param goodsId 商品id
	 * @return 受影响的条数
	 */
	Integer changeGoodsNum(@Param("num")Integer num,@Param("uid")Integer uid,@Param("goodsId")Long goodId);
	
	/**
	 * 获取购物车数据列表
	 * @param uid 用户id
	 * @param offset 跳过多少条数据
	 * @param count 获取多少条数据
	 * @return 购物车数据列表
	 */
	List<Cart> getList(
			@Param("uid") Integer uid, 
			@Param("offset") Integer offset, 
			@Param("count") Integer count);
	
	/**
	 * 获取某用户的购物车数据的数量
	 * @param uid 用户的id
	 * @return 用户的购物车数据的数量
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * 获取某个用户的购物车中所有商品
	 * @param uid
	 * @param ids
	 * @return
	 */
	List<Cart> getListByIds(@Param("uid") Integer uid,@Param("ids") Integer[] ids);
}
