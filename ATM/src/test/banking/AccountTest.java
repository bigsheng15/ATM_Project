package test.banking;


import org.junit.jupiter.api.Test;

import atm.ATM;
import domain.Bcard;

/**
     * @ClassName: AccountTest
     * @Description: 测试类
     * @author Huang_shengjun
     * @date 2020年5月19日 下午9:47:08
     *
 */
class AccountTest {

	Bcard dao =  Bcard.bcard();

	@Test
	void testGetAccount() {
		ATM.getInstance().turnon();
		System.out.println(ATM.getInstance().getResponse());
		//fail("Not yet implemented");
	}

	@Test
	void testeditBacrd() {
		try {
			dao.editBacrd("201848448", 100110);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("testeditBacrd-->执行成功");
	}
	
	@Test
	void testlogin() {
		dao.login("201848448", "1");
		System.out.println("testlogin执行成功-->卡号="+dao.getbCardId()+";密码="+dao.getbPwd()+";余额="+dao.getbBalance());
	}
	
}
