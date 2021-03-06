package net.qhhhq.core.boot;

import java.io.File;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.global.auth.factory.AuthFactory;
import net.qhhhq.global.service.config.factory.ServiceConfigFactory;
import net.qhhhq.server.netty.AbstractNettyServer;

public class PlatformBootstart {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println(context.getDisplayName() + ": here");
		context.start();
		File file = new File(PlatformBootstart.class.getClass().getResource("/auth.xml").getPath());
		File fileService = new File(PlatformBootstart.class.getClass().getResource("/service-conf.xml").getPath());
		AuthFactory.createAuthList(file);
		ServiceConfigFactory.createServiceMap(fileService);
        System.out.println("服务已经启动...");
        AbstractNettyServer server = (AbstractNettyServer)context.getBean("httpServer");
        AbstractNettyServer serverFile = (AbstractNettyServer)context.getBean("httpFileServer");
        try {
			server.startServer();
			serverFile.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
