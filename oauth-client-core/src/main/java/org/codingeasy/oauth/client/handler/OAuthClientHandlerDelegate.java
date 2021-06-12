package org.codingeasy.oauth.client.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.XsrfTokenGenerator;
import org.codingeasy.oauth.client.exception.RedirectException;
import org.codingeasy.oauth.client.exception.UnknownOAuthNameException;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.utils.PathUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.*;

import static org.codingeasy.oauth.client.OAuthClientFilter.CALL_PATH;

/**
* oauth client 处理器的委托器  
* @author : KangNing Hu
*/
public class OAuthClientHandlerDelegate {

	public final static String PARAMETER_RESPONSE_TYPE = "code";
	public final static String PARAMETER_STATE = "state";
	public final static String PARAMETER_NAME = "oauth-name";
	//授权url 模版
	public final static String AUTHORIZE_URL_TEMPLATE = "%s?response_type=code&client_id=%s&redirect_uri=%s";
	/**
	 * oauth client 处理器列表
	 */
	private Map<String,OAuthClientHandler> oAuthClientHandlerMap = new HashMap<>();

	/**
	 * xsrf token生成器
	 */
	private XsrfTokenGenerator xsrfTokenGenerator;

	/**
	 * oauth client 授权配置
	 */
	private Map<String , OAuthProperties> authPropertiesMap = new HashMap<>();

	/**
	 * 新增oauth 客户端处理器
	 * @param oAuthClientHandler 客户端处理器对象
	 */
	public void addOAuthClientHandler(OAuthClientHandler oAuthClientHandler){
		addOAuthClientHandlers(Arrays.asList(oAuthClientHandler));
	}

	/**
	 * 新增oauth 客户端处理器
	 * @param oAuthClientHandlers 客户端处理器对象列表
	 */
	public void addOAuthClientHandlers(List<OAuthClientHandler> oAuthClientHandlers){
		if (CollectionUtils.isEmpty(oAuthClientHandlers)){
			return;
		}
		for (OAuthClientHandler oAuthClientHandler : oAuthClientHandlers){
			if (oAuthClientHandler == null){
				continue;
			}
			if (StringUtils.isEmpty(oAuthClientHandler.getName())){
				throw new IllegalStateException(String.format("授权处理器名称不能为空 %s" , oAuthClientHandler.getClass()));
			}
			oAuthClientHandlerMap.put(oAuthClientHandler.getName() , oAuthClientHandler);
		}
	}

	public void setXsrfTokenGenerator(XsrfTokenGenerator xsrfTokenGenerator) {
		this.xsrfTokenGenerator = xsrfTokenGenerator;
	}

	/**
	 * 添加ouath 配置列表
	 * @param oAuthProperties 配置对象列表
	 */
	public void addOAuthProperties(List<OAuthProperties> oAuthProperties){
		if (CollectionUtils.isEmpty(oAuthProperties)){
			return;
		}
		for (OAuthProperties authProperty : oAuthProperties){
			if (authProperty == null){
				continue;
			}
			if (StringUtils.isEmpty(authProperty.getName())){
				throw new IllegalStateException(String.format("授权配置名称不能为空 %s" , authProperty));
			}
			authPropertiesMap.put(authProperty.getName() , authProperty);
		}
	}

	/**
	 * 添加 oauth 配置
	 * @param authProperty 配置对象
	 */
	public void addOAuthProperty(OAuthProperties authProperty){
		this.addOAuthProperties(Arrays.asList(authProperty));
	}


	/**
	 * 执行授权
	 * @param request 请求上下文
	 * @param response 响应上下文
	 */
	public void doAuthorize(HttpServletRequest request , HttpServletResponse response){
		String oauthName = request.getParameter(PARAMETER_NAME);
		OAuthProperties oAuthProperties = authPropertiesMap.get(oauthName);
		if (oAuthProperties == null){
			throw new UnknownOAuthNameException(String.format("未知的授权名称 %s" , oauthName));
		}
		String authorizeUrlTemplate = AUTHORIZE_URL_TEMPLATE;
		if (oAuthProperties.isEnableXsrfToken()){
			authorizeUrlTemplate = authorizeUrlTemplate + "&state=%s";
		}
		String url = fillAuthorizeUrlTemplate(authorizeUrlTemplate , oAuthProperties , request);
		try {
			response.sendRedirect(url);
		}catch (Exception e){
			throw new RedirectException(e);
		}
	}

	/**
	 * 填充授权url的模版
	 * @param authorizeUrlTemplate url模版
	 * @param oAuthProperties oauth 配置
	 * @return
	 */
	private String fillAuthorizeUrlTemplate(String authorizeUrlTemplate , OAuthProperties oAuthProperties,HttpServletRequest request) {
		Object[] params;
		if(oAuthProperties.isEnableXsrfToken()){
			params = new String[4];
		}else {
			params = new String[3];
		}
		params[0] = oAuthProperties.getAccessTokenUrl();
		params[1] = oAuthProperties.getClientId();
		params[2] = PathUtils.getPathPrefix(request) + CALL_PATH + "?" +PARAMETER_NAME +"=" + oAuthProperties.getName();
		if (oAuthProperties.isEnableXsrfToken()){
			params[3] = xsrfTokenGenerator.generate();
		}
		return String.format(authorizeUrlTemplate , params);
	}

	/**
	 * 处理 token
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	public OAuthToken accessToken(HttpServletRequest request , HttpServletResponse response){
		//获取授权码
		String code = request.getParameter(PARAMETER_RESPONSE_TYPE);
		String state = request.getParameter(PARAMETER_STATE);
		String name = request.getParameter(PARAMETER_NAME);
		OAuthClientHandler authClientHandler = oAuthClientHandlerMap.get(name);
		OAuthProperties oAuthProperties = authPropertiesMap.get(name);
		if (authClientHandler == null || oAuthProperties == null){
			throw new UnknownOAuthNameException(String.format("未知的授权名称 %s" , name));
		}
		//创建创建第三方token
		return authClientHandler.createToken(oAuthProperties, code, state);
	}

}
