package org.codingeasy.oauth.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.exception.AuthorizeFailureException;
import org.codingeasy.oauth.client.exception.OAuthException;
import org.codingeasy.oauth.client.model.GoogleOAuthToken;
import org.codingeasy.oauth.client.model.OAuthToken;

import javax.servlet.http.HttpServletRequest;

/**
* 谷歌第三方登录  
* @author : KangNing Hu
*/
public class GoogleOAuthClientHandler implements OAuthClientHandler{

	private static final String NAME = "google";

	/**
	 * 指示当用户不在浏览器时您的应用程序是否可以刷新访问令牌。有效参数值为online ，这是默认值，以及offline 。
	 *
	 * 如果您的应用程序需要在用户不在浏览器中时刷新访问令牌，则将该值设置为offline 。这是本文档后面描述的刷新访问令牌的方法。该值指示 Google 授权服务器在您的应用程序第一次将授权代码交换为令牌时返回刷新令牌和访问令牌。
	 */
	private static final String PARAMETER_ACCESS_TYPE = "access_type";

	/**
	 * 使应用程序能够使用增量授权来请求访问上下文中的其他范围。如果您将此参数的值设置为true并授予授权请求，则新的访问令牌还将涵盖用户先前授予应用程序访问权限的任何范围。有关示例，请参阅增量授权部分。
	 */
	private static final String PARAMETER_INCLUDE_GRANTED_SCOPES = "include_granted_scopes";

	/**
	 * 如果您的应用程序知道哪个用户正在尝试进行身份验证，它可以使用此参数向 Google 身份验证服务器提供提示。服务器通过在登录表单中预填电子邮件字段或选择适当的多登录会话，使用提示来简化登录流程。
	 *
	 * 将参数值设置为电子邮件地址或sub标识符，相当于用户的 Google ID。
	 */
	private static final String PARAMETER_LOGIN_HINT = "login_hint";

	/**
	 * 以空格分隔、区分大小写的提示列表，以呈现给用户。如果您不指定此参数，则仅在您的项目第一次请求访问时才会提示用户。有关更多信息，请参阅提示重新同意。
	 *
	 * 可能的值为：
	 *
	 * none	不显示任何身份验证或同意屏幕。不得与其他值一起指定。
	 * consent	提示用户同意。
	 * select_account	提示用户选择一个帐户。
	 */
	private static final String PARAMETER_PROMPT = "prompt";


	private static final String PARAMETER_ERROR = "error";

	@Override
	public String getName() {
		return NAME;
	}


	@Override
	public String postAfterAuthorizeProcessor(String authorizeUrl, OAuthProperties oAuthProperties) {
		return appendParam(authorizeUrl ,oAuthProperties.getExtend(), PARAMETER_ACCESS_TYPE, PARAMETER_INCLUDE_GRANTED_SCOPES,
				PARAMETER_LOGIN_HINT ,PARAMETER_PROMPT );
	}


	@Override
	public boolean isCancelAuthorize(HttpServletRequest request) {
		String error = request.getParameter(PARAMETER_ERROR);
		if (!StringUtils.isEmpty(error)){
			throw new AuthorizeFailureException(String.format("授权失败 %s" , error));
		}
		return false;
	}

	@Override
	public OAuthToken createToken(OAuthProperties properties, String code) {
		String text = null;
		try {
			text = sendAccessTokenFormRequest(properties, code);
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(text , GoogleOAuthToken.class);
		}catch (Exception e){
			throw new OAuthException(text ,e);
		}
	}
}
