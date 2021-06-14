package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* 微博的oauth token  
* @author : KangNing Hu
*/
public class WeiboOAuthToken implements  OAuthToken {


	@JsonProperty("access_token")
	private String token;


	@JsonProperty("expires_in")
	private Long effectiveTime;



	private String uid;


	public void setToken(String token) {
		this.token = token;
	}

	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public String getRefreshToken() {
		return null;
	}

	@Override
	public long getEffectiveTime() {
		return effectiveTime;
	}
}
