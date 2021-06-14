package org.codingeasy.oauth.client.exception;

/**
* 获取第三方token异常  
* @author : KangNing Hu
*/
public class AccessTokenException extends OAuthException {

	public AccessTokenException() {
	}

	public AccessTokenException(String s) {
		super(s);
	}

	public AccessTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessTokenException(Throwable cause) {
		super(cause);
	}
}
