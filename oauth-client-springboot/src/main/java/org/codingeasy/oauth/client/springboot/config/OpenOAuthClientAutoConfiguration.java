package org.codingeasy.oauth.client.springboot.config;

import org.apache.commons.collections4.CollectionUtils;
import org.codingeasy.oauth.client.OAuthClientFilter;
import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.XsrfTokenGenerator;
import org.codingeasy.oauth.client.handler.LoginHandler;
import org.codingeasy.oauth.client.handler.OAuthClientHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* 自动装配  
* @author : KangNing Hu
*/
public class OpenOAuthClientAutoConfiguration {



	@ConfigurationProperties("open.oauth2.client")
	@Bean
	public OAuthClientProperties oAuthClientProperties(){
		return new OAuthClientProperties();
	}



	@Bean
	public List<OAuthProperties> oAuthProperties(OAuthClientProperties properties){
		return properties.getProperties();
	}


	@Bean
	public FilterRegistrationBean<OAuthClientFilter> oAuthClientFilter(@Autowired(required = false)XsrfTokenGenerator xsrfTokenGenerator,
	                                                                   @Autowired(required = false) List<OAuthClientHandler> oAuthClientHandlers,
	                                                                   @Autowired(required = false) List<OAuthProperties> oAuthProperties,
	                                                                   LoginHandler loginHandler){
		FilterRegistrationBean<OAuthClientFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		filterRegistrationBean.setFilter(createFilter(xsrfTokenGenerator, oAuthClientHandlers, oAuthProperties, loginHandler));
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
		return filterRegistrationBean;
	}

	/**
	 * 创建oauth client 拦截器
	 * @param xsrfTokenGenerator xsrf token 生成器
	 * @param oAuthClientHandlers oauth client 处理器列表
	 * @param oAuthProperties oauth 配置列表
	 * @param loginHandler 登录处理器
	 * @return
	 */
	private OAuthClientFilter createFilter(XsrfTokenGenerator xsrfTokenGenerator ,
	                                       List<OAuthClientHandler> oAuthClientHandlers ,
	                                       List<OAuthProperties> oAuthProperties ,
	                                       LoginHandler loginHandler) {

		OAuthClientFilter oAuthClientFilter;
		if (xsrfTokenGenerator == null && CollectionUtils.isEmpty(oAuthClientHandlers)){
			oAuthClientFilter = new OAuthClientFilter(oAuthProperties , loginHandler);
		}
		else if (!CollectionUtils.isEmpty(oAuthClientHandlers)){
			oAuthClientFilter = new OAuthClientFilter(oAuthClientHandlers , oAuthProperties , loginHandler);
		}else {
			oAuthClientFilter = new OAuthClientFilter(oAuthClientHandlers , oAuthProperties , xsrfTokenGenerator , loginHandler);
		}
		return oAuthClientFilter;
	}
}
