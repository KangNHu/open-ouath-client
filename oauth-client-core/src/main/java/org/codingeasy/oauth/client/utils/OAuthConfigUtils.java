package org.codingeasy.oauth.client.utils;


import org.codingeasy.oauth.client.handler.*;

import java.util.ArrayList;
import java.util.List;

/**
* ouath 配置 工具类  
* @author : KangNing Hu
*/
public class OAuthConfigUtils {

	/**
	 * 获取默认的授权处理器
	 * <ul>
	 *     <li>giee</li>
	 *     <li>知乎</li>
	 *     <li>qq</li>
	 *     <li>新浪微博</li>
	 *     <li>微信</li>
	 *     <li>faceboot</li>
	 *     <li>cas (耶鲁大学开源SSO项目 )</li>
	 *     <li>google</li>
	 * </ul>
	 * @return 返回默认的授权处理器
	 */
	public static List<OAuthClientHandler> getDefaultOAuthClientHandlers(){
		List<OAuthClientHandler> oAuthClientHandlers = new ArrayList<>();
		oAuthClientHandlers.add(new GiteeOauthClientHandler());
		oAuthClientHandlers.add(new ZhihuOAuthClientHandler());
		oAuthClientHandlers.add(new QQOAuthClientHandler());
		oAuthClientHandlers.add(new WeiboOAuthClientHandler());
		oAuthClientHandlers.add(new WechatOAuthClientHandler());
		oAuthClientHandlers.add(new FacebookOAuthClientHandler());
		oAuthClientHandlers.add(new CasOAuthClientHandler());
		oAuthClientHandlers.add(new GoogleOAuthClientHandler());
		return oAuthClientHandlers;
	}


}
