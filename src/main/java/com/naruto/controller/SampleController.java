package com.naruto.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naruto.service.ProducerBean;

/**
 * @author hhp
 * @mail 1228929031@qq.com
 * @date 2018年4月21日
 */
@RestController
public class SampleController {

	@Autowired
	private ProducerBean produt;
	

	@RequestMapping("/sendMessage")
	public String sendMessage() {
		UUID randomUUID = UUID.randomUUID();
		String key = randomUUID.toString();
		produt.sendMessage(key, "data>" + key);
		return key;
	}

}
