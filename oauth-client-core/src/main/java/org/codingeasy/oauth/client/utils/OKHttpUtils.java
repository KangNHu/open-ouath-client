package org.codingeasy.oauth.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;

import javax.xml.stream.events.Characters;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * okhttp 工具类
 *
 * @author : KangNing Hu
 */
public class OKHttpUtils {

	private static OkHttpClient client = new OkHttpClient();
	private static MediaType JSON = MediaType.parse("application/json;charset=utf-8");
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

	/**
	 * form 提交
	 * @param url 请求url
	 * @param params form表单参数
	 * @return 返回响应结果
	 */
	public static String form(String url , Map<String , Object> params){
		try {
			List<String> encodedNames = new ArrayList<>();
			List<String> encodedValues = new ArrayList<>();
			if (!MapUtils.isEmpty(params)) {
				for (Map.Entry<String, Object> entry : params.entrySet()){
					encodedNames.add(URLEncoder.encode(entry.getKey() , "UTF-8"));
					Object value = entry.getValue();
					encodedValues.add(URLEncoder.encode(value == null ? "" : value.toString() , "UTF-8"));
				}
			}
			Request request = new Request.Builder()
					.url(url)
					.post(new FormBody(encodedNames , encodedValues))
					.build();
			Call call = client.newCall(request);
			Response response = call.execute();
			return response.body().string();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}


	/**
	 * form 提交
	 * @param url 请求url
	 * @param params form表单参数
	 * @return 返回响应结果
	 */
	public static String post(String url , Map<String , Object> params){
		try {
			if (params == null){
				params = new HashMap<>();
			}
			ObjectMapper objectMapper = new ObjectMapper();
			RequestBody requestBody = RequestBody.create(JSON, objectMapper.writeValueAsString(params));
			Request request = new Request.Builder()
					.url(url)
					.post(requestBody)
					.build();
			Call call = client.newCall(request);
			Response response = call.execute();
			return response.body().string();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
