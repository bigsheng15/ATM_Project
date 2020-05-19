//******************************************************************************
//
// ATM系统 -  Account.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package banking;

/**
* 一个Account对象代表了现实世界里的一个银行账户.
* @author  何希
* @version 10/06/2018
*/

/**
 * 
     * @ClassName: Account
     * @Description: 此类废弃
     * @author Huang_shengjun
     * @date 2020年5月19日 下午9:12:57
     *
 */
public class Account {
	// 账号
	String card_no = "";
	// 密码
	String pwd = "";
	// 余额
	double balance = 1000.00;
	
	public Account(String card_no, String pwd) {
		this.card_no = card_no;
		this.pwd = pwd;
	}
	/**
	 * 通过用户名，密码获取一个银行账户对象
	 * 实际中应该查询数据库。在当前的实现中，我们只有一个账户。
	 * @param card_no
	 * @param pwd
	 * @return
	 */
	public static Account getAccount(String card_no, String pwd) {
		if(card_no.equals("12345") && pwd.equals("123") ) {
			Account act= new Account(card_no,pwd); 
			return act;
		}
		return null;
	}
	/**
	 * 取款
	 * @param amount
	 * @return 0:成功 1:不成功
	 */
	public int withdraw(double amount) {
		if(this.balance >= amount) {
			this.balance = this.balance - amount;
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取账户余额
	 * @return
	 */
	public double getBalance() {
		return this.balance;
	}
	
	/**
	 * 存款
	 * @param amount
	 */
	public void deposit(double amount) {
		this.balance += amount;
	}
}
