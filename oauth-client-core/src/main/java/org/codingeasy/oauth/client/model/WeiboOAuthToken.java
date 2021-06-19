package org.codingeasy.oauth.client.model;


/**
* 微博的oauth token  
* @author : KangNing Hu
*/
public class WeiboOAuthToken extends   SimpleOAuthToken {



	private String uid;


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
