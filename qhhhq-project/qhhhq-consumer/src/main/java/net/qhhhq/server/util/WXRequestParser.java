package net.qhhhq.server.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

/**
 * 解析http请求中的请求数据
 * @author bankqh-ldr
 *
 */
public class WXRequestParser {

	private static Logger logger = Logger.getLogger(WXRequestParser.class);
	private FullHttpRequest fullReq;

    /**
     * 构造一个解析器
     * @param req
     */
    public WXRequestParser(FullHttpRequest req) {
        this.fullReq = req;
    }

    /**
     * 解析请求参数
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     *
     * @throws BaseCheckedException
     * @throws IOException
     */
    public Map<String, Object> parse() throws IOException {
        HttpMethod method = fullReq.method();

        Map<String, Object> parmMap = new HashMap<String, Object>();

        if (HttpMethod.GET == method) {
        	QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
            Map<String, List<String>> parame = decoder.parameters();
            Iterator<Entry<String, List<String>>> iterator = parame.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<String, List<String>> next = iterator.next();
                parmMap.put(next.getKey().toUpperCase(), next.getValue().get(0));
            }
        } else if (HttpMethod.POST == method) {
            // 是POST请求
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);
            decoder.offer(fullReq);
            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
            for (InterfaceHttpData parm : parmList) {
            	if(parm.getHttpDataType() == HttpDataType.Attribute) {
            		Attribute data = (Attribute) parm;
	                parmMap.put("DATA", data.toString());
            	}
            }
        }
        return parmMap;
    }
}
