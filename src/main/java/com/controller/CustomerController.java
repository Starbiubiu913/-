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
@RequestMapping("/customer")
public class CustomerController extends BaseController {
	@Resource
	CustomerDAO dao;
	@Resource
	HouseDAO houseDAO;
	@Resource
	CostDAO costDAO;

	@RequestMapping("/showadd")
	public String showadd(HttpServletRequest request) {
		request.setAttribute("houses", houseDAO.show(null));
		return "/customer/add";
	}
	@RequestMapping("/add")
	public String add(Customer data) {

		data.setPwd("111111");
		dao.add(data);
		return "redirect:show";
	}

	@RequestMapping("/del")
	public String del(int id) {
		costDAO.delByCustId(id);
		dao.del(id);
		return "redirect:show";
	}

	@RequestMapping("/findById")
	public String findById(int id,HttpServletRequest request) {
		Customer record = dao.findById(id);
		request.setAttribute("record", record);
		request.setAttribute("houses", houseDAO.show(null));

		return "customer/modify";
	}

	@RequestMapping("/edit")
	public String edit(int id,HttpServletRequest request) {
		Customer record = dao.findById(id);
		String room = record.getFlat()+"-"+record.getDanyuan()+"-"+record.getRoom();
		request.setAttribute("house", room);
		request.setAttribute("record", record);
		return "customer/edit";
	}

	@RequestMapping("/update")
	public String update(Customer data,HttpServletRequest request) {
		dao.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,@RequestParam(defaultValue = "",value= "flat") Long flat,HttpServletRequest request) {

		PageHelper.startPage(pn, 5);
		String role = (String)request.getSession().getAttribute("role");
		if(!"住户".equals(role)) {

			if("抄表员".equals(role)){
				 flat = (Long) request.getSession().getAttribute("empFlat");
			}
			List all =  dao.show(text,flat);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}else {
			int id = (Integer)request.getSession().getAttribute("userId");
			List all =  dao.my(id,text,flat);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}
		request.setAttribute("houses", houseDAO.show(null));
		return "customer/show";
	}

	@RequestMapping("/load")
	public String load(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		List all =  dao.show(null,null);
		PageInfo  p = new PageInfo(all);
		request.setAttribute("page",p);

		return "customer/load";
	}

	@RequestMapping("/download")
	@ResponseBody
	public List download() {
		List all =  dao.show(null,null);
		return all;
	}
}
