package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* 码云token  
* @author : KangNing Hu
*/
public class GiteeOAuthToken extends SimpleOAuthToken {


	@JsonProperty("created_at")
	private Long createdAt;


	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
}
