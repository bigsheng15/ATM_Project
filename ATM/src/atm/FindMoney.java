package atm;


import domain.Bcard;

public class FindMoney extends Transaction{


	public static final int TRANS_UNSTART = 1; // 未开始
	public static final int TRANS_GETDATA = 2; // 获取交易请求
	public static final int TRANS_SUCCESS = 3; // 交易成功
	public static final int TRANS_FAILURE = 4; // 交易失败
	public static final int TRANS_EXIT = 4; // 退出
	Bcard cardDao = Bcard.bcard();
	
	public FindMoney(Session session, Bcard bcard) {
		super(session,bcard);
		// 当选择取款交易时,需要改变显示屏的显示,需要改变数字键盘的状态
		ATM machine = ATM.getInstance();
		this.setState(TRANS_SUCCESS);
		machine.getDisplay().setText("该账户余额为："+cardDao.getbBalance()+"<br>"+" 返回:1");
		machine.getDigitButton().stateChange(0, 0, "FindMoneyServlet");
	}
	
	/**
	 * 从用户账户查询金额
	 */
	public void findmoney(int flag) {
		// 显示屏更新 数字键盘状态更新 
		this.setState(TRANS_EXIT);
		this.getSession().setState(Session.CHOOSING);
		ATM machine = ATM.getInstance();
		machine.getDisplay().setText("请选择业务 1:取款 2:存款 3:查询余额 0:退出 ");
		machine.getDigitButton().stateChange(0, 0, "TransactionServlet");
	}
}
