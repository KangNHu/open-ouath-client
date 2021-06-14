package org.codingeasy.oauth.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.exception.AccessTokenException;
import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.model.WeiboOAuthToken;

import java.util.HashMap;

/**
* 微博 登录  
* @author : KangNing Hu
*/
public class WeiboOAuthClientHandler implements OAuthClientHandler {

	private final static String NAME = "weibo";

	private static final String PARAMETER_DISPLAY = "display";
	private static final String PARAMETER_FORCELOGIN = "forcelogin";
	private static final String PARAMETER_LANGUAGE = "language";

	@Override
	public String getName() {
		return NAME;
	}


	@Override
	public String postAfterAuthorizeProcessor(String authorizeUrl, OAuthProperties oAuthProperties) {
		return appendParam(authorizeUrl , oAuthProperties.getExtend(), PARAMETER_DISPLAY,PARAMETER_FORCELOGIN,PARAMETER_LANGUAGE);
	}



	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		String text = null;
		try {
			text = sendAccessTokenFormRequest(properties, code);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(text , WeiboOAuthToken.class );
		}catch (Exception e){
			throw new AccessTokenException(text , e);
		}
	}
}
