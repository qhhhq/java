import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.merachant.api.MerachantService;
import net.qhhhq.model.merachant.Merachant;

public class MerachantConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();
        System.out.println("consumer start");
        MerachantService service = context.getBean(MerachantService.class);
        System.out.println("consumer");
        Merachant m = new Merachant();
        m.setCity("111");
        m.setContactName("ldr");
        m.setContactPhone("1111111111");
        m.setId(1);
        m.setType(1);
        m.setName("123");
        m.setProvince("666");
        m.setStatus("S");
        //service.save(m);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", 0);
        map.put("pagesize", 5);
        map.put("status", "S");
        List<Merachant> list = service.listMerachant(map);
        for(Merachant mc : list) {
        	System.out.println(mc.getContactName());
        }
	}

}
