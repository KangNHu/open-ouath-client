package org.codingeasy.oauth.client.exception;

/**
* ouath异常  
* @author : KangNing Hu
*/
public class OAuthException extends IllegalStateException{

	public OAuthException() {
	}

	public OAuthException(String s) {
		super(s);
	}

	public OAuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public OAuthException(Throwable cause) {
		super(cause);
	}
}
