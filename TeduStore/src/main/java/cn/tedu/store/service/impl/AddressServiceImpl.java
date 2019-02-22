package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.ArgumentException;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.UpdateDataException;

@Service("addressService")
public class AddressServiceImpl implements IAddressService {

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private IDistrictService districtService;

	public Address addnew(String currentUser, Address address) throws InsertDataException {
		String recvDistrict = getDistrictName(address);
		address.setRecvDistrict(recvDistrict);
		// 业务：当前尝试添加的时该用户的第一条收货地址
		Integer count = getCountByUid(address.getUid());
		address.setIsDefault(count == 0 ? 1 : 0);
		insert(currentUser, address);
		// 执行
		return address;
	}

	@Transactional
	public void setDefaultAddress(Integer uid, Integer id) {
		// 将用户收货地址全部设置为非默认收货地址
		setNonDefault(uid);
		// 设置用户的默认收货地址
		setDefault(uid, id);
	}

	@Transactional
	public void deleteById(Integer uid, Integer id)
			throws AddressNotFoundException, ArgumentException, UpdateDataException {
		Address address = getAddressById(id);
		// 判断是否查询到数据
		if (address != null) {
			if (address.getUid().equals(uid)) {
				delete(id);
				//判断删除的数据的isDefault是否为1
				if (address.getIsDefault() == 1) {
					// -- -- 是：删除了默认收货地址，则查询最后添加的收货地址
					Address latestAddress = getLatestAddress(uid);
					// -- -- 判断是否查询到结果
					if (latestAddress != null) {
						// 是：获取数据id，调用setDefault(uid, id)
						setDefault(uid, latestAddress.getId());
					}
				}
			} else {
				//否：尝试删除的不是自己的数据：ArgumentException
				throw new ArgumentException("参数错误");
			}
		} else {
			// 否：数据不存在，AddressNotFoundException
			throw new AddressNotFoundException("尝试删除的数据不存在！");
		}
	}

	public Address getAddressById(Integer id) {
		return addressMapper.getAddressById(id);
	}

	public Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);
	}

	public List<Address> getList(Integer uid) {
		return addressMapper.getList(uid);
	}

	public Address getLatestAddress(Integer uid) {
		return addressMapper.getLatestAddress(uid);
	}

	/**
	 * 插入收货地址数据
	 * 
	 * @param address
	 *            收货地址数据
	 * @throws InsertDataException
	 */
	private void insert(String currentUser, Address address) throws InsertDataException {
		// 添加日志数据
		Date now = new Date();
		address.setCreatedUser(currentUser);
		address.setCreatedTime(now);
		address.setModifiedUser(currentUser);
		address.setModifiedTime(now);
		// 执行插入
		Integer rows = addressMapper.insert(address);
		// 判断是否出错
		if (rows != 1) {
			throw new InsertDataException("增加收货地址时出现未知错误！请联系系统管理员！");
		}
	}

	/**
	 * 将用户的所有收货地址设置为非默认收货地址
	 * 
	 * @param uid
	 *            用户id
	 * @return 受影响的行数
	 */
	private Integer setNonDefault(Integer uid) {
		Integer rows = addressMapper.setNonDefault(uid);
		if (rows == 0) {
			throw new UpdateDataException("尝试更新收货地址失败，用户数据参数错误！");
		}
		return rows;
	}

	/**
	 * 将用户的收货地址设置为默认收货地址
	 * 
	 * @param uid
	 *            用户id
	 * @param id
	 *            地址的id
	 * @return 受影响的行数
	 */
	private Integer setDefault(Integer uid, Integer id) {
		
		Integer rows = addressMapper.setDefalut(uid, id);
		if (rows == 0) {
			throw new UpdateDataException("更新收货地址数据发生未知错误！请联系系统管理员！");
		}
		return rows;
	}

	/**
	 * 删除用户指定收货地址
	 * 
	 * @param id
	 *            收货地址id
	 */
	private void delete(Integer id) {
		Integer rows = addressMapper.delete(id);
		if (rows == 0) {
			throw new AddressNotFoundException("删除数据时出现未知错误！请联系系统管理员！");
		}
	}

	/**
	 * 获取省 市 区的名称
	 * 
	 * @param address
	 *            封装了省市区的代号的对象
	 * @return 省市区的名称，例如：河北省石家庄XX区
	 */
	private String getDistrictName(Address address) {
		// 业务：根据省市区的代号，得到他们的名称
		District province = districtService.getInfo(address.getRecvProvince());
		District city = districtService.getInfo(address.getRecvCity());
		District area = districtService.getInfo(address.getRecvArea());
		String recvDistrict = province.getName() + city.getName() + area.getName();
		return recvDistrict;
	}
}
