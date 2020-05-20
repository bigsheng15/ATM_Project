//******************************************************************************
//
// ATM系统 -  Transaction.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

import banking.Account;
import domain.Bcard;

/**
* 这个Transaction类是所有交易类的父类。当用户选择一个交易（取款，存款，查询）时，
* 会实例化相应的交易类。 
* @author  何希
* @version 10/06/2018
*/
public class Transaction {
	
	public static final int TRANS_UNSTART = 1; // 未开始

	
	public static final int TYPE_EXIT = 0; // 退出交易
	public static final int TYPE_WITHDRAW = 1; // 取款交易
	public static final int TYPE_DEPOSIT = 2; // 存款交易
	
	// 会话的状态
	private int state = TRANS_UNSTART;
	//相关的账户
//	private Account acct = null;
	private Bcard bcard = null;
	// 对应的对话
	private Session session;
	// 金额的大小
	private double amount = 0.0;
	
	/**
	 * 获取金额
	 * @return
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Session getSession() {
		return this.session;
	}
	
//	public Account getAccount() {
//		return this.acct;
//	}
	
	
	public int getState() {
		return this.state;
	}
	
	public Bcard getBcard() {
		return bcard;
	}

	public void setBcard(Bcard bcard) {
		this.bcard = bcard;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public Transaction(Session session, Bcard bcard) {
		this.session = session;
		this.bcard = bcard;
	}
	/**
	 * 根据用户的选择，生成一个交易子类
	 * @param session
	 * @param acct
	 * @param options
	 * @return
	 */
	public static Transaction makeTransaction(Session session, Bcard bcard, int options) {
		Transaction tmp = null;
		switch(options) {
			case TYPE_WITHDRAW: //取钱
				tmp = new Withdraw(session,bcard);
				break;
			case TYPE_DEPOSIT:  //存取
				tmp = new Deposit(session,bcard);
				break;
			case TYPE_EXIT: //关机
				// 插卡孔状态要变 开关按钮状态要变 显示屏状态要变 数字键盘状态要变
				ATM machine = ATM.getInstance();
				machine.setState(ATM.IDLE);
				machine.getCardSlot().eject();
				machine.getSwitchButton().stateChange(ATM.IDLE);
				machine.getDisplay().setText("请插入你的银行卡");
				machine.getDigitButton().stateChange(2, 0, "");
				break;
		}
		return tmp;
	}
	
	/**
	 * 把执行用户的请求,在Withdraw中已重写方法
	 */
	public void execute() {
		
	}
	
	/**
	 * 把执行用户的请求,在Withdraw中已重写方法
	 */
	public void execute1() {
		
	}
	
	
	/**
	 * 处理打印
	 * @param flag 0:打印 1:不打印 在Withdraw中已重写方法
	 */
	public void print(int flag) {
		
	}
	
	/**
	 * 处理打印
	 * @param flag 0:打印 1:不打印 在Withdraw中已重写方法
	 */
	public void print1(int flag) {
		
	}
	
}
