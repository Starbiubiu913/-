package com.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.*;
import com.entity.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
	@Resource 
	HouseDAO dao;
  	@Resource
	CustomerDAO customerDAO;
	@RequestMapping("/add")
	@ResponseBody
	public String add(House data,HttpServletRequest request) {
		House n=dao.selectByFlat(data.getFlat());
		if(null !=n){
			return "楼层号重复";
		}
		dao.add(data);
		return "OK";
	}

	@RequestMapping("/del")
	@ResponseBody
	public String del(Long id) {
		 List show = customerDAO.show(null, id);
		 if(null !=show && show.size()>0){
		 	return "失败：改楼栋存在住户！";
		 }
		House house = dao.selectByFlat(id + "");
		dao.del(house.getId());
		return "OK";
	}

	@RequestMapping("/findById")
	public String findById(int id,HttpServletRequest request) {
		House record = dao.findById(id);
		request.setAttribute("record", record);
		return "house/modify";
	}

	@RequestMapping("/update")
	public String update(House data,HttpServletRequest request) {
		dao.update(data);
		return "redirect:show";
	}

	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		List all =  dao.show(text);
		PageInfo  p = new PageInfo(all);
		request.setAttribute("page",p);
	    return "house/show";
	}

}
