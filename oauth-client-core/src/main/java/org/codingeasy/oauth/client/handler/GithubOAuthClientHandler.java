package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

/**
* github 登录  
* @author : KangNing Hu
*/
public class GithubOAuthClientHandler implements OAuthClientHandler {

	private final static String NAME = "github";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		return null;
	}
}
