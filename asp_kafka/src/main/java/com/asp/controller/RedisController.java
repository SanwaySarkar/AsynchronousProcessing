package com.asp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asp.common.redis.MetaDataImpl;
import com.asp.common.redis.Metadata;

@RestController
public class RedisController {
	
	@Autowired
	MetaDataImpl metaDataImpl;

	@PostMapping(value = "/findUserNameinRedis")
    public String findUser(@RequestParam String userName) {

		Metadata result = metaDataImpl.findByName(userName);
		if(result!=null)
			return  result.toString();
		else
			return "UserName not found";
       


    }
}
