package middleware.httpclient;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Title: HttpClientsDemo
 * 使用apache HttpClient
 */
public class HttpClientsDemo {
	static String doGet(String url, Map<String, String> map) throws URISyntaxException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		URIBuilder builder = new URIBuilder(url);
		if (!MapUtils.isEmpty(map)) {
			Set<Map.Entry<String, String>> entries = map.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				builder.addParameter(entry.getKey(), entry.getValue());
			}
		}
		URI uri = builder.build();

		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		String result = null;
		if (response.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
		}
		response.close();
		httpClient.close();
		return result;
	}

	static String doPost(String url, Map<String, String> map) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> paramList = new ArrayList<>();
		if (!MapUtils.isEmpty(map)) {
			Set<Map.Entry<String, String>> entries = map.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
			httpPost.setEntity(entity);
		}
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
		response.close();
		httpClient.close();
		return result;
	}

	static String doPostJson(String url, String jsonStr) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
		response.close();
		httpClient.close();
		return result;
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		String getUrl = "http://fanyi.youdao.com/openapi.do?keyfrom=wjy-test&key=36384249&type=data&doctype=jsonp&callback=show&version=1.1&q=hello";
		String result = doGet(getUrl, null);
		System.out.println(result);

		String postUrl = "http://fanyi.youdao.com/openapi.do";
		Map<String, String> map = new HashMap<>();
		map.put("keyfrom", "wjy-test");
		map.put("key", "36384249");
		map.put("type", "data");
		map.put("doctype", "xml");
		map.put("version", "1.1");
		map.put("q", "welcome");
		String postResponse = doPost(postUrl, map);
		System.out.println(postResponse);


		String postJsonUrl = "http://fanyi.youdao.com/openapi.do";
		String jsonStr = JSON.toJSONString(map);
		String postJsonResult = doPostJson(postJsonUrl, jsonStr);
		System.out.println(postJsonResult);
	}
}
