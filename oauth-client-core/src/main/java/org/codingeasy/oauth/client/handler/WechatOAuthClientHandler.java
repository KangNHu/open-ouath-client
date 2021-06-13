package org.codingeasy.oauth.client.handler;

import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.utils.OKHttpUtils;

/**
* 微信 第三方登录  
* @author : KangNing Hu
*/
public class WechatOAuthClientHandler implements OAuthClientHandler{

	private String NAME = "wechat";

	private final static String TOKEN_URL_TEMPLATE = "%s?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {

		String tokenUrl = String.format(TOKEN_URL_TEMPLATE,
				properties.getAccessTokenUrl(),
				properties.getClientId(),
				properties.getClientSecret(),
				code);
		String s = OKHttpUtils.get(tokenUrl);

		return null;
	}
}
