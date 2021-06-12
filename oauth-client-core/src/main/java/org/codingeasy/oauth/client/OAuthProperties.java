package org.codingeasy.oauth.client;

/**
* oauth授权配置类  
* @author : KangNing Hu
*/
public class OAuthProperties implements Cloneable{


	/**
	 * 授权名称
	 */
	private String name;

	/**
	 * 是否开启跨域站点token
	 */
	private boolean enableXsrfToken;

	/**
	 * 第三方授权url
	 */
	private String authorizeUrl;

	/**
	 * 第三方获取tokenUrl
	 */
	private String accessTokenUrl;

	/**
	 * 第三方client id
	 */
	private String clientId;

	/**
	 * 第三方client secret
	 */
	private String clientSecret;

	/**
	 * 授权范围
	 */
	private String scope;


	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnableXsrfToken() {
		return enableXsrfToken;
	}

	public void setEnableXsrfToken(boolean enableXsrfToken) {
		this.enableXsrfToken = enableXsrfToken;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "OAuthProperties{" +
				"name='" + name + '\'' +
				", enableXsrfToken=" + enableXsrfToken +
				", authorizeUrl='" + authorizeUrl + '\'' +
				", accessTokenUrl='" + accessTokenUrl + '\'' +
				", clientId='" + clientId + '\'' +
				", clientSecret='" + clientSecret + '\'' +
				", scope='" + scope + '\'' +
				'}';
	}
}
