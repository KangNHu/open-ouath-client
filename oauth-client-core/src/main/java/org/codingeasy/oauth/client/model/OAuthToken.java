package org.codingeasy.oauth.client.model;

/**
* oauth2 token  
* @author : KangNing Hu
*/
public interface OAuthToken {

	/**
	 * token
	 * @return 返回token
	 */
	String getToken();

	/**
	 * refresh token
	 * <p>用于刷新token</p>
	 * @return 返回 refresh token
	 */
	String getRefreshToken();


	/**
	 * 返回token有效时长
	 * @return 返回有效时长 单位秒
	 */
	long getEffectiveTime();
}
