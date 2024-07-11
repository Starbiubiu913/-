package com.controller;

import com.dao.LiuyanDAO;
import com.dao.StatisticDAO;
import com.entity.Liuyan;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/statistic")
public class StatisticController extends BaseController {
	@Resource
	StatisticDAO dao;


	@RequestMapping("/house")
	@ResponseBody
	public Map house(HttpServletRequest request) {
		String role = (String)request.getSession().getAttribute("role");
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		if(!"住户".equals(role)) {
			userId=null;
		}

		List<Map<String,Object>> all =  dao.house(userId);
		List<String> flat=new ArrayList<>();
		List<Long> data=new ArrayList<>();
		for(Map<String,Object> o:all){
				flat.add((String) o.get("flat"));
			   data.add((Long) o.get("people"));
		}

		Map<String,Object> result=new HashMap<>();
		result.put("flat",flat);
		result.put("data",data);

		return result;
	}

	@RequestMapping("/person")
	@ResponseBody
	public Map<String, Object> person(HttpServletRequest request) {
		String role = (String)request.getSession().getAttribute("role");
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		if(!"住户".equals(role)) {
			userId=null;
		}

		List<Map<String,Object>> all =  dao.person(userId);
		List<String> time=new ArrayList<>();
		List<String> person=new ArrayList<>();
		List<Map> data=new ArrayList<>();
		for(Map<String,Object> o:all){
			if(!time.contains((String) o.get("curtime"))){
				time.add((String) o.get("curtime"));
			}

			if(!person.contains((String) o.get("person"))){
				person.add((String) o.get("person"));
			}
		}

		for(String o:person){

			Map<String,Object> people=new HashMap<>();
			people.put("name",o);
			people.put("type","line");
			people.put("stack","总人数");

			List<Integer> dt=new ArrayList();
			for(String t:time){
//
				int money=0;
				for(Map<String,Object> m:all){
					if(((String)m.get("curtime")).equals(t) && o.equals((String)m.get("person"))){
						money=((BigDecimal)m.get("money")).intValue();
						break;
					}
				}
				dt.add(money);
			}
			people.put("data",dt);
			data.add(people);
		}

		Map<String,Object> result=new HashMap<>();
		result.put("time",time);
		result.put("flat",person);

		result.put("data",data);

		return result;
	}

	@RequestMapping("/tousu")
	@ResponseBody
	public Map<String, Object> tousu(HttpServletRequest request) {
		String role = (String)request.getSession().getAttribute("role");
		List<Map> all =  dao.tousu(null);
		List<String> time=new ArrayList<>();
		List<String> person=new ArrayList<>();
		List<Map> data=new ArrayList<>();
		for(Map<String,Object> o:all){
			if(!time.contains((String) o.get("curtime"))){
				time.add((String) o.get("curtime"));
			}

			if(!person.contains((String) o.get("person"))){
				person.add((String) o.get("person"));
			}
		}
		for(String o:person){

			Map<String,Object> people=new HashMap<>();
			people.put("name",o);
			people.put("type","line");
			people.put("stack","总人数");

			List<Integer> dt=new ArrayList();
			for(String t:time){
//
				int money=0;
				for(Map<String,Object> m:all){
					if(((String)m.get("curtime")).equals(t) && o.equals((String)m.get("person"))){
						money=((BigDecimal)m.get("num")).intValue();
						break;
					}
				}
				dt.add(money);
			}
			people.put("data",dt);
			data.add(people);
		}

		Map<String,Object> result=new HashMap<>();
		result.put("time",time);
		result.put("flat",person);

		result.put("data",data);

		return result;
	}


}
