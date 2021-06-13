package org.codingeasy.oauth.client.handler;

import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.model.OAuthToken;
import org.codingeasy.oauth.client.utils.OAuthConfigUtils;
import org.codingeasy.oauth.client.utils.OKHttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
* qq 第三方登录
* @author : KangNing Hu
*/
public class QQOAuthClientHandler implements OAuthClientHandler{

	private final static String NAME = "qq";

	private final static String PARAMETER_DISPLAY = "display";
	private final static String PARAMETER_USERCANCEL = "usercancel";

	private final static String TOKEN_URL_TEMPLATE = "%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";


	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * 处理 qq授权界面的展示样式
	 * @param authorizeUrl 授权url
	 * @param oAuthProperties 授权配置属性
	 * @return
	 */
	@Override
	public String postAfterAuthorizeProcessor(String authorizeUrl, OAuthProperties oAuthProperties) {
		HashMap<String, String> extend = oAuthProperties.getExtend();
		String display = extend.get(PARAMETER_DISPLAY);
		if (!StringUtils.isEmpty(display)){
			return authorizeUrl +"&" + PARAMETER_DISPLAY + "=" +  display;
		}
		return authorizeUrl;
	}

	/**
	 * 处理用户取消第三方授权
	 * @param request 请求对象
	 * @return
	 */
	@Override
	public boolean isCancelAuthorize(HttpServletRequest request) {
		String usercancel = request.getParameter(PARAMETER_USERCANCEL);
		if (!StringUtils.isEmpty(usercancel) && "0".equals(usercancel)){
			return true;
		}
		return false;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		String tokenUrl = String.format(TOKEN_URL_TEMPLATE,
				properties.getClientId(),
				properties.getClientSecret(),
				code,
				OAuthConfigUtils.getCallbackUrl(properties)
		);
		String body = OKHttpUtils.get(tokenUrl);

		return null;
	}
}
