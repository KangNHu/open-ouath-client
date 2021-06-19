package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* github ouath token
* @author : KangNing Hu
*/
public class GithubOAuthToken extends   SimpleOAuthToken{


	@JsonProperty("refresh_token_expires_in")
	private String refreshTokenEffectiveTime;


	private String error;

	@JsonProperty("error_description")
	private String errorDescription;

	@JsonProperty("error_uri")
	private String errorUri;


	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorUri() {
		return errorUri;
	}

	public void setErrorUri(String errorUri) {
		this.errorUri = errorUri;
	}


	public String getRefreshTokenEffectiveTime() {
		return refreshTokenEffectiveTime;
	}

	public void setRefreshTokenEffectiveTime(String refreshTokenEffectiveTime) {
		this.refreshTokenEffectiveTime = refreshTokenEffectiveTime;
	}


}
