package domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Bcard {
	
	//卡号
	private String bCardId;
	//密码
	private String bPwd;
	//余额
	private double bBalance;
	
	private static Bcard bcard;
	
	//使用单列模式
	public static Bcard bcard() {
		if(bcard == null){
			bcard = new Bcard();
        }
        return bcard;
	}
	
	private Bcard() {
		
	}
 
	/**
	 * 
	     * @Title: editBacrd
	     * @author Huang_shengjun
	     * @Description: 修改操作update
	     * @param @param cardid
	     * @param @param balance
	     * @param @throws Exception 参数
	     * @return void 返回类型
	     * @date 2020年5月19日 下午8:30:11
	     * @throws
	 */
	public void editBacrd(String cardid,double balance)throws Exception{
		Connection conn=null;
		try{
			bcard.bCardId = cardid;
			bcard.bBalance = balance;
			System.out.println("==editBacrd-->"+bcard.bCardId+";"+bcard.bBalance);
			conn =DbUtil.getConn();
			//开启手动提交事务
			conn.setAutoCommit(false);
			//保存到数据库
			  PreparedStatement pstmt=null;
			  try{
				  StringBuffer sqlBuff=new StringBuffer("update t_card set b_balance=? where bcard_id=?");
				  pstmt=conn.prepareStatement(sqlBuff.toString());
				  pstmt.setDouble(1, bcard.bBalance);
				  pstmt.setString(2, bcard.bCardId); 
				  pstmt.executeUpdate(); 
			  }catch(SQLException e){
				  throw e;
			  }finally{
				  System.out.println("==执行结束，editBacrd数据连接-->close");
				  DbUtil.close(pstmt);
			  } 
			//提交事务
			conn.commit();
		}catch (Exception e) {
			//事务回滚
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}
	 
	/**
	 * 
	     * @Title: login
	     * @author Huang_shengjun
	     * @Description: 登录验证，得到账户密码、余额
	     * @param @param cardid
	     * @param @param pwd 参数
	     * @return void 返回类型
	     * @date 2020年5月19日 下午8:31:35
	     * @throws
	 */
	public String login(String cardid, String pwd) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbUtil.getConn();
			System.out.println("==login-->"+cardid+";"+pwd);
			pstmt = conn
					.prepareStatement("select * from t_card where bcard_id=? and b_pwd=?");
			int index = 1;
			pstmt.setString(index++, cardid);
			pstmt.setString(index, pwd);
			rs = pstmt.executeQuery();
			if (rs != null && rs.next()) {
				bcard.bCardId = rs.getString("bcard_id");
				bcard.bPwd = rs.getString("b_pwd");
				bcard.bBalance = rs.getDouble("b_balance");
			} else {
				System.out.println(" with id :" + cardid
						+ " could not be loaded from the database.");
				return null;
			}
		} catch (SQLException e) {
			System.out.println(" with id :" + cardid
					+ " could not be loaded from the database.");
		} finally {
			System.out.println("==登录之后bBalance-->"+bcard.bCardId +";"+ bcard.bPwd +";"+ bcard.bBalance);
//			setBcard(bCardId,bPwd,bBalance);
			
			System.out.println("==执行结束，BcardDao数据连接-->close");
			DbUtil.close(rs, pstmt, conn);
		}
		return "success";
	}
	 
	/**
	 * 取款
	 * @param amount
	 * @return 0:成功 1:不成功
	 */
	public int withdraw(double amount) {
		System.out.println("==bBalance-->"+bcard.bBalance+"；amount-->"+amount);
		if(this.bBalance >= amount) {
			System.out.println("==withdraw取款前-->"+bcard.bBalance);
			bcard.bBalance = bcard.bBalance - amount;
			try {
				editBacrd(bcard.bCardId,bcard.bBalance);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 1;//失败返回1
			}
			System.out.println("==withdraw取款后-->"+bcard.bBalance);
			return 0; //成功返回0
		}
		return 1;//失败返回1
	}
	
	 
	
	/**
	 * 存款
	 * @param amount
	 */
	public int deposit(double amount) {
		bcard.bBalance += amount;
		try {
			editBacrd(bcard.bCardId,bcard.bBalance);
			System.out.println("deposit存款后-->"+bcard.bBalance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;//失败返回
		} 
		return 0;  //成功返回0
	}	
	
	


	public String getbCardId() {
		return bCardId;
	}


	public void setbCardId(String bCardId) {
		this.bCardId = bCardId;
	}


	public String getbPwd() {
		return bPwd;
	}


	public void setbPwd(String bPwd) {
		this.bPwd = bPwd;
	}


	public double getbBalance() {
		return bBalance;
	}


	/**
	 * 获取账户余额
	 * @return
	 */
	public void setbBalance(double bBalance) {
		this.bBalance = bBalance;
	}
	
}
