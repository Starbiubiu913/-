package com.controller;

import java.io.IOException;
import java.text.*;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import com.dao.*;
import com.entity.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	UserDAO userDAO;
	BaseDAO dao = new BaseDAO();
	@Resource
	NoticeDAO noticeDAO;

	@Autowired
	private SimpleCharVerifyCodeGen simpleCharVerifyCodeGen;

	@RequestMapping("/login")
	public String login(String name,String pwd,String role,String vercode,HttpServletRequest request) {
		Object[] basic = dao.findSingle("select * from basic",null);

		String verifyCode = (String) request.getSession().getAttribute("VerifyCode");
		if(null ==verifyCode || "".equalsIgnoreCase(verifyCode) ||!verifyCode.equalsIgnoreCase(vercode)){
			request.setAttribute("msg","验证码不对");
			return  "login";
		}

		 List<Notice> notices = noticeDAO.show(null);
		 if(notices.size()>0){
			 request.getSession().setAttribute("notice",notices.get(0).getTitle()+":"+notices.get(0).getContent());
		 }


		if ("管理员".equals(role)) {
			Object[] user = dao.findSingle("select * from userinfo where name=? and pwd=?", new Object[] { name, pwd });
			if (user != null) {

				request.getSession().setAttribute("userId", user[0]);
				request.getSession().setAttribute("name", user[1]);
				request.getSession().setAttribute("role", role);
				request.getSession().setAttribute("price", (Integer)basic[1]);
				return "index";
			}else {
				request.setAttribute("msg","用户名或密码不对");
				return "login";
			}
		} else if ("抄表员".equals(role)) {
			Object[] user = dao.findSingle("select * from emp where no=? and pwd=?", new Object[] {name, pwd });
			if (user != null) {
				request.getSession().setAttribute("userId", user[0]);
				request.getSession().setAttribute("name", user[2]);
				request.getSession().setAttribute("empFlat", Long.valueOf((String) user[5]));
				request.getSession().setAttribute("role", role);
				request.getSession().setAttribute("price", (Integer)basic[1]);
				return "index";
			}else {
				request.setAttribute("msg","用户名或密码不对");

				return "login";
			}
		} else {
			Object[] user = dao.findSingle("select * from customer where tel=? and pwd=?", new Object[] {name,pwd});
			if (user != null) {
				request.getSession().setAttribute("userId", user[0]);
				request.getSession().setAttribute("name", user[1]);
				request.getSession().setAttribute("room", user[4]+"-"+user[5]+"-"+user[6]);
				request.getSession().setAttribute("empFlat", Long.valueOf((String) user[4]));

				request.getSession().setAttribute("price", (Integer)basic[1]);
				request.getSession().setAttribute("role",role);
				return "index";
			}else {
				request.setAttribute("msg","用户名或密码不对");

				return "login";
			}
		}
	}


	@RequestMapping("/exit")
	public String exit(HttpServletRequest request) {
		request.getSession().removeAttribute("role");
		return "login";
	}


	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		return "welcome";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "index";
	}



	@RequestMapping("/findById")
	public String findById(int id,HttpServletRequest request) {
		Userinfo record = userDAO.findById(id);
		request.setAttribute("record", record);
		return "userinfo/modify";
	}

	@RequestMapping("/update")
	public String update(Userinfo data,HttpServletRequest request) {
		userDAO.update(data);
		return "redirect:show";
	}


	@RequestMapping("/show")
	public String show(@RequestParam(required = false,defaultValue = "1",value= "pn") Integer pn,HttpServletRequest request) {
		PageHelper.startPage(pn, 5);
		List all =  userDAO.show();
		PageInfo  p = new PageInfo(all);
		request.setAttribute("page",p);
		return "userinfo/show";
	}


	@GetMapping("/verifyCode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			//设置长宽
			VerifyCode verifyCode = simpleCharVerifyCodeGen.generate(80, 28);
			String code = verifyCode.getCode();
			System.err.println(code);
			request.getSession().setAttribute("VerifyCode", code);
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			response.getOutputStream().write(verifyCode.getImgBytes());
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
