package com.naruto.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.naruto.service.ProducerBean;

/**
 * @author hhp
 * @mail 1228929031@qq.com
 * @date 2018年4月22日
 */

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

	private HttpServletRequest request;

	@Autowired
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	@RequestMapping("/hello")
	public String hello(Map<String, Object> map) {
		map.put("msg", "hello thymeleaf");
		return "hello";
	}
	
	@Autowired
	private ProducerBean produt;

	@RequestMapping(value = "/statistics", method = RequestMethod.POST)
	@ResponseBody
	public String statistics(@RequestParam Map<String, Object> map) {
		if (map !=null ) {
			Object timeObject = map.get("time");
			if(timeObject!=null&&Integer.valueOf(timeObject.toString())>0){
				UUID randomUUID = UUID.randomUUID();
				String id = randomUUID.toString();
				map.put("ip",request.getRemoteAddr());
				produt.sendMessage(id, JSON.toJSONString(map));
			}
		}
		return "ok";
	}

}
