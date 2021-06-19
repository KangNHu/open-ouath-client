package org.codingeasy.oauth.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.exception.AccessTokenException;
import org.codingeasy.oauth.client.exception.InvalidCodeException;
import org.codingeasy.oauth.client.model.GithubOAuthToken;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.utils.TextUtils;

/**
* github 登录  
* @author : KangNing Hu
*/
public class GithubOAuthClientHandler implements OAuthClientHandler {

	private final static String NAME = "github";

	private final static String PARAMETER_ERROR_ALLOW_SIGNUP = "allow_signup";
	private final static String PARAMETER_ERROR_LOGIN = "login";
	private final static String BAD_VERIFICATION_CODE = "bad_verification_code";

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
		GithubOAuthToken githubOAuthToken;
		try {
			text = sendAccessTokenFormRequest(properties, code);
			if (TextUtils.validJson(text)){
				ObjectMapper objectMapper = new ObjectMapper();
				githubOAuthToken =  objectMapper.readValue(text , GithubOAuthToken.class);
			}else {
				githubOAuthToken = TextUtils.formBodyToObject(text , GithubOAuthToken.class);
			}

		}catch (Exception e){
			throw new AccessTokenException(text , e);
		}
		if (githubOAuthToken == null){
			throw new AccessTokenException(String.format("无法获取第三方%s token" , NAME));
		}
		if (BAD_VERIFICATION_CODE.equals(githubOAuthToken.getError())){
			throw new InvalidCodeException(String.format("无效的的授权码 %s" , code));
		}
		if (!StringUtils.isEmpty(githubOAuthToken.getError())){
			throw new AccessTokenException(githubOAuthToken.getErrorDescription());
		}
		return githubOAuthToken;
	}
}
