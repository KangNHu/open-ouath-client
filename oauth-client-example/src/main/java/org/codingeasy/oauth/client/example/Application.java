package org.codingeasy.oauth.client.example;

import org.codingeasy.oauth.client.springboot.annotation.EnableOpenOAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
*   
* @author : KangNing Hu
*/
@SpringBootApplication
@EnableOpenOAuthClient
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class , args);
	}
}
