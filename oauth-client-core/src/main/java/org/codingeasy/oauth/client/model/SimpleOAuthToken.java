package org.codingeasy.oauth.client.model;

import java.util.HashMap;
import java.util.Map;

/**
* 简单的oauth token  
* @author : KangNing Hu
*/
public class SimpleOAuthToken implements  OAuthToken{

	private Map<String , Object> attach = new HashMap<>();

	private String token;


	private String refreshToken;


	private long effectiveTime;


	public SimpleOAuthToken(String token , String refreshToken , long effectiveTime){
		this.token = token;
		this.refreshToken = refreshToken;
		this.effectiveTime =effectiveTime;
	}

	/**
	 * 设置附加属性
	 * @param key key
	 * @param value value
	 */
	public void setAttachAttr(String key , Object value){
		this.attach.put(key , value);
	}

	/**
	 * 获取附加属性
	 * @param key key
	 * @param <T> 值的类型
	 * @return 如果有返回值 如果没有返回null
	 */
	public <T>T getAttachAttr(String key){
		return (T) this.attach.get(key);
	}




	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public String getRefreshToken() {
		return this.refreshToken;
	}

	@Override
	public long getEffectiveTime() {
		return this.effectiveTime;
	}
}
