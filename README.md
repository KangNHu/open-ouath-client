# open-ouath-client

#### 介绍
##### 一个第三方OAuth2协议的授权框架
- 内置支持的第三方登录
  -  giee
  -  github
  -  知乎
  -  qq
  -  新浪微博
  -  微信
  -  facebook
  -  cas (耶鲁大学开源SSO项目 )
  -  google 
- 支持用户自定义扩展第三方登录方式
- 支持自定义第三方token和业务系统token绑定  
#### 项目结构

- oauth-client-core 核心包，主要处理第Oauth授权
- oauth-client-springboot 和spring boot集成
- oauth-client-example 示例工程

#### 第三方依赖说明

| groupId                    | artifactId           | 版本          |
| -------------------------- | -------------------- | ------------- |
| org.apache.commons         | commons-lang3        | 3.11          |
| org.apache.commons         | commons-collections4 | 4.4           |
| com.squareup.okhttp3       | okhttp               | 4.9.1         |
| org.springframework.boot   |                      | 2.2.0.RELEASE |
| com.fasterxml.jackson.core |                      | 2.9.6         |
| javax.servlet              | servlet-api          | 2.5           |



#### 使用说明

##### 快速开始

###### 启动类

激活oauth client 客户端

```
@EnableOpenOAuthClient
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class , args);
	}
}
```

###### 实现登录处理器

```java
@Component
public class SimpleLoginHandler implements LoginHandler {



	@Override
	public void doLogin(HttpServletResponse response, HttpServletRequest request, OAuthToken token)  {
			//用于处理第三方token和业务token进行绑定，用户校验等逻辑 ，还可以做一些登录成功后重定义工作
	}

	@Override
	public void dofFailure(OAuthException e, HttpServletResponse response, HttpServletRequest request) {
		// 用于处理授权中异常 如 第三方授权失败 ，无效的code，获取token失败 等等
	}
}
```

###### 添加配置

application.yml

```yaml
open:
  oauth2:
    client:
      properties[0]:
        name: qq # 第三方名称
        authorize-url: https://graph.qq.com/oauth2.0/authorize # 授权url
        access-token-url: https://graph.qq.com/oauth2.0/token # 获取token url
        client-id: xxxxxx # 在第三方申请的appId
        client-secret: xxxxxxxx #在第三方申请的密钥
      properties[1]: # 如果要支持多个 以改种格式进行添加即可
        name: gitee
        authorize-url: https://gitee.com/oauth/authorize
        access-token-url: https://gitee.com/oauth/token
        client-id: xxxxx
        client-secret: xxxxx
```

###### 在第三方配置回调url

配置格式为 {http/https}://{domain}/{rootPath}/oauth2/call

###### 前端登录入口

```html
<a href="{http/https}://{domain}/{rootPath}/oauth2/login?oauth-name={第三方名称}"></a>
```

##### 内置第三方名称<a id = "oauth-name"></a>

| 名称（oauth-name） | 说明                    |
| ------------------ | ----------------------- |
| cas                | 耶鲁大学开源项目（SSO） |
| facebook           | Facebook(脸书)          |
| gitee              | 码云                    |
| github             | Github                  |
| google             | 谷歌                    |
| qq                 | 腾讯QQ                  |
| wechat             | 微信                    |
| weibo              | 新浪微博                |
| zhihu              | 知乎                    |

##### 两个重要的Path说明

**/oauth2/login**

- 登录url path
- 参数
  - oauth-name：类型为query, 标示第三方登录名称 ，这里可以参考 [点击前往](#oauth-name)

**/oauth2/call**

- 第三方回调path
- 目前设计为常量，暂时不支持自定义配置

**注意**

<u>保证系统的其他请求路径不要和这两个路径一样，否则会导致冲突</u>

##### 配置属性说明

| 属性名称        | 说明                                                         | 是否必要 |
| --------------- | ------------------------------------------------------------ | -------- |
| name            | 第三方名称                                                   | 是       |
| enableXsrfToken | 是否开启跨站点攻击token 默认开启，部分第三方强制需要开启     | 否       |
| authorizeUrl    | 第三方授权url                                                | 是       |
| accessTokenUrl  | 第三方获取token url                                          | 是       |
| clientId        | 第三方申请的 clientId                                        | 是       |
| clientSecret    | 第三方申请的clientSecret                                     | 是       |
| scope           | 授权范围，根据第三方定义的范围进行配置，没有特殊情况下不需要配置 | 否       |
| extend          | 扩展参数 为一个map结构 有些第三方会加一些自定义的授权参数    |          |

##### 重要的方法说明

- OAuthClientHandler#sendAccessTokenFormRequest 向第三方发起获取token的请求 基于 form表达的方式
- OAuthClientHandler#sendAccessTokenGetRequest 向第三方发起获取token的请求基于 query的方式

##### 扩展

###### 自定义xsrf token生成器

实现`XsrfTokenGenerator`接口

```
public class  DefaultXsrfTokenGenerator implements XsrfTokenGenerator{

		@Override
		public String generate() {
			//生成 state 参数值
		}
	}
```

###### 自定义第三方登录

1. 实现接口`OAuthClientHandler`

   ```java
   public class MyAuthClientHandler implements OAuthClientHandler{
   
   	
   	@Override
   	public String getName() {
   		// 返回第三方名称 ，这里的名称要和配置的{name} 一致
   	}
   
   	// 返回的是授权的url
   	@Override
   	public String postAfterAuthorizeProcessor(String authorizeUrl, OAuthProperties oAuthProperties) {
   		//处理第三方自定义参数
   	}
   
   	/**
   	 * 处理用户取消第三方授权
   	 * @param request 请求对象
   	 * @return
   	 */
   	@Override
   	public boolean isCancelAuthorize(HttpServletRequest request) {
   		// 处理用户取消第三方授权处理
   	}
   
   	@Override
   	public OAuthToken createToken(OAuthProperties properties, String code) {
   		// 向第三方发起获取token的请求
       // 创建一个第三方token 
   	}
   }
   ```

2. 注册 handler 直接将自定义的实现类对象注入到spring 容器中即可



#### 参与贡献

1.  Fork 本仓库
2.  新建 dev_xxx 分支
3.  提交代码
4.  新建 Pull Request
