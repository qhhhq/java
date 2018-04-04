package net.qhhhq.server.netty.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.qhhhq.server.netty.handler.HttpServerHandler;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
        pipeline.addLast( new HttpObjectAggregator(1024*100));
        pipeline.addLast(new ChunkedWriteHandler());
//      pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(
                new HttpServerHandler());
	}


}
