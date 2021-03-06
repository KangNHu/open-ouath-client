package org.codingeasy.oauth.client.handler;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.utils.OAuthConfigUtils;
import org.codingeasy.oauth.client.utils.OKHttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
* oauth2 客户端处理器  
* @author : KangNing Hu
*/
public interface OAuthClientHandler {

	//通用的token url 模版
	String TOKEN_URL_TEMPLATE = "%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
	//通用的参数模版
	String URL_PARAM_TEMPLATE = "&%s=%s";

	/**
	 * 获取授权名称
	 * @return 返回授权名称
	 */
	String getName();

	/**
	 * 创建token
	 * @param properties 授权配置信息
	 * @param code 授权码
	 * @return 返回 oauth token对象
	 */
	OAuthToken createToken(OAuthProperties properties , String code);

	/**
	 * 授权之前处理
	 * @param authorizeUrl 授权url
	 * @param oAuthProperties 授权配置属性
	 * @return
	 */
	default String postAfterAuthorizeProcessor(String authorizeUrl , OAuthProperties oAuthProperties){
		return authorizeUrl;
	}


	/**
	 * 是否取消授权
	 * @param request 请求对象
	 * @return 如果取消第三方授权则返回true 否则false
	 */
	default boolean isCancelAuthorize(HttpServletRequest request){
		return false;
	}

	/**
	 * 发送获取token get 请求
	 * @param properties oauth 配置对象
	 * @param code 授权码
	 * @return 响应数据
	 */
	default String sendAccessTokenGetRequest(OAuthProperties properties , String code){
		String tokenUrl = String.format(TOKEN_URL_TEMPLATE,
				properties.getClientId(),
				properties.getClientSecret(),
				code,
				OAuthConfigUtils.getCallbackUrl(properties)
		);
		return OKHttpUtils.get(tokenUrl);
	}

	/**
	 * 发送获取token form 请求
	 * @param properties oauth 配置对象
	 * @param code 授权码
	 * @return 响应数据
	 */
	default String sendAccessTokenFormRequest(OAuthProperties properties , String code){
		return sendAccessTokenFormRequest(properties, code , null);
	}

	/**
	 * 发送获取token form 请求
	 * @param properties oauth 配置对象
	 * @param code 授权码
	 * @param body 额外的请求参数
	 * @return 响应数据
	 */
	default String sendAccessTokenFormRequest(OAuthProperties properties , String code , Map<String , Object> body){
		return sendAccessTokenRequest(
				properties ,
				code ,
				body ,
				b->  OKHttpUtils.form(properties.getAccessTokenUrl() , b));
	}

	/**
	 * 发送获取token post 请求
	 * @param properties oauth 配置对象
	 * @param code 授权码
	 * @param body 额外的请求参数
	 * @return 响应数据
	 */
	default String sendAccessTokenPostRequest(OAuthProperties properties , String code , Map<String , Object> body){
		return sendAccessTokenRequest(
				properties ,
				code ,
				body ,
				b->  OKHttpUtils.post(properties.getAccessTokenUrl() , b));
	}

	/**
	 * 发送获取token post 请求
	 * @param properties oauth 配置对象
	 * @param code 授权码
	 * @return 响应数据
	 */
	default String sendAccessTokenPostRequest(OAuthProperties properties , String code){
		return sendAccessTokenRequest(
				properties ,
				code ,
				null ,
				b->  OKHttpUtils.post(properties.getAccessTokenUrl() , b));
	}

	/**
	 * 发送获取token 请求
	 * @param properties oauth 配置对象
	 * @param code 授权码
	 * @param body 额外的请求参数
	 * @param function 回调
	 * @return 响应数据
	 */
	default String sendAccessTokenRequest(OAuthProperties properties , String code ,
	                                          Map<String , Object> body ,
	                                          Function< Map<String , Object> , String> function){
		if (body == null) {
			body = new HashMap<>();
		}
		body.put("grant_type" ,"authorization_code" );
		body.put("client_id" , properties.getClientId());
		body.put("client_secret" , properties.getClientSecret());
		body.put("code" , code);
		body.put("redirect_uri" ,OAuthConfigUtils.getCallbackUrl(properties) );
		return function.apply(body);
	}


	/**
	 * 追加参数
	 * @param url 原url
	 * @param map 参数映射列表
	 * @param names 参数名称列表
	 * @return
	 */
	default String appendParam(String url , Map<String , String>  map, String... names){
		StringBuilder sb = new StringBuilder(url);
		if (MapUtils.isEmpty(map)){
			return sb.toString();
		}
		for (String name : names){
			String value = map.get(name);
			if (!StringUtils.isEmpty(value)){
				sb.append("&");
				sb.append(name);
				sb.append("=");
				sb.append(value);
			}
		}
		return sb.toString();
	}
}
