package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

import javax.servlet.http.HttpServletRequest;


/**
* oauth2 客户端处理器  
* @author : KangNing Hu
*/
public interface OAuthClientHandler {

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
}
