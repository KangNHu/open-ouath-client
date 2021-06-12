package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

/**
* qq 第三方登录
* @author : KangNing Hu
*/
public class QQOAuthClientHandler implements OAuthClientHandler{

	private final static String NAME = "qq";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code, String state) {
		return null;
	}
}
