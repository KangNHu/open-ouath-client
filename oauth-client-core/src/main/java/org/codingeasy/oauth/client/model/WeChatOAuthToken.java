package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* 微信获取token响应  
* @author : KangNing Hu
*/
public class WeChatOAuthToken implements OAuthToken {


	@JsonProperty("access_token")
	private String token;


	@JsonProperty("expires_in")
	private Long effectiveTime;


	@JsonProperty("refresh_token")
	private String refreshToken;

	private String errcode;

	private String errmsg;

	private String openid;


	private String scope;


	private String unionid;


	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	@Override
	public String getRefreshToken() {
		return this.refreshToken;
	}

	@Override
	public long getEffectiveTime() {
		return this.effectiveTime;
	}
}
