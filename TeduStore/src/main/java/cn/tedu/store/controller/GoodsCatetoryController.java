package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.GoodsCateGory;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IGoodsCategoryService;

@Controller
@RequestMapping("/goodsCatetory")
public class GoodsCatetoryController extends BaseController {
	
	@Autowired
	private IGoodsCategoryService goodsCategoryService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<GoodsCateGory>> getListByParent(@RequestParam("parent_id") Long parentId){
		System.out.println("parentId="+parentId);
		List<GoodsCateGory> goodsCateGorys = goodsCategoryService.getListByParent(parentId);
		ResponseResult<List<GoodsCateGory>> rr = new ResponseResult<List<GoodsCateGory>>();
		rr.setData(goodsCateGorys);
		return rr;
	}
	
	
	
}
