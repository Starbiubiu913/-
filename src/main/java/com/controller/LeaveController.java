package com.controller;

import com.dao.CustomerDAO;
import com.dao.LeaveDAO;
import com.dao.PaywarnDAO;
import com.dao.PaywarnrecordDAO;
import com.entity.Leave;
import com.entity.Paywarn;
import com.entity.Paywarnrecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController {
	@Resource
	LeaveDAO dao;
	@Resource
	CustomerDAO customerDAO;


	@RequestMapping("/addview")
	public String addview(HttpServletRequest request) {
		request.setAttribute("username", request.getSession().getAttribute("name"));
		return "leave/add";
	}

	@RequestMapping("/add")
	public String add(Leave data, HttpServletRequest request) {
		data.setCurtime(new Date());
		data.setStatus("带审批");
		data.setUserId((Integer) request.getSession().getAttribute("userId"));
		data.setUsername((String) request.getSession().getAttribute("name"));
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
		Leave record = dao.findById(id);
		request.setAttribute("record", record);
		return "leave/modify";
	}

	@RequestMapping("/update")
	public String update(Leave data,HttpServletRequest request) {
		if(null ==data.getStatus() || "".equals(data.getStatus())){
			data.setStatus("带审批");

		}
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
			int userId = (Integer) request.getSession().getAttribute("userId");
			List all =  dao.my(userId);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}


		return "leave/show";
	}

}
