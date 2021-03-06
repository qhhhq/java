package netty.http;

import java.io.File;
import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qcloud.weapp.ConfigurationManager;

import net.qhhhq.global.auth.factory.AuthFactory;
import net.qhhhq.global.service.config.factory.ServiceConfigFactory;
import net.qhhhq.server.netty.AbstractNettyServer;

public class HttpServerTest {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println(context.getDisplayName() + ": here");
		context.start();
		File file = new File(HttpServerTest.class.getClass().getResource("/auth.xml").getPath());
		File fileService = new File(HttpServerTest.class.getClass().getResource("/service-conf.xml").getPath());
		AuthFactory.createAuthList(file);
		ServiceConfigFactory.createServiceMap(fileService);
        System.out.println("服务已经启动...");
        AbstractNettyServer http = (AbstractNettyServer)context.getBean("httpServer");
        AbstractNettyServer server = (AbstractNettyServer)context.getBean("httpsServer");
        AbstractNettyServer serverFile = (AbstractNettyServer)context.getBean("httpFileServer");
        AbstractNettyServer wxServer = (AbstractNettyServer)context.getBean("wxHttpsServer");
        AbstractNettyServer websocketHttpsServer = (AbstractNettyServer)context.getBean("websocketHttpsServer");
        try {
        	http.startServer();
			server.startServer();
			serverFile.startServer();
			wxServer.startServer();
			websocketHttpsServer.startServer();

			String configFilePath = getConfigFilePath();
			System.out.println("QCloud SDK 配置文件路径：" + configFilePath);

			ConfigurationManager.setupFromFile(configFilePath);
			System.out.println("QCloud SDK 已成功配置！");
		} catch (Exception e) {
			e.printStackTrace();
		}
        //System.in.read();
	}


	private static String getConfigFilePath() {
		String osName = System.getProperty("os.name").toLowerCase();
		String defaultConfigFilePath = null;
		boolean isWindows = osName.indexOf("windows") > -1;
		boolean isLinux = osName.indexOf("linux") > -1;

		if (isWindows) {
			defaultConfigFilePath = "C:\\qcloud\\sdk.config";
		}
		else if (isLinux) {
			defaultConfigFilePath = "/etc/qcloud/sdk.config";
		}
		return defaultConfigFilePath;
	}
}
