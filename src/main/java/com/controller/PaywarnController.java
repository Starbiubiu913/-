package com.controller;

import com.dao.CostDAO;
import com.dao.CustomerDAO;
import com.dao.PaywarnDAO;
import com.dao.PaywarnrecordDAO;
import com.entity.Cost;
import com.entity.Paywarn;
import com.entity.Paywarnrecord;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/paywarn")
public class PaywarnController extends BaseController {
	@Resource
	PaywarnDAO paywarnDAO;
	@Resource
	PaywarnrecordDAO paywarnrecordDAO;
	@Resource
	CustomerDAO customerDAO;
	@Resource
	CostDAO costDAO;


	@RequestMapping("/addview")
	public String addview(HttpServletRequest request) {
		//request.setAttribute("users", customerDAO.show(null,null));
		 Integer id = Integer.valueOf( request.getParameter("id"));
		 		Cost cost=costDAO.findById(id);

		request.setAttribute("userId",cost.getPerson());
		request.setAttribute("username",cost.getName());
		request.setAttribute("price",cost.getMoney());

		return "paywarn/add";
	}

	@RequestMapping("/add")
	@ResponseBody
	public String add(Paywarn data, HttpServletRequest request) {
		data.setCurtime(new Date());
		data.setUsername(customerDAO.findById(data.getUserId()).getName());
		paywarnDAO.add(data);

		//costDAOl.updateStatusById(data.getUserId());

		Paywarnrecord paywarnrecord=new Paywarnrecord();
		paywarnrecord.setCurtime(new Date());
		paywarnrecord.setNotice(data.getId());
		paywarnrecord.setStatus(Paywarnrecord.Status.noread.name());
		paywarnrecordDAO.add(paywarnrecord);



		return "OK";
	}

	@RequestMapping("/del")
	public String del(int id) {
		paywarnDAO.del(id);
		return "redirect:show";
	}

	@RequestMapping("/findById")
	public String findById(int id,HttpServletRequest request) {
		Paywarn record = paywarnDAO.findById(id);
		request.setAttribute("record", record);
		return "paywarn/modify";
	}

	@RequestMapping("/update")
	public String update(Paywarn data,HttpServletRequest request) {
		paywarnDAO.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,@RequestParam(defaultValue = "",value= "text") String text,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);


		String role = (String)request.getSession().getAttribute("role");
		if(!"住户".equals(role)) {
			List all =  paywarnDAO.show(text);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}else {
			int userId = (Integer) request.getSession().getAttribute("userId");
			List all =  paywarnDAO.my(userId);
			PageInfo  p = new PageInfo(all);
			request.setAttribute("page",p);
		}


		return "paywarn/show";
	}

}
