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
@RequestMapping("/basic")
public class BasicController extends BaseController {
	@Resource 
	BasicDAO dao;
  
	@RequestMapping("/add")
	public String add(Basic data) {
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
		Basic record = dao.findById(id);
		request.setAttribute("record", record);
		return "basic/modify";
	}

	@RequestMapping("/update")
	public String update(Basic data,HttpServletRequest request) {
		dao.update(data);
		return "redirect:show";
	}

	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		List all =  dao.show();
		PageInfo  p = new PageInfo(all);
		request.setAttribute("page",p);
	    return "basic/show";
	}

}
