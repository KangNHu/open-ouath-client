package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
* 简单的oauth token  
* @author : KangNing Hu
*/
public class SimpleOAuthToken implements  OAuthToken{

	private Map<String , Object> attach = new HashMap<>();

	@JsonProperty("access_token")
	private String token;

	@JsonProperty("token_type")
	private String tokenType;

	private String scope;

	@JsonProperty("expires_in")
	private Long effectiveTime;


	@JsonProperty("refresh_token")
	private String refreshToken;


	public SimpleOAuthToken(String token , String refreshToken , long effectiveTime){
		this.token = token;
		this.refreshToken = refreshToken;
		this.effectiveTime =effectiveTime;
	}


	public SimpleOAuthToken() {
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


	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}


	public Map<String, Object> getAttach() {
		return attach;
	}

	public void setAttach(Map<String, Object> attach) {
		this.attach = attach;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
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
