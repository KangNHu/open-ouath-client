package org.codingeasy.oauth.client.example.handler;

import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.handler.LoginHandler;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
*   
* @author : KangNing Hu
*/
@Component
public class SimpleLoginHandler implements LoginHandler {



	@Override
	public void doLogin(HttpServletResponse response, HttpServletRequest request, OAuthToken token) {

	}

	@Override
	public void dofFailure(OAuthException e, HttpServletResponse response, HttpServletRequest request) {

	}
}
