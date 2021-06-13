package org.codingeasy.oauth.client.exception;

/**
* 非法的状态  
* @author : KangNing Hu
*/
public class IllegitimateStateException extends OAuthException {

	public IllegitimateStateException() {
	}

	public IllegitimateStateException(String s) {
		super(s);
	}

	public IllegitimateStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegitimateStateException(Throwable cause) {
		super(cause);
	}
}
