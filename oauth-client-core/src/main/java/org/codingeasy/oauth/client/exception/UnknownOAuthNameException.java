package org.codingeasy.oauth.client.exception;

/**
* 未知的授权名称  
* @author : KangNing Hu
*/
public class UnknownOAuthNameException extends OAuthException{

	public UnknownOAuthNameException() {
	}

	public UnknownOAuthNameException(String s) {
		super(s);
	}

	public UnknownOAuthNameException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownOAuthNameException(Throwable cause) {
		super(cause);
	}
}
