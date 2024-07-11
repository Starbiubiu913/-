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
@RequestMapping("/liuyan")
public class LiuyanController extends BaseController {
	@Resource
	LiuyanDAO dao;

	@RequestMapping("/add")
	public String add(Liuyan data,HttpServletRequest request) {
		String name = (String)request.getSession().getAttribute("name");
		String room = (String)request.getSession().getAttribute("room");
		data.setName(name);
		data.setRoom(room);
		data.setReply("未回复");
		data.setCurtime(new Date());
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
		Liuyan record = dao.findById(id);
		request.setAttribute("record", record);
		return "liuyan/modify";
	}

	@RequestMapping("/update")
	public String update(Liuyan data,HttpServletRequest request) {
		dao.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		String role = (String)request.getSession().getAttribute("role");
		if(!"住户".equals(role)) {
			List all =  dao.show(text);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}else {
			String room = (String)request.getSession().getAttribute("room");
			List all =  dao.my(room);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}
		return "liuyan/show";
	}

}
