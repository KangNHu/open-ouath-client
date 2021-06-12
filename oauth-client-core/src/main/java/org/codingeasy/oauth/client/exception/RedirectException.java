package org.codingeasy.oauth.client.exception;

/**
* 重定向异常  
* @author : KangNing Hu
*/
public class RedirectException  extends OAuthException{

	public RedirectException() {
	}

	public RedirectException(String s) {
		super(s);
	}

	public RedirectException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedirectException(Throwable cause) {
		super(cause);
	}
}
