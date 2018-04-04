package net.qhhhq.server.netty.initializer;


import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.qhhhq.server.netty.handler.WXHttpsServerHandler;
import net.qhhhq.utils.SslUtil;

public class WXHttpsChannelInitializer extends ChannelInitializer<SocketChannel> {

	private String keyStorePath;
	private String keyPassword;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		SSLContext sslContext = SslUtil.createSSLContext("JKS",keyStorePath ,keyPassword);
	        SSLEngine sslEngine = sslContext.createSSLEngine();
	        sslEngine.setUseClientMode(false);
	        sslEngine.setNeedClientAuth(false);
			ChannelPipeline pipeline = ch.pipeline();
	        pipeline.addLast("ssl", new SslHandler(sslEngine));
		pipeline.addLast(new HttpServerCodec());
        pipeline.addLast( new HttpObjectAggregator(1024*100));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(
                new WXHttpsServerHandler());
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

}
