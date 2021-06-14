package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* facebook oauth token  
* @author : KangNing Hu
*/
public class FacebookOAuthToken implements OAuthToken{



	@JsonProperty("access_token")
	private String token;


	@JsonProperty("token_type")
	private String tokenType;


	@JsonProperty("expires_in")
	private Long effectiveTime;


	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
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
