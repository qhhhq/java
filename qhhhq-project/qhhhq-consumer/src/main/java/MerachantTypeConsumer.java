import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.qhhhq.base.QueryResult;
import net.qhhhq.merachant.api.MerachantTypeService;
import net.qhhhq.model.merachant.MerachantType;

public class MerachantTypeConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();
        System.out.println("consumer start");
        MerachantTypeService service = context.getBean(MerachantTypeService.class);
        System.out.println("consumer");
        MerachantType type = new MerachantType();
        type.setId(11);
        type.setName("sub type");
        type.setParent(1);
        service.save(type);
        Integer[] ids = new Integer[] {1};
        //service.delete(ids);
        QueryResult<MerachantType> result = service.getScrollData();
        List<MerachantType> childType = service.getMerachantTypeByParent(1);
        List<MerachantType> list = result.getResultList();
        for(MerachantType mt : list) {
        	System.out.println(mt.getId()+"==="+mt.getName());
        }
        for(MerachantType mt : childType) {
        	System.out.println(mt.getId()+"==="+mt.getName());
        }
	}

}