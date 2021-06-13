package org.codingeasy.oauth.client.utils;


import org.codingeasy.oauth.client.OAuthProperties;
import org.codingeasy.oauth.client.handler.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* ouath 配置 工具类  
* @author : KangNing Hu
*/
public class OAuthConfigUtils {

	private static final String CALLBACK_URL = "callback_url";


	/**
	 * 获取回调url
	 * @param oAuthProperties oauth 配置对象
	 * @return 返回回调url 如果为空则返回null
	 */
	public static String getCallbackUrl(OAuthProperties oAuthProperties){
		HashMap<String, String> extend = oAuthProperties.getExtend();
		if (extend == null){
			return null;
		}
		return extend.get(CALLBACK_URL);
	}


	/**
	 * 设置回调url
	 * @param oAuthProperties oauth 配置对象
	 * @param callbackUrl 回调url
	 */
	public static void setCallbackUrl(OAuthProperties oAuthProperties , String callbackUrl){
		HashMap<String, String> extend = oAuthProperties.getExtend();
		if (extend == null){
			extend = new HashMap<>();
			oAuthProperties.setExtend(extend);
		}
		extend.put(CALLBACK_URL , callbackUrl);
	}

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
