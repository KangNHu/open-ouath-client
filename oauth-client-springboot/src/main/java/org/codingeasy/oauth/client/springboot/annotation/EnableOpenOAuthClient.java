package org.codingeasy.oauth.client.springboot.annotation;

import org.codingeasy.oauth.client.springboot.config.OpenOAuthClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
* 激活oauth 授权客户端  
* @author : KangNing Hu
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(OpenOAuthClientAutoConfiguration.class)
@Documented
public @interface EnableOpenOAuthClient {
}
