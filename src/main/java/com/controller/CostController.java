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
@RequestMapping("/cost")
public class CostController extends BaseController {
	@Resource
	CostDAO dao;
	@Resource
	CustomerDAO customerDAO;
	@Resource
	UserDAO userDAO;
	@Resource
	BasicDAO basicDAO;


	@RequestMapping("/addview")
	public String addview(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,HttpServletRequest request) {
		request.setAttribute("users", customerDAO.show(text,null));

		PageHelper.startPage(pn, 5);
		List all =  customerDAO.show(text,null);
		PageInfo  p = new PageInfo(all);
		request.setAttribute("page",p);

		return "cost/users";
	}

	@RequestMapping("/addviewt")
	public String addviewt(int id,String name,HttpServletRequest request) {
		request.setAttribute("users", customerDAO.show(null,null));
		request.setAttribute("basics", basicDAO.show());

		request.setAttribute("id",id);

		request.setAttribute("name",		customerDAO.findById(id).getName());

		return "cost/add";
	}

	@RequestMapping("/add")
	public String add(Cost data) {
		data.setState("未缴费");
		data.setCurtime(new Date());
		data.setMoney((data.getOver()-data.getStart())*data.getMoney());
		Customer customer =customerDAO.findById(data.getPerson());
		data.setName(customer.getName());
		data.setRoom(customer.getFlat()+"-"+customer.getDanyuan()+"-"+customer.getRoom());
		dao.add(data);
		return "redirect:show";
	}

	@RequestMapping("/del")
	public String del(int id) {
		dao.del(id);
		return "redirect:show";
	}

	@RequestMapping("/findById")
	public String findById(int id,HttpServletRequest request) {
		Cost record = dao.findById(id);
		request.setAttribute("record", record);
		return "cost/modify";
	}

	@RequestMapping("/pay")
	public String pay(int id,HttpServletRequest request) {
		Cost record = dao.findById(id);
		request.setAttribute("record", record);
		return "cost/pay";
	}

	@RequestMapping("/save")
	public String save(Cost data,HttpServletRequest request) {
		data.setState("已缴费");
		dao.save(data);
		return "redirect:show";
	}

	@RequestMapping("/update")
	public String update(Cost data,HttpServletRequest request) {
		dao.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		String role = (String)request.getSession().getAttribute("role");
		if(!"住户".equals(role)) {

			Long flat=null;
			if("抄表员".equals(role)){
				flat = (Long) request.getSession().getAttribute("empFlat");
			}

			List all =  dao.show(text,flat);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}else {
			String room = (String)request.getSession().getAttribute("room");
			List all =  dao.my(room);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}
		return "cost/show";
	}


	@RequestMapping("/download")
	@ResponseBody
	public List download(HttpServletRequest request) {
		String role = (String)request.getSession().getAttribute("role");
		Long flat=null;
		if("抄表员".equals(role)){
			flat = (Long) request.getSession().getAttribute("empFlat");
		}
		List all =  dao.show(null,flat);
		return all;
	}

}
