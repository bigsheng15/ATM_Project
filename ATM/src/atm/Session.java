//******************************************************************************
//
// ATM系统 -  Session.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

import domain.Bcard;

/**
* 一个Session对象代表了现实世界里的一个人与ATM机器的对话过程.
* @author  何希
* @version 10/06/2018
*/
public class Session {

	public static final int NOTREADY = 1; // 未开始
	public static final int AUTHENTICATION = 2; // 待验证 
	public static final int CHOOSING = 3; // 交易选择
	public static final int INTRANSACTION = 4; // 交易中
	public static final int QUIT = 5; // 退出
	public static final int QUIT_WITH_CARD = 6; // 拔卡退出
	public static final int QUIT_WITHOUT_CARD = 7; // 留卡退出
	// 账号
	private String cardNo = null;
	// 密码
	private String pwd = null;
	// 会话的状态
	private int state = NOTREADY;
	// 此会话对应的账户
//	private Account acct = null;
	private Bcard bcard = null;
	
	Bcard cardDao = Bcard.bcard();
	// 当前的交易
	private Transaction trans = null;
	
	
	public void setState(int state) {
		this.state = state;
	}
	
	
	public Transaction getTransaction() {
		return trans;
	}
	
	public Bcard getBcard() {
		return bcard;
	}

	public void setTransaction(Transaction trans) {
		this.trans = trans;
	}
	
	/**
	 * 退卡，重新开始一个新会话
	 */
	public void startOver() {
		state = NOTREADY;
		cardNo = null;
		pwd = null;
		bcard = null;
	}
	
	/**
	 * 确定银行卡是否有效。需要和后台数据库进行比较.
	 * 现在的实现假设银行卡是有效的。
	 * @param cardNo
	 * @return 银行卡是否正确。
	 */
	public boolean verify(String cardNo) {
		this.cardNo = cardNo;
		this.state = AUTHENTICATION;
		System.out.println("确定银行卡是否有效-会话的状态state-->"+state);
		return true;
	}
	
	
	/**
	 * 验证银行卡密码
	 */
	public void auth(String pwd) {
		
		System.out.println("验证银行卡密码-会话的状态state-->"+state);
		if(state ==  AUTHENTICATION) {
			this.pwd = pwd;
			String loginstate =  cardDao.login( this.cardNo,  this.pwd);
			// 账户密码正确
			if(loginstate != null) {
				state = CHOOSING;
				ATM instance = ATM.getInstance();
				instance.getDisplay().setText("请选择业务 1:取款 2:存款 3:查询余额 0:退出 ");
				instance.getDigitButton().stateChange(0, 1, "TransactionServlet");
			}
			// 账户密码不正确
			else {
				
			}
		}
	}
	
	/**
	 * 选择交易
	 */
	public void selectTransaction(int options) {
		if(state == CHOOSING) {
			this.trans = Transaction.makeTransaction( this, this.bcard, options);
			state = INTRANSACTION;
		}
	}
	
	/**
	 * 获取Session的状态字符串
	 */
	public String toString() {
		String output = "{";
		output += "\"state\":" + this.state;
		output += "}";
		return output;
	}
}
