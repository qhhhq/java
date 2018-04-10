package net.qhhhq.service.http.qcloud;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

public class TunnelChannel {

	private static Map<String, ChannelHandlerContext> tunnelList = null;

	public static void addTunnelChannel(String tunnelId, ChannelHandlerContext ctx) {
		if(tunnelList == null) {
			tunnelList = new HashMap<String, ChannelHandlerContext>();
		}
		tunnelList.put(tunnelId, ctx);
	}

	public static void removeTunnelChannel(String tunnelId) {
		tunnelList.remove(tunnelId);
	}

	public static ChannelHandlerContext getChannel(String tunnelId) {
		return tunnelList.get(tunnelId);
	}
}
