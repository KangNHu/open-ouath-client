package org.codingeasy.oauth.client;

/**
 * xsrf token生成器
 * @author hukangning
 */
public interface XsrfTokenGenerator {

	/**
	 * 生成
	 * @return 返回xsrf token 用于sate
	 */
	String generate();
}
