package org.codingeasy.oauth.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.exception.AccessTokenException;
import org.codingeasy.oauth.client.model.GithubOAuthToken;
import org.codingeasy.oauth.client.model.OAuthToken;

import java.util.HashMap;

/**
* github 登录  
* @author : KangNing Hu
*/
public class GithubOAuthClientHandler implements OAuthClientHandler {

	private final static String NAME = "github";

	private final static String PARAMETER_ERROR_ALLOW_SIGNUP = "allow_signup";
	private final static String PARAMETER_ERROR_LOGIN = "login";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String postAfterAuthorizeProcessor(String authorizeUrl, OAuthProperties oAuthProperties) {
		return appendParam(authorizeUrl , oAuthProperties.getExtend() ,PARAMETER_ERROR_ALLOW_SIGNUP,PARAMETER_ERROR_LOGIN  );
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		String text = null;
		try {
			text = sendAccessTokenPostRequest(properties, code);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(text , GithubOAuthToken.class);
		}catch (Exception e){
			throw new AccessTokenException(text , e);
		}
	}
}
