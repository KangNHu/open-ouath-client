package org.codingeasy.oauth.client.exception;

/**
* 无效的code异常  
* @author : KangNing Hu
*/
public class InvalidCodeException extends AccessTokenException{

	public InvalidCodeException() {
	}

	public InvalidCodeException(String s) {
		super(s);
	}

	public InvalidCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCodeException(Throwable cause) {
		super(cause);
	}
}
