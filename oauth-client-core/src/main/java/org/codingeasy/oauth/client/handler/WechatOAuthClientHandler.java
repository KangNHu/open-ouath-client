package org.codingeasy.oauth.client.handler;

import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;

/**
* 微信 第三方登录  
* @author : KangNing Hu
*/
public class WechatOAuthClientHandler implements OAuthClientHandler{

	private String NAME = "wechat";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code, String state) {
		return null;
	}
}
