import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.model.shop.ShopType;
import net.qhhhq.service.shop.ShopTypeService;

public class ShopTypeConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();
        System.out.println("consumer start");
        ShopTypeService service = context.getBean(ShopTypeService.class);
        ShopType type =new ShopType();
        type.setName("美食");
        type.setParent(null);
        service.save(type);
        System.out.println(type.getId());
	}

}
