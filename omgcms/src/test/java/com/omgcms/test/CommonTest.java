package com.omgcms.test;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.omgcms.util.CmsUtil;

public class CommonTest {
	
	private static final String UUM_URL = null;

	private Logger logger = LoggerFactory.getLogger(CommonTest.class);
	
	private ApplicationContext ctx = null;
	private EntityManagerFactory entityManagerFactory;

	{
		ctx = new ClassPathXmlApplicationContext(
				new String[] { "config/spring-jpa.xml", "config/spring-context.xml", "config/spring-shiro.xml" });
		entityManagerFactory = ctx.getBean(EntityManagerFactory.class);
	}
	
	@Test
	public void initDBTest() throws SQLException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println(entityManager);
		entityManager.close();
	}
	
	@Test
	public void encryptPassword() {
		String password = new SimpleHash("md5", "123456", null, 2).toHex();
		System.out.println(password);
		String password_cipherText= new Md5Hash("123456",ByteSource.Util.bytes("cmsOmg"),2).toHex(); 
		System.out.println(password_cipherText);
		String password2 = new SimpleHash("md5", "123456", ByteSource.Util.bytes("cmsOmg"), 2).toHex();
		System.out.println(password2);
	}
	
	@Test
	public void testMap() throws ClientProtocolException, IOException {
		String url= "http://127.0.0.1:8088/RestProject/services/user";
		
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
		CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

		// String createUserUrl = UUM_URL + "/rest/user/createUser";
		
		HttpPost httpPost = new HttpPost(url);
		
		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("userid", "AAAAAAA");
		mapData.put("username", "BBB");
		mapData.put("password", "CCC");
		mapData.put("liferayUid", "DDDDDD");
		
		StringEntity sEntry = new StringEntity(CmsUtil.objectToJsonString(mapData), "UTF-8");
		sEntry.setContentEncoding("UTF-8");
		sEntry.setContentType("application/json");

		httpPost.setEntity(sEntry);

		try {

			CloseableHttpResponse response = httpclient.execute(httpPost);

			int status = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			ContentType contentType = ContentType.getOrDefault(entity);
			String content = EntityUtils.toString(entity, contentType.getCharset());
			
			if(content.indexOf("true")!=-1){
				return;
			}
			logger.debug("STATUS:{}", status);
			logger.debug("content:{}", content);

			return;

		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.toString());
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("Close httpclient failed. " + e.toString());
			}
		}
		
	}
	
	@Test
	public void testParam() throws ClientProtocolException, IOException {
		
		String url = "http://127.0.0.1:8088/omgcms/index.jhtml";
		
		String usernamePassword = "yangdey:hirisun2016";

		HttpPost httpPost = new HttpPost(url);
		String base64Encoded = DatatypeConverter.printBase64Binary(usernamePassword.getBytes("UTF-8"));
		httpPost.setHeader("Authorization", "Basic " + base64Encoded);

		ContentType text_plain = ContentType.create("text/plain", Charset.forName("UTF-8"));
		ContentType app_json = ContentType.create("application/json", Consts.UTF_8);


		MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

		// 设置为浏览器兼容模式
		entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		// 设置请求的编码格式
		entityBuilder.setCharset(Charset.forName("UTF-8"));

		// 可使用 InputSream、byte[]参数，可直接读取远程文件
		// entityBuilder.setContentType(ContentType.APPLICATION_FORM_URLENCODED);
		
		entityBuilder.addTextBody("text", "测试text 11", text_plain);
		entityBuilder.addTextBody("title", "测试text title", text_plain);
		
		
		
		// 其他详细字段名称需要根据 Liferay 内容管理 结构对应字段设置
		Map<String, Serializable> contentMap = new HashMap<String, Serializable>();
		// contentMap.put("author", "作者");
		// contentMap.put("subTitle", "副标题");
		// contentMap.put("sescription", "摘要");
		// contentMap.put("content", "内容");
		contentMap.put("projectName", "项目名称11");

		String contentMapString =CmsUtil.objectToJsonString(contentMap);
		
		entityBuilder.addTextBody("contentMap", contentMapString, app_json);
		
		HttpEntity httpEntity = entityBuilder.build();
		
		httpPost.setEntity(httpEntity);
		
		logger.debug("httpEntity ContentType:",httpEntity.getContentType().getValue());

		CloseableHttpResponse response = (CloseableHttpResponse) HttpClientBuilder.create().build().execute(httpPost);

		int status = response.getStatusLine().getStatusCode();
		HttpEntity entity = response.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
		String content = EntityUtils.toString(entity, contentType.getCharset());

		logger.debug("status:[{}] content:[{}]", status, content);

	}

}
