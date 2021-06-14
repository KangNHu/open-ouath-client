package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* github ouath token
* @author : KangNing Hu
*/
public class GithubOAuthToken implements  OAuthToken{


	@JsonProperty("access_token")
	private String token;


	private String scope;


	@JsonProperty("token_type")
	private String tokenType;


	public void setToken(String token) {
		this.token = token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
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
		return -1;
	}
}
