import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.api.common.AreaService;
import net.qhhhq.model.common.Area;

public class AreaConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();
        System.out.println("consumer start");
        AreaService areaService = context.getBean(AreaService.class);
        System.out.println("consumer");
        Area area = new Area();
        area.setCode("630000");
        area.setDistrict("");
        area.setId(3304);
        area.setParent(null);
        area.setProvince("青海省");
        areaService.save(area);
        //areaService.delete(3304);

	}

}
