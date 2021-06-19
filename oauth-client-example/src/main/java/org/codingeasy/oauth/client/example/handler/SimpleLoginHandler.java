package org.codingeasy.oauth.client.example.handler;

import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.handler.LoginHandler;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.springframework.stereotype.Component;
import sun.net.www.http.ChunkedOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
*   
* @author : KangNing Hu
*/
@Component
public class SimpleLoginHandler implements LoginHandler {



	@Override
	public void doLogin(HttpServletResponse response, HttpServletRequest request, OAuthToken token)  {
		String token1 = token.getToken();
		try {
			response.sendRedirect(request.getContextPath()+ "/user/success?token=" + token1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dofFailure(OAuthException e, HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("*/*;charset=utf-8");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(e.getMessage().getBytes("UTF-8"));
	}
}
