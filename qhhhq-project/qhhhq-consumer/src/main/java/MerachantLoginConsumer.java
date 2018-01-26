import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.base.QueryResult;
import net.qhhhq.merachant.api.MerachantLoginService;
import net.qhhhq.model.merachant.MerachantLogin;
import net.qhhhq.model.merachant.MerachantType;

public class MerachantLoginConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();
        System.out.println("consumer start");
        MerachantLoginService service = context.getBean(MerachantLoginService.class);
        System.out.println("consumer");
        MerachantLogin login = new MerachantLogin();
        login.setMerchantId(405060683731046400l);
        login.setPassword("123");
        login.setPhone("6666666");
        login.setUserName("u1");
        login.setEmail("666@qq.com");
        //service.save(login);
        Long[] ids = new Long[] {405054072354508800l, 405054076045496320l};
        //service.delete(ids);
        QueryResult<MerachantLogin> result = service.getScrollData();
        List<MerachantLogin> list = result.getResultList();
        for(MerachantLogin mt : list) {
        	System.out.println(mt.getId()+"==="+mt.getEmail());
        }
	}

}
