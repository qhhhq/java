package net.qhhhq.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.qhhhq.global.auth.config.AuthConfig;
import net.qhhhq.global.auth.config.AuthList;
import net.qhhhq.global.auth.config.AuthServiceConfig;

public class XmlUtils {

	 /**
     * xml文件配置转换为对象
     * @param xmlPath  xml文件路径
     * @param load    java对象.Class
     * @return    java对象
     * @throws JAXBException
     * @throws IOException
     */
    public static Object xmlToBean(File xmlFile,Class<?> load) throws JAXBException, IOException{
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object object = unmarshaller.unmarshal(xmlFile);
        return object;
    }

    public static void main(String[] args) throws JAXBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    	String xmlPath =  "E:\\git\\resources\\qhhhq-project\\qhhhq-consumer\\resources\\auth.xml";
    	Object object = XmlUtils.xmlToBean(new File(xmlPath),AuthList.class);
    	AuthList ac = (AuthList)object;
    	System.out.println(ac.getTrans().size());
    	for(AuthConfig conf: ac.getTrans()) {
    		System.out.println(conf.getServiceCode()+"=="+conf.getMessageType()+"==="+conf.getMessageCode()+"==="+conf.getService().size());
    		for(AuthServiceConfig osc : conf.getService()) {
    			System.out.println(osc.getClassPath()+"---"+osc.getAuthService() + "===" +osc.getRole());
    		}

    	}
	}
}
