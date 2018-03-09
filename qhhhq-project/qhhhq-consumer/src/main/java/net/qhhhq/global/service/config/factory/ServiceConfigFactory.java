package net.qhhhq.global.service.config.factory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.qhhhq.global.service.config.ServiceConfig;
import net.qhhhq.global.service.config.ServiceItemConfig;
import net.qhhhq.utils.XmlUtils;

public class ServiceConfigFactory {

	private static Map<String, ServiceItemConfig> map;

	public static Map<String, ServiceItemConfig> getServiceMap() {
		return map;
	}

	public static void createServiceMap(File file) {
		try {
			map = new HashMap<String, ServiceItemConfig>();
			Object object = XmlUtils.xmlToBean(file,ServiceConfig.class);
			ServiceConfig config = (ServiceConfig) object;
			for(ServiceItemConfig item : config.getService()) {
				map.put(item.getServiceCode()+item.getMessageType(), item);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
