

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"conf/spring.xml","conf/spring-mybatis.xml"});
		System.out.println(context.getDisplayName() + ": here");
		context.start();
        System.out.println("服务已经启动...");
        System.in.read();
		//com.alibaba.dubbo.container.Main.main(args);
	}

}
