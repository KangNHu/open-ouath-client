package org.codingeasy.oauth.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.exception.AccessTokenException;
import org.codingeasy.oauth.client.exception.CancelAuthorizeException;
import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.model.FacebookOAuthToken;
import org.codingeasy.oauth.client.model.OAuthToken;

import javax.servlet.http.HttpServletRequest;

/**
* facebook 第三方登录  
* @author : KangNing Hu
*/
public class FacebookOAuthClientHandler implements OAuthClientHandler {


	private static final String PARAMETER_ERROR_REASON = "error_reason";
	private static final String PARAMETER_ACCESS_DENIED = "access_denied";
	private static final String PARAMETER_ERROR_DESCRIPTION = "error_description";

	private final static String NAME = "facebook";

	@Override
	public String getName() {
		return NAME;
	}


	@Override
	public boolean isCancelAuthorize(HttpServletRequest request) {
		String errorReason = request.getParameter(PARAMETER_ERROR_REASON);
		String accessDenied = request.getParameter(PARAMETER_ACCESS_DENIED);
		String errorDescription = request.getParameter(PARAMETER_ERROR_DESCRIPTION);
		if (!StringUtils.isEmpty(errorReason) ||
		!StringUtils.isEmpty(accessDenied) ||
		!StringUtils.isEmpty(errorDescription)){
			throw new CancelAuthorizeException(String.format("取消%s授权,取消原因[errorReason:%s,accessDenied:%s,errorDescription:%s]",
					NAME ,errorReason , accessDenied , errorDescription));
		}
		return false;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		String text = null;
		FacebookOAuthToken facebookOAuthToken = null;
		try {
			text = sendAccessTokenFormRequest(properties, code);
			ObjectMapper objectMapper = new ObjectMapper();
			facebookOAuthToken = objectMapper.readValue(text, FacebookOAuthToken.class);
		}catch (Exception e){
			throw new AccessTokenException(text , e);
		}
		if (facebookOAuthToken == null || StringUtils.isEmpty(facebookOAuthToken.getToken())){
			throw new AccessTokenException(text);
		}
		return facebookOAuthToken;
	}
}
