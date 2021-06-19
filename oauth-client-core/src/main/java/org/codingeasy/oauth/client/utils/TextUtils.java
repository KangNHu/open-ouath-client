package org.codingeasy.oauth.client.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
*   文本相关工具类
* @author : KangNing Hu
*/
public class TextUtils {


	private final static char FORM_BODY_SEPARATOR = '&';
	private final static char FORM_BODY_MAPPING = '=';
	/**
	 * 校验是否是json
	 * @param context 待校验内容
	 * @return 如果是json则返回true否则false
	 */
	public static boolean validJson(final String context) {
		try {
			JsonParser parser = new ObjectMapper().getJsonFactory()
					.createParser(context);
			for (;parser.nextToken() != null;){}
			return true;
		} catch (Exception jpe) {
			return false;
		}
	}

	/**
	 * form响应体转换为实体
	 * <p>将 p1=222&p2=333 转换为实体</p>
	 * @param context 待转换文本
	 * @param tClass 转换的class
	 * @param <T> 转换的实体
	 * @return
	 */
	public static <T> T formBodyToObject(final String context , Class<T>  tClass){
		if (StringUtils.isEmpty(context)){
			return null;
		}
		//将p1=222&p2=333格式数据转换为json
		StringBuilder json = new StringBuilder();
		json.append("{\n");
		char[] chars = context.toCharArray();
		for (int i = 0 ,l = chars.length ; i < l ; i ++){
			if (i == 0){
				json.append("\"");
			}
			if (chars[i] == FORM_BODY_SEPARATOR){
				json.append("\",\n");
				json.append("\"");
			}else if (chars[i] == FORM_BODY_MAPPING){
				json.append("\":\"");
			}else {
				json.append(chars[i]);
				if (i == l -1){
					json.append("\"");
				}
			}
		}
		json.append("}");
		//将json转换为实体
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json.toString() , tClass);
		}catch (Exception e){
			throw new IllegalStateException(e);
		}
	}




	public static void main(String[] args) throws JsonProcessingException {
		final Map map = formBodyToObject("ssss=111&sssccc=222&ddd=555", Map.class);
		final String s = new ObjectMapper().writeValueAsString(map);

		final boolean b = validJson(s);
	}
}
