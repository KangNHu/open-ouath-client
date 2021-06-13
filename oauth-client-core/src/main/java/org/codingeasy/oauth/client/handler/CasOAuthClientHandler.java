package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

/**
* Cas开源项目登录  
* @author : KangNing Hu
*/
public class CasOAuthClientHandler implements OAuthClientHandler {

	private String NAME = "cas";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		return null;
	}
}
