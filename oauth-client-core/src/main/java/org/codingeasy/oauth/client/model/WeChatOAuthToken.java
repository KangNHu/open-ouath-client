package org.codingeasy.oauth.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* 微信获取token响应  
* @author : KangNing Hu
*/
public class WeChatOAuthToken extends SimpleOAuthToken {


	private String errcode;

	private String errmsg;

	private String openid;


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


	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}
