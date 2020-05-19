//******************************************************************************
//
// ATM系统 -  Withdraw.java 
// 参考了 http://www.cs.gordon.edu/courses/cs211/ATMExample/index.html
// 
//******************************************************************************

package atm;

import banking.Account;
import domain.Bcard;

public class Withdraw extends Transaction {
	
	public static final int TRANS_UNSTART = 1; // 未开始
	public static final int TRANS_GETDATA = 2; // 获取交易请求
	public static final int TRANS_SUCCESS = 3; // 交易成功
	public static final int TRANS_FAILURE = 4; // 交易失败
	public static final int TRANS_EXIT = 4; // 退出
	Bcard cardDao = Bcard.bcard();
	
	public Withdraw(Session session, Bcard bcard) {
		super(session,bcard);
		// 当选择取款交易时,需要改变显示屏的显示,需要改变数字键盘的状态
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText("请输入取款金额");
		machine.getDigitButton().stateChange(1, 0, "WithdrawInfoServlet");
	}
	
	/**
	 * 从用户账户扣取金额
	 */
	public void execute() {
//		int ret = this.getAccount().withdraw(this.getAmount());
		int ret = cardDao.withdraw(this.getAmount());
		// 扣取成功
		if(ret == 0) {
			// 显示屏更新 数字键盘状态更新 
			this.setState(TRANS_SUCCESS);
			ATM machine = ATM.getInstance();
			machine.getDisplay().setText("取款成功。你的余额是"+cardDao.getbBalance()+"<br>"+"打印:0 不打印:1");
			machine.getDigitButton().stateChange(0, 0, "WithdrawPrintServlet");
		}
		// 扣取不成功
		else {
			
		}
	}
	
	/**
	 * 处理打印
	 * @param flag 0:打印 1:不打印
	 */
	public void print(int flag) {
		// 显示屏更新 数字键盘状态更新 
		this.setState(TRANS_EXIT);
		this.getSession().setState(Session.CHOOSING);
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText("请选择业务 1:取款 2:存款 0:退出 ");
		machine.getDigitButton().stateChange(0, 0, "TransactionServlet");
	}
	
}
