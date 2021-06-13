package org.codingeasy.oauth.client.utils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp 工具类
 *
 * @author : KangNing Hu
 */
public class OKHttpUtils {

	private static OkHttpClient client = new OkHttpClient();


	/**
	 * get请求
	 * @param url 请求url
	 * @return 返回响应数据
	 */
	public static String  get(String url) {
		try {
			Request request = new Request.Builder()
					.url(url)
					.get()
					.build();
			Call call = client.newCall(request);
			Response response = call.execute();
			return response.body().string();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
