package org.codingeasy.oauth.client.model;

/**
* 简单的oauth token  
* @author : KangNing Hu
*/
public class SimpleOAuthToken implements  OAuthToken{


	private String token;


	private String refreshToken;


	private long effectiveTime;


	public SimpleOAuthToken(String token , String refreshToken , long effectiveTime){
		this.token = token;
		this.refreshToken = refreshToken;
		this.effectiveTime =effectiveTime;
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
