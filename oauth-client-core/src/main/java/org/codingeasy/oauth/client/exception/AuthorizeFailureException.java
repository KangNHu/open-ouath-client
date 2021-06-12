package org.codingeasy.oauth.client.exception;

/**
* 授权失败异常  
* @author : KangNing Hu
*/
public class AuthorizeFailureException extends OAuthException {
	public AuthorizeFailureException() {
	}

	public AuthorizeFailureException(String s) {
		super(s);
	}

	public AuthorizeFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizeFailureException(Throwable cause) {
		super(cause);
	}
}
