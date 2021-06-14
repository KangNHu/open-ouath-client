package org.codingeasy.oauth.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.exception.AccessTokenException;
import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.model.GiteeOAuthToken;
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
	public OAuthToken createToken(OAuthProperties properties, String code) {
		String text =null;
		try {
			text = sendAccessTokenFormRequest(properties, code);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(text , GiteeOAuthToken.class);
		}catch (Exception e){
			throw new AccessTokenException(text ,e);
		}
	}

}
