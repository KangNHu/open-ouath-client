package org.codingeasy.oauth.client.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.XsrfTokenGenerator;
import org.codingeasy.oauth.client.exception.*;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.utils.OAuthConfigUtils;
import org.codingeasy.oauth.client.utils.PathUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.*;

import static org.codingeasy.oauth.client.OAuthClientFilter.CALL_PATH;

/**
* oauth client 处理器的委托器  
* @author : KangNing Hu
*/
public class OAuthClientHandlerDelegate {
	private final static String PARAMETER_SCOPE = "scope";
	private final static String PARAMETER_RESPONSE_TYPE = "code";
	private final static String PARAMETER_STATE = "state";
	private final static String PARAMETER_NAME = "oauth-name";
	private final static String SESSION_PARAMETER_STATE = "_" + PARAMETER_STATE;
	private final static String SESSION_PARAMETER_NAME = "_" + PARAMETER_NAME;
	//授权url 模版
	private final static String AUTHORIZE_URL_TEMPLATE = "%s?response_type=code&client_id=%s&redirect_uri=%s";
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
	private Map<String , OAuthProperties> oAuthPropertiesMap = new HashMap<>();

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
			oAuthPropertiesMap.put(authProperty.getName() , authProperty);
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
	 * 处理 token
	 * @param request 请求对象
	 * @param response 响应对象
	 */
	public OAuthToken accessToken(HttpServletRequest request , HttpServletResponse response){
		try {
			//获取授权码
			String code = request.getParameter(PARAMETER_RESPONSE_TYPE);
			OAuthProperties oAuthProperties = getOAuthProperties(request);
			OAuthClientHandler oAuthClientHandler = getOAuthClientHandler(request);
			//校验xsrf
			if (oAuthProperties.isEnableXsrfToken()) {
				String state = request.getParameter(PARAMETER_STATE);
				String storeState = getStoreState(request);
				if (StringUtils.isEmpty(state) || !state.equals(storeState)) {
					throw new IllegitimateStateException(String.format("非法的 state:%s", state));
				}
			}

			//是否取消授权
			if (oAuthClientHandler.isCancelAuthorize(request)) {
				throw new CancelAuthorizeException("取消第三方授权");
			}
			//设置回调地址
			OAuthConfigUtils.setCallbackUrl(oAuthProperties, getCallbackUrl(request));
			//创建创建第三方token
			return oAuthClientHandler.createToken(oAuthProperties, code);
		}finally {
			removeStoreAttr(request , SESSION_PARAMETER_STATE);
			removeStoreAttr(request ,SESSION_PARAMETER_NAME);
		}
	}
	/**
	 * 执行授权
	 * @param request 请求上下文
	 * @param response 响应上下文
	 */
	public void doAuthorize(HttpServletRequest request , HttpServletResponse response){
		OAuthProperties oAuthProperties = getOAuthProperties(request);
		OAuthClientHandler oAuthClientHandler = getOAuthClientHandler(request);
		String authorizeUrlTemplate = AUTHORIZE_URL_TEMPLATE;
		if (oAuthProperties.isEnableXsrfToken()){
			authorizeUrlTemplate = authorizeUrlTemplate + "&state=%s";
		}
		if (!StringUtils.isEmpty(oAuthProperties.getScope())){
			authorizeUrlTemplate = authorizeUrlTemplate + "&scope=%s";
		}
		String url;
		try {
			url = fillAuthorizeUrlTemplate(authorizeUrlTemplate , oAuthProperties , request);
			//对授权之前处理
			url = oAuthClientHandler.postAfterAuthorizeProcessor(url, oAuthProperties);
		}catch (Exception e){
			throw new OAuthException(e);
		}
		try {
			//重定向到第三方授权
			response.sendRedirect(url);
		}catch (Exception e){
			throw new RedirectException(e);
		}
	}

	/**
	 * 获取oauth 客户端处理其
	 * @param request 请求对象
	 * @return 返回和当前请求匹配的 客户端处理其
	 * @throws UnknownOAuthNameException 当找不到当前请求对应的oauht 配置时 抛出
	 */
	private OAuthClientHandler getOAuthClientHandler(HttpServletRequest request) {
		String oAuthName = getOAuthName(request);
		OAuthClientHandler oAuthClientHandler = oAuthClientHandlerMap.get(oAuthName);
		if (oAuthClientHandler == null){
			throw new UnknownOAuthNameException(String.format("未知的授权名称 %s" , oAuthName));
		}
		return oAuthClientHandler;
	}


	/**
	 * 获取授权名称
	 * @param request 请求对象
	 * @return 返回授权名称
	 */
	private String getOAuthName(HttpServletRequest request){
		String oauthName = request.getParameter(PARAMETER_NAME);
		if (StringUtils.isEmpty(oauthName)){
			oauthName = (String) request.getSession().getAttribute(SESSION_PARAMETER_NAME);
		}
		if (StringUtils.isEmpty(oauthName)){
			throw new UnknownOAuthNameException("授权名称不能为空");
		}
		return oauthName;
	}
	/**
	 * 获取 授权配置
	 * @param request 请求对象
	 * @return 返回和当前请求匹配的oauth 配置
	 * @throws UnknownOAuthNameException 当找不到当前请求对应的oauht 配置时 抛出
	 */
	private OAuthProperties getOAuthProperties(HttpServletRequest request) {
		String scope = null;
		String oauthName = getOAuthName(request);;
		//遍历所以请求参数
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String , String> extend = new HashMap<>();
		while (parameterNames.hasMoreElements()){
			String parameterName = parameterNames.nextElement();
			if (PARAMETER_SCOPE.equals(parameterName)){//授权范围
				scope = request.getParameter(PARAMETER_SCOPE);
			}
			//扩展参数
			else {
				extend.put(parameterName , request.getParameter(parameterName));
			}
		}
		//授权名称不能为空
		if (StringUtils.isEmpty(oauthName)){
			throw new UnknownOAuthNameException("授权名称不能为空");
		}
		//根据授权名称获取 open 授权配置
		OAuthProperties oAuthProperties = oAuthPropertiesMap.get(oauthName);
		if (oAuthProperties == null){
			throw new UnknownOAuthNameException(String.format("未知的授权名称 %s" , oauthName));
		}
		oAuthProperties = ObjectUtils.clone(oAuthProperties);
		//如果请求指定了授权范围则使用请求所指定的
		if (!StringUtils.isEmpty(scope)){
			oAuthProperties.setScope(scope);
		}
		//使用请求的扩展参数覆盖配置的扩展参数
		HashMap<String, String> oAuthPropertiesExtend = oAuthProperties.getExtend();
		oAuthPropertiesExtend.putAll(extend);
		return oAuthProperties;
	}

	/**
	 * 填充授权url的模版
	 * @param authorizeUrlTemplate url模版
	 * @param oAuthProperties oauth 配置
	 * @return
	 */
	private String fillAuthorizeUrlTemplate(String authorizeUrlTemplate , OAuthProperties oAuthProperties,HttpServletRequest request) throws UnsupportedEncodingException {
		Object[] params;
		int optionNum = 0;
		if (oAuthProperties.isEnableXsrfToken()){
			optionNum ++;
		}
		if (!StringUtils.isEmpty(oAuthProperties.getScope())){
			optionNum ++;
		}
		params = new String[3 + optionNum];
		params[0] = oAuthProperties.getAuthorizeUrl();
		params[1] = oAuthProperties.getClientId();
		params[2] = URLEncoder.encode(getCallbackUrl(request) , "UTF-8");
		//存在 ouaht-name 在session
		storeAttr(request ,SESSION_PARAMETER_NAME , oAuthProperties.getName());
		if (oAuthProperties.isEnableXsrfToken()){
			params[3] = xsrfTokenGenerator.generate();
			//存在 state在session
			storeAttr(request ,SESSION_PARAMETER_STATE , (String) params[3]);
		}
		String scope = oAuthProperties.getScope();
		if (!StringUtils.isEmpty(scope)){
			params[4] = scope;
		}
		return String.format(authorizeUrlTemplate , params);
	}

	/**
	 * 存在 属性到session store中
	 * <p>忽略多个浏览器窗口导致的session 线程不安全问题</p>
	 * @param key  属性名称
	 * @param value 属性值
	 * @param request 请求对象
	 */
	private void storeAttr(HttpServletRequest request ,String key , String value){
		HttpSession session = request.getSession();
		session.setAttribute(key , value);
	}

	/**
	 *
	 * 移除 session 中的属性
	 * <p>忽略多个浏览器窗口导致的session 线程不安全问题</p>
	 * @param request 请求对象
	 * @param key 属性名称
	 */
	private void removeStoreAttr(HttpServletRequest request , String key ){
		HttpSession session = request.getSession();
		session.removeAttribute(key);
	}
	/**
	 * 获取存储的state
	 * @param request  请求对象
	 * @return
	 */
	private String getStoreState(HttpServletRequest request){
		HttpSession session = request.getSession();
		return (String) session.getAttribute(SESSION_PARAMETER_STATE);
	}

	/**
	 * 获取回调 url
	 * @param request 请求对象
	 * @return
	 */
	@NotNull
	private String getCallbackUrl(HttpServletRequest request) {
		return PathUtils.getPathPrefix(request) + CALL_PATH;
	}



}
