package org.codingeasy.oauth.client.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
*   
* @author : KangNing Hu
*/
@RequestMapping("/user")
@RestController
public class UserController {


	@GetMapping("/success")
	public String success(String token){
		return "登录成功 token:" + token;
	}
}
