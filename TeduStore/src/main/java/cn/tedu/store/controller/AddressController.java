package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IAddressService;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController{
	
	@Autowired
	private IAddressService addressService;
	
	@RequestMapping(value="/handle_addnew.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleAddnew(Address address,HttpSession session){
		String currentUser = session.getAttribute("username").toString();
		Integer uid = getUidFormSession(session);
		address.setUid(uid);
		addressService.addnew(currentUser, address);
		return new ResponseResult<Void>();
	}
	
	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<Address>> getList(HttpSession session){
		Integer uid = Integer.parseInt(session.getAttribute("uid").toString());
		List<Address> addresses = addressService.getList(uid);
		ResponseResult<List<Address>> rr = new ResponseResult<List<Address>>();
		rr.setData(addresses);
		return rr;
	}
	
	@RequestMapping("/set_default.do")
	@ResponseBody
	public ResponseResult<Void> setDefault(@RequestParam("id") int id,HttpSession session){
		Integer uid = getUidFormSession(session);
		addressService.setDefaultAddress(uid, id);
		return new ResponseResult<Void>();
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public ResponseResult<Void> delete(HttpSession session,Integer id){
		Integer uid = getUidFormSession(session);
		addressService.deleteById(uid, id);
		return new ResponseResult<Void>();
	}
	
}
