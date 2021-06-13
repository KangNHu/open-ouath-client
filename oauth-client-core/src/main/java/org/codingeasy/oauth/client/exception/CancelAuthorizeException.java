package org.codingeasy.oauth.client.exception;

/**
* 取消授权异常  
* @author : KangNing Hu
*/
public class CancelAuthorizeException extends OAuthException {

	public CancelAuthorizeException() {
	}

	public CancelAuthorizeException(String s) {
		super(s);
	}

	public CancelAuthorizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CancelAuthorizeException(Throwable cause) {
		super(cause);
	}
}
