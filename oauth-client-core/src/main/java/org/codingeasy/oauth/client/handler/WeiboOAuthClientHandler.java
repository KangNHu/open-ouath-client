package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

/**
* 微博 登录  
* @author : KangNing Hu
*/
public class WeiboOAuthClientHandler implements OAuthClientHandler {

	private String NAME = "weibo";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		return null;
	}
}
