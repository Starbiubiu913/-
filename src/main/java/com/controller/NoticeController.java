package com.controller;

import com.dao.LiuyanDAO;
import com.dao.NoticeDAO;
import com.entity.Liuyan;
import com.entity.Notice;
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
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	@Resource
	NoticeDAO dao;

	@RequestMapping("/add")
	public String add(Notice data, HttpServletRequest request) {
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
		Notice record = dao.findById(id);
		request.setAttribute("record", record);
		return "notice/modify";
	}

	@RequestMapping("/update")
	public String update(Notice data,HttpServletRequest request) {
		dao.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		List all =  dao.show(text);
		PageInfo  p = new PageInfo(all);
		request.setAttribute("page",p);

		return "notice/show";
	}

}
