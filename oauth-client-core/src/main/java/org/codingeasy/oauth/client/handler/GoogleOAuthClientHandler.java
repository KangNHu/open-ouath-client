package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

/**
* 谷歌第三方登录  
* @author : KangNing Hu
*/
public class GoogleOAuthClientHandler implements OAuthClientHandler{

	private static final String NAME = "google";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code, String state) {
		return null;
	}
}
