package org.codingeasy.oauth.client;


import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.handler.LoginHandler;
import org.codingeasy.oauth.client.handler.OAuthClientHandler;
import org.codingeasy.oauth.client.handler.OAuthClientHandlerDelegate;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.utils.OAuthConfigUtils;
import org.codingeasy.oauth.client.utils.PathUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
*  oauth2  授权客户端 过滤器
* @author : KangNing Hu
*/
public class OAuthClientFilter implements Filter {

	//登录path
	public final static String LOGIN_PATH = "/oauth2/login";
	//回调的path
	public final static String CALL_PATH = "/oauth2/call";

	private OAuthClientHandlerDelegate oAuthClientHandlerDelegate = new OAuthClientHandlerDelegate();

	private LoginHandler loginHandler;

	public OAuthClientFilter(List<OAuthProperties> oAuthProperties ,
	                         LoginHandler loginHandler){
		this(OAuthConfigUtils.getDefaultOAuthClientHandlers(), oAuthProperties , new DefaultXsrfTokenGenerator() , loginHandler);
	}

	public OAuthClientFilter(List<OAuthClientHandler> oAuthClientHandlers ,
	                         List<OAuthProperties> oAuthProperties ,
	                         LoginHandler loginHandler){
		this(oAuthClientHandlers , oAuthProperties , new DefaultXsrfTokenGenerator() , loginHandler);
	}

	public OAuthClientFilter(List<OAuthClientHandler> oAuthClientHandlers ,
	                         List<OAuthProperties> oAuthProperties ,
	                         XsrfTokenGenerator tokenGenerator ,
	                         LoginHandler loginHandler){
		assert tokenGenerator != null : "xsrf token generator is not null";
		assert loginHandler != null : "login handler is not null";
		oAuthClientHandlerDelegate.addOAuthClientHandlers(oAuthClientHandlers);
		oAuthClientHandlerDelegate.setXsrfTokenGenerator(tokenGenerator);
		oAuthClientHandlerDelegate.addOAuthProperties(oAuthProperties);
		this.loginHandler = loginHandler;
	}

	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//必须要是http协议
		if (!(request instanceof HttpServletRequest)){
			return;
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		try {
			//执行oauth执行流程
			executeOAuthFlow((HttpServletRequest)request , (HttpServletResponse)response , chain);
		}catch (OAuthException e){
			//处理登录错误
			loginHandler.dofFailure( e ,httpServletResponse ,httpServletRequest);
		}
	}

	/**
	 * 执行oauth执行流程
	 * @param request 请求上下文
	 * @param response 响应上下文
	 * @param chain 过滤链
	 */
	private void executeOAuthFlow(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String controlPath = PathUtils.getControlPath(request);
		switch (controlPath){
			case LOGIN_PATH:
				executeLogin(request , response);
				break;
			case CALL_PATH:
				executeCallback(request , response);
				break;
				default:chain.doFilter(request , response);
		}
	}

	/**
	 * 执行回调处理
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	private void executeCallback(HttpServletRequest request, HttpServletResponse response) {
		OAuthToken oAuthToken = oAuthClientHandlerDelegate.accessToken(request, response);
		loginHandler.doLogin(response , request , oAuthToken);
	}

	/**
	 * 执行登录
	 * @param request 请求上下文
	 * @param response 响应上下文
	 */
	private void executeLogin(HttpServletRequest request, HttpServletResponse response) {
		 oAuthClientHandlerDelegate.doAuthorize(request , response);
	}

	@Override
	public void destroy() {

	}

	/**
	 * 默认的xsrf token 生成器
	 * @author hukangning
	 */
	private static class  DefaultXsrfTokenGenerator implements XsrfTokenGenerator{

		@Override
		public String generate() {
			return UUID.randomUUID().toString();
		}
	}
}
