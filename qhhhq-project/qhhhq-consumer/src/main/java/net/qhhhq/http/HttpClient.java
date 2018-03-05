package net.qhhhq.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

public class HttpClient {
	public static Object doGet(String uri, Map<String, String> params, ResponseHandler handler)
			throws URISyntaxException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(uri);
		if ((params != null) && (params.size() > 0)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				uriBuilder.addParameter((String) entry.getKey(), (String) entry.getValue());
			}
		}
		HttpGet httpGet = new HttpGet(uriBuilder.build().toString());
		Object obj = httpclient.execute(httpGet, handler);
		return obj;
	}

	public static Object doPost(String uri, Map<String, String> params, String json, ResponseHandler handler)
			throws URISyntaxException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder uriBuilder = new URIBuilder(uri);
		if ((params != null) && (params.size() > 0)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				uriBuilder.addParameter((String) entry.getKey(), (String) entry.getValue());
			}
		}
		HttpPost httpPost = new HttpPost(uriBuilder.build().toString());
		StringEntity entity = new StringEntity(json, Charset.forName("utf-8"));
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json; charset=UTF-8");
		entity.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
		httpPost.setHeader("Content-Type", "Content-Type: text/html; charset=UTF-8");
		httpPost.setEntity(entity);
		Object obj = httpclient.execute(httpPost, handler);
		return obj;
	}
}
