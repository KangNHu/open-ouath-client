package org.codingeasy.oauth.client.model;


/**
*   google oauth token
* @author : KangNing Hu
*/
public class GoogleOAuthToken extends SimpleOAuthToken{


	public GoogleOAuthToken(String token, String refreshToken, long effectiveTime) {
		super(token, refreshToken, effectiveTime);
	}


	public GoogleOAuthToken() {
		super();
	}
}
