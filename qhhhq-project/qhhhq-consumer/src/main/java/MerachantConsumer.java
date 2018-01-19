import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.base.QueryResult;
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
        m.setContactName("dubbo");
        m.setContactPhone("1111111111");
        m.setId(2);
        m.setType(1);
        m.setName("123");
        m.setProvince("666");
        m.setStatus("S");
        //service.save(m);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", 0);
        map.put("pagesize", 1);
        map.put("status", "S");
        LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
        orderBy.put("contact_name", "asc");
        QueryResult<Merachant> result = service.getScrollData(0,10, orderBy);
        List<Merachant> list = result.getResultList();
        for(Merachant mc : list) {
        	System.out.println(mc.getContactName());
        }
	}

}
