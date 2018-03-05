package net.qhhhq.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonResponseHandler
  implements ResponseHandler<JSONObject>
{
  private Logger log = Logger.getLogger(JsonResponseHandler.class);

  public JSONObject handleResponse(HttpResponse response)
    throws ClientProtocolException, IOException
  {
    StatusLine statusLine = response.getStatusLine();
    HttpEntity entity = response.getEntity();
    if (statusLine.getStatusCode() >= 300) {
      throw new HttpResponseException(statusLine.getStatusCode(),
        statusLine.getReasonPhrase());
    }
    if (entity == null)
    {
      this.log.error("请求未收到响应数据");
      throw new ClientProtocolException("data is null！");
    }
    ContentType contentType = ContentType.getOrDefault(entity);
    Charset charset = contentType.getCharset();
    this.log.info(charset);
    Reader reader = new InputStreamReader(entity.getContent(), "UTF-8");
    JSONObject object = null;
    try
    {
      object = new JSONObject(new JSONTokener(reader));
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return object;
  }
}
