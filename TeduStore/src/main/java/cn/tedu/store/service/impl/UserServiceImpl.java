package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserMapper userMapper;

	public User login(String username, String password) throws UserNotFoundException,PasswordNotMatchException{
		//根据用户名查询用户信息
		User user = getUserByUsername(username);
		//判断时否查询到用户信息
		if(user!=null) {
			//是：用户名有匹配的数据，即用户正确
			String salt = user.getSalt();
			//对用户输入的密码执行加密
			String md5Password = getEncrpytePassword(password, salt);
			//判断以上加密密码与数据库的是否匹配
			if(user.getPassword().equals(md5Password)) {
				user.setPassword(null);
				user.setSalt(null);
				return user;
			}else {
				throw new PasswordNotMatchException("你输入的密码错误");
			}
		}else {
			//否：没有用户名匹配的数据，即用户输入错误，抛出异常
			throw new UserNotFoundException("您输入的用户名不存在");
		}
	}

	public User reg(User user) throws UsernameConflictException,InsertDataException{
		// 根据用户名查询用户信息3
		User result = getUserByUsername(user.getUsername());
		// 判断是否查询到数据
		if (result == null) {
			// 否：用户名可用
			insert(user);
			return user;
		} else {
			// 是：用户名已经被占用，抛出UserConflictException异常
			throw new UsernameConflictException("您注册的用户名(" + user.getUsername() + ")已被占用");
		}
	}
	
	public void changePasswordByOldPassword(Integer id, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateDataException {
		User user = getUserById(id);
		if(user!=null) {
			String salt = user.getSalt();
			String md5Password = getEncrpytePassword(oldPassword, salt);
			if(user.getPassword().equals(md5Password)) {
				String md5NewPassword = getEncrpytePassword(newPassword, salt);
				changePassword(id, md5NewPassword);
			}else {
				throw new PasswordNotMatchException("您输入的原密码错误");
			}
		}else {
			throw new UserNotFoundException("尝试访问的用户数据不存在");
		}
		
	}

	public void changePassword(Integer id,String password) {
		//调用持久成对象的同名方法实现修改
		Integer rows = userMapper.changePassword(id, password);
		
		if(rows != 1) {
			throw new UpdateDataException("修改密码时出现未知错误！请联系系统管理员！");
		}
	}
	
	public void insert(User user) throws InsertDataException{
		//TODO 加密密码
		String password = user.getUsername();
		String salt = getRandomSalt();
		String md5Password = getEncrpytePassword(password, salt);
		user.setSalt(salt);
		user.setPassword(md5Password);
		//为用户没有提交的属性添加值
		user.setStatus(1);
		user.setIsDelete(0);
		//设置用户的数据的日志
		Date now = new Date();
		user.setCreatedUser(user.getUsername());
		user.setCreatedTime(now);
		user.setModifiedUser(user.getUsername());
		user.setModifiedTime(now);
		// 执行注册
		Integer rows = userMapper.insert(user);
		if(rows!=1) {
			throw new InsertDataException("注册时发生未知错误！请联系系统管理员！");
		}
	}
	public void changeInfo(User user) throws UserNotFoundException,UpdateDataException{
		User result = getUserById(user.getId());
		if(result!=null) {
			//补充日志，即：modifiedUser和modifiedTime
			user.setModifiedUser(user.getUsername()==null?result.getUsername():user.getUsername());
			user.setModifiedTime(new Date());
			Integer rows = userMapper.changeInfo(user);
			if(rows!=1) {
				throw new UpdateDataException("修改用户信息时发生未知错误！请联系系统管理员！");
			}
		}else {
			throw new UserNotFoundException("尝试访问的用户不存在！");
		}
		
	}
	public User getUserById(Integer id) {
		return userMapper.getUserById(id);
	}

	public User getUserByUsername(String username) {
		
		return userMapper.getUserByUsername(username);
	}
	
	/**
	 * 获取随机的盐值
	 * @return 盐值
	 */
	private String getRandomSalt() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	/**
	 * 获取加密后的密码
	 * @param src 原始密码
	 * @param salt 盐
	 * @return 加密后的密码
	 * @see md5(String)
	 */
	private String getEncrpytePassword(String src,String salt) {
		//将原密码加密
		String s1 = md5(src);
		//将盐加密
		String s2 = md5(salt);
		//将2此加密结果拼接，再加密
		String s3 = s1+s2;
		String result = md5(s3);
		//将以上结果再加密5次
		for(int i=0;i<5;i++) {
			result = md5(result);
		}
		return result;
	}
	
	/**
	 * 使用MD5算法对数据进行加密
	 * @param src 原文
	 * @return 密文
	 */
	private String md5(String src) {
		return DigestUtils.md5DigestAsHex(src.getBytes()).toUpperCase();
	}
}
