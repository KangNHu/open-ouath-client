package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.model.OAuthToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录处理器
 * <p>用于业务登录操作 ，如token置换 ，用户校验等等</p>
 * @author hukangning
 */
public interface LoginHandler {

	/**
	 * 执行登录
	 * @param response  响应对象
	 * @param request 请求对象
	 * @param token 第三方token
	 */
	void doLogin(HttpServletResponse response, HttpServletRequest request, OAuthToken token);


	/**
	 * 执行登录错误处理
	 * @param e  异常
	 * @param response 响应对象
	 * @param request 请求对象
	 */
	void dofFailure(OAuthException e , HttpServletResponse response, HttpServletRequest request) throws IOException;
}
