package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;


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
	 * @param state xsrf token ,如果{@link OAuthProperties#isEnableXsrfToken()}
	 *              为true 则有值 否则 为null
	 * @return 返回 oauth token对象
	 */
	OAuthToken createToken(OAuthProperties properties , String code , String state);

}
