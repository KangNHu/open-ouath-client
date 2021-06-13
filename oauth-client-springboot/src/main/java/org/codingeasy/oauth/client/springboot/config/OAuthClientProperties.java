package org.codingeasy.oauth.client.springboot.config;

import org.codingeasy.oauth.client.OAuthProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;


/**
* oauth配置  
* @author : KangNing Hu
*/
public class OAuthClientProperties {


	@NestedConfigurationProperty
	List<OAuthProperties> properties = new ArrayList<>();

	public List<OAuthProperties> getProperties() {
		return properties;
	}

	public void setProperties(List<OAuthProperties> properties) {
		this.properties = properties;
	}
}
