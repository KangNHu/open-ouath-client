package org.codingeasy.oauth.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.exception.InvalidCodeException;
import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.model.WeChatOAuthToken;
import org.codingeasy.oauth.client.utils.OKHttpUtils;

import java.io.IOException;

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
		String text = OKHttpUtils.get(tokenUrl);
		ObjectMapper objectMapper = new ObjectMapper();
		WeChatOAuthToken token;
		try {
			token = objectMapper.readValue(text , WeChatOAuthToken.class);
		} catch (IOException e) {
			throw new OAuthException(e);
		}
		//获取token失败
		if (!StringUtils.isEmpty(token.getErrcode())){
			//处理是否为无效的code
			if ("40029".equals(token.getErrcode())){
				throw new InvalidCodeException(token.getErrmsg());
			}
			throw new OAuthException(token.getErrmsg());
		}
		return token;
	}
}
