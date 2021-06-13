package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* 码云token  
* @author : KangNing Hu
*/
public class GiteeOAuthToken implements OAuthToken {

	@JsonProperty("access_token")
	private String token;

	@JsonProperty("token_type")
	private String tokenType;


	@JsonProperty("expires_in")
	private Long effectiveTime;


	@JsonProperty("refresh_token")
	private String refreshToken;


	private String scope;


	@JsonProperty("created_at")
	private Long createdAt;


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

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
}
