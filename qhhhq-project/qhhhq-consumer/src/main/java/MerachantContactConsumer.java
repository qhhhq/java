import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.base.QueryResult;
import net.qhhhq.merachant.api.MerachantContactService;
import net.qhhhq.model.merachant.MerachantContact;
import net.qhhhq.model.merachant.MerachantLogin;

public class MerachantContactConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();
        System.out.println("consumer start");
        MerachantContactService service = context.getBean(MerachantContactService.class);
        System.out.println("consumer");
        MerachantContact contact = new MerachantContact();
        contact.setAddress("adf d");
        contact.setCity("111");
        contact.setContactId("666");
        contact.setFirst(true);
        contact.setMerchantId(405060683731046400l);
        contact.setMobilePhone("131111");
        contact.setContactName("jack");
        contact.setProvince("qh");
        //service.save(contact);
        Long[] ids = new Long[] {405054072354508800l, 405054076045496320l};
        //service.delete(ids);
        QueryResult<MerachantContact> result = service.getScrollData();
        List<MerachantContact> list = result.getResultList();
        for(MerachantContact mt : list) {
        	System.out.println(mt.getId()+"==="+mt.getAddress()+"===="+mt.getContactName());
        }
	}

}
