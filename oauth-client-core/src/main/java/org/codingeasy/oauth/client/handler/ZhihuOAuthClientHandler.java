package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

/**
* 知乎登录  
* @author : KangNing Hu
*/
public class ZhihuOAuthClientHandler implements OAuthClientHandler {

	private String NAME = "zhihu";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code, String state) {
		return null;
	}
}
