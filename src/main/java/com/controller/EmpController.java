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
@RequestMapping("/emp")
public class EmpController extends BaseController {
	@Resource
	EmpDAO empDAO;
	@Resource
	HouseDAO houseDAO;

	@RequestMapping("/showAdd")
	public String showAdd(HttpServletRequest request) {
		request.setAttribute("houses", houseDAO.show(null));
		return "/emp/add";
	}

	@RequestMapping("/add")
	@ResponseBody
	public String add(Emp data,HttpServletRequest request) {
		Emp emp=empDAO.selectByNo(data.getNo());
		if(null !=emp){
			request.setAttribute("msg", "工号重复");
			return "工号重复";
		}
		data.setPwd("111111");
		empDAO.add(data);
		return "OK";
	}

	@RequestMapping("/del")
	public String del(int id) {
		empDAO.del(id);
		return "redirect:show";
	}

	@RequestMapping("/findById")
	public String findById(int id,HttpServletRequest request) {
		Emp record = empDAO.findById(id);
		request.setAttribute("record", record);
		request.setAttribute("houses", houseDAO.show(null));

		return "emp/modify";
	}

	@RequestMapping("/update")
	public String update(Emp data,HttpServletRequest request) {
		empDAO.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,@RequestParam(defaultValue = "",value= "flat") Long flat,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		String role = (String)request.getSession().getAttribute("role");
		if(!"抄表员".equals(role)) {
			if("住户".equals(role)){
				flat = (Long) request.getSession().getAttribute("empFlat");
			}

			List all =  empDAO.show(text,flat);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}else {
			int id = (Integer)request.getSession().getAttribute("userId");
			List all =  empDAO.my(id,flat);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}
		request.setAttribute("houses", houseDAO.show(null));
		return "emp/show";
	}


	@RequestMapping("/download")
	@ResponseBody
	public List download() {
		List all =  empDAO.show(null,null);
		return all;
	}

}
