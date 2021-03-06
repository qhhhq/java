import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.api.common.AreaService;
import net.qhhhq.base.QueryResult;
import net.qhhhq.model.common.Area;

public class AreaConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();
        System.out.println("consumer start");
        AreaService areaService = context.getBean(AreaService.class);
        System.out.println("consumer");
       Area area = new Area();
        area.setCode("630123");
        area.setId(3313);
        area.setParent(3305);
        area.setDistrict("湟源县");
        areaService.save(area);
        //areaService.delete(3304);
        QueryResult<Area> result = areaService.getScrollData();
        List<Area> areas = result.getResultList();
        for(Area a : areas) {
        	System.out.println(a.getProvince() +"----"+a.getCity());
        }
	}

}
