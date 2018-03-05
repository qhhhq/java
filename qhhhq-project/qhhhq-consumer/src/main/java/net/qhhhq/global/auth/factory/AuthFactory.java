package net.qhhhq.global.auth.factory;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import net.qhhhq.global.auth.config.AuthList;
import net.qhhhq.utils.XmlUtils;

public class AuthFactory {

	private static AuthList list;

	public static AuthList getAuthList() {
		return list;
	}

	public static void createAuthList(File file) {
		try {
			Object object = XmlUtils.xmlToBean(file,AuthList.class);
			list = (AuthList)object;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
