package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.UploadAvatarException;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private IUserService userService;

	@RequestMapping(value="/handle_reg.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleReg(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			String email,String phone,
			@RequestParam(value="gender",required=false,defaultValue="1") Integer gender) {

		User user = new User(username,password,email,phone,gender);
		userService.reg(user);
		return new ResponseResult<Void>();
	}

	@RequestMapping(value="/handle_login.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleLogin(@RequestParam("username")String username,@RequestParam("password")String password,
			HttpSession session) {
		User user = userService.login(username, password);
		session.setAttribute("uid", user.getId());
		session.setAttribute("username",user.getUsername());
		return new ResponseResult<Void>();
	}

	@RequestMapping(value="/handle_change_password.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleChangePassword(@RequestParam("old_password") String oldPassword,@RequestParam("new_password") String newPassword,HttpSession session) {
		Integer id = getUidFormSession(session);
		userService.changePasswordByOldPassword(id, oldPassword, newPassword);
		return new ResponseResult<Void>();
	}

	@RequestMapping(value="/handle_change_info.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> handleChangeInfo(User user,HttpSession session,@RequestParam CommonsMultipartFile avatarfile,HttpServletRequest request) {
		if("".equals(user.getUsername())) {
			user.setUsername(null);
		}
		if("".equals(user.getEmail())) {
			user.setEmail(null);
		}
		//**处理上传的头像**
		//上传头像，并获取上传后的路径
		//判断上传的头像是否为非空
		if(!avatarfile.isEmpty()) {
			//把头像文件的路径封装，以写入到数据表中
			String avatarPath = uploadAvatar(request, avatarfile);
			user.setAvatar(avatarPath);
		}
		
		Integer id = getUidFormSession(session);
		user.setId(id);
		userService.changeInfo(user);
		ResponseResult<String> rr = new ResponseResult<String>();
		rr.setData(user.getAvatar());
		return rr;
	}
	
	@RequestMapping("/getInfo.do")
	@ResponseBody
	public ResponseResult<User> getInfo(HttpSession session){
		//从session中获取uid
		Integer id = getUidFormSession(session);
		//调用业务层对象的getUserById(),得到当前用户的User数据
		User user = userService.getUserById(id);
		//创建返回值对象，将User对象封装到Data属性中
		ResponseResult<User> rr = new ResponseResult<User>();
		rr.setData(user);
		//返回
		return rr;
	}
	
	
	/**
	 * 上传头像
	 * @param request HttpervletRequestr
	 * @param avatarfile CommonsMultipartFile
	 * @return 成功上船后，返回路径
	 * @throws UploadAvatarException 上传失败
	 */
	private String uploadAvatar(HttpServletRequest request,CommonsMultipartFile avatarfile) throws UploadAvatarException {
		//确定头像保存到的文件夹的路径
		String uploadDirPath = request.getServletContext().getRealPath("upload");
		//确定头像保存到的文件夹
		File uploadDir = new File(uploadDirPath);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		int beginIndex = avatarfile.getOriginalFilename().lastIndexOf(".");
		//确定头像文件的扩展名
		String suffix = avatarfile.getOriginalFilename().substring(beginIndex);
		//确定头像文件夹的文件名
		String fileName = UUID.randomUUID().toString()+suffix;
		//确定头像保存在哪个文件
		File dest = new File(uploadDir,fileName);
		//处理上传的头像
		try {
			avatarfile.transferTo(dest);
			return "upload/"+fileName;
		} catch (IllegalStateException e) {
			throw new UploadAvatarException("非法状态！");
		} catch (IOException e) {
			throw new UploadAvatarException("读写出错！");
		}
	}
}
