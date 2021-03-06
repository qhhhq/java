import org.json.JSONObject;

import net.qhhhq.weixin.service.accesstoken.AccessTokenConfig;
import net.qhhhq.weixin.service.accesstoken.AccessTokenServiceBean;
import net.qhhhq.weixin.service.templatemessage.TemplatemessageServiceBean;

public class AccessTokenTest {

	public static void main(String[] args) {
		AccessTokenConfig ac = new AccessTokenConfig();
		ac.setAppid("wx275a30f644ebc4c9");
		ac.setSecret("ae0c581b7a6bf0792c87d00a1a8a1d9b");
		AccessTokenServiceBean atb = new AccessTokenServiceBean(ac);
		System.out.println(atb.getToken());
		TemplatemessageServiceBean tsb = new TemplatemessageServiceBean(atb);
		JSONObject message = new JSONObject();
		message.put("touser", "ocwv50CeBU1vcGThF7-D0plZOxwA");
		message.put("template_id", "V85rKJivbaoPgHaSUpqApA3aWvRNY7EKZtGXhSXoA00");
		message.put("form_id", "1522204322694");
		message.put("data", "");
		System.out.println(tsb.send(message));
	}

}
