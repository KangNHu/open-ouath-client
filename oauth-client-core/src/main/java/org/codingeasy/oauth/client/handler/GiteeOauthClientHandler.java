package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;


/**
* gitee的授权  
* @author : KangNing Hu
*/
public class GiteeOauthClientHandler implements OAuthClientHandler {

	private  static final String NAME = "gitee";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code, String state) {

		return null;
	}

}
