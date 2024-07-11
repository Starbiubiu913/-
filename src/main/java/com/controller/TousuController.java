package com.controller;

import com.dao.EmpDAO;
import com.dao.TousuDAO;
import com.entity.Emp;
import com.entity.Tousu;
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
@RequestMapping("/tousu")
public class TousuController extends BaseController {
	@Resource
	TousuDAO dao;
	@Resource
	EmpDAO empDAO;

	@RequestMapping("/addView")
	public String addView(HttpServletRequest request) {
		String role = (String)request.getSession().getAttribute("role");
		Long flat=null;
		if("住户".equals(role)) {
			flat = (Long) request.getSession().getAttribute("empFlat");
		}
		request.setAttribute("erps", empDAO.show(null,flat));
		return "/tousu/add";
	}

	@RequestMapping("/add")
	public String add(Tousu data, HttpServletRequest request) {
		String name = (String)request.getSession().getAttribute("name");
		String room = (String)request.getSession().getAttribute("room");
		data.setName(name);
		data.setRoom(room);
		 Emp emp = empDAO.findById(data.getEmpid());
		data.setEmp(emp.getName());
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
		Tousu record = dao.findById(id);
		request.setAttribute("record", record);
		return "tousu/modify";
	}

	@RequestMapping("/update")
	public String update(Tousu data,HttpServletRequest request) {
		dao.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		String role = (String)request.getSession().getAttribute("role");
		if(!"住户".equals(role)) {


			if("抄表员".equals(role)){
				text=(String)request.getSession().getAttribute("name");
			}

			List all =  dao.show(text);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}else {
			String room = (String)request.getSession().getAttribute("room");
			List all =  dao.my(room);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}
		return "tousu/show";
	}

}
