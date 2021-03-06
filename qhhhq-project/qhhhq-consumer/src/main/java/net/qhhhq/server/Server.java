package net.qhhhq.server;

import java.net.InetSocketAddress;

public interface Server {
	public interface TransmissionProtocol{

    }

    // 服务器使用的协议
    public enum TRANSMISSION_PROTOCOL implements TransmissionProtocol {
        TCP,UDP
    }

    TransmissionProtocol getTransmissionProtocol();
    // 启动服务器
    public void startServer() throws Exception;

    public void startServer(int port) throws Exception;

    public void startServer(InetSocketAddress socketAddress) throws Exception;

    // 关闭服务器
    public void stopServer() throws Exception;

    public InetSocketAddress getSocketAddress();
}
