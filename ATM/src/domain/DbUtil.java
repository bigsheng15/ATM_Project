package domain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * 数据库访问操作管理类
 * @author 演示
 *
 */
public class DbUtil {
	private static final String driverClass="com.mysql.jdbc.Driver";
	private static final String url="jdbc:mysql://127.0.0.1:3306/atmDB";
	private static final String user="root";
	private static final String password="123456";
	 /**
	  * 获取数据库连接
	  * @return
	  */
	  public static Connection getConn(){
		  Connection conn =null;
		  try{
			  Class.forName(driverClass);
			  conn =DriverManager.getConnection(url, user, password);
			  
		  }catch (Exception e) {
			 e.printStackTrace();
		}
		  
		  return conn;
	  }
	  /**
	   * 
	   * @param sql
	   * @throws SQLException
	   */
	  public void executeUpdate(String sql)throws SQLException{
		  Connection conn=null;
		  Statement stmt=null;
		  try{
			  conn=getConn();
			  stmt=conn.createStatement();
			  int c=stmt.executeUpdate(sql);
		  }catch (SQLException e) {
			throw new SQLException("执行SQL更新失败:"+e.getMessage(),e);
		}finally{
			
			close(stmt, conn);
			
		}
	  }
	  /**
	   * 
	   * @param sql
	   * @throws SQLException
	   */
	  public ResultSet executeQuery(Statement stmt,String sql)throws SQLException{
	
		  ResultSet rs=null;
		  try{
			  rs=stmt.executeQuery(sql);
			  return rs;
		  }catch (SQLException e) {
			throw new SQLException("执行SQL查询失败"+e.getMessage(),e);
		}
	  }
	  
	  public static void close(ResultSet rs,Statement stmt,Connection conn){
		  try{
			  if(rs!=null){
				  rs.close();
				}
			   if(stmt!=null){
					stmt.close();
				}
				if(conn!=null){
					conn.close();
				}
		  }catch (SQLException e) {
			  System.out.printf(e.getMessage(),e);
//			  LOG.error(e.getMessage(),e);
		  }
		  
	  }
	  public static void close(Statement stmt,Connection conn){
		  close(null, stmt, conn);
	  }
	  /**
	   * 关闭连接
	   * @param conn
	   * @throws SQLException
	   */
	  public static void close(Connection conn){
		  close(null, null, conn);
	  }
	  
	  /**
	   * 关闭Statement
	   * @param conn
	   * @throws SQLException
	   */
	  public static void close(Statement stmt){
		  close(null, stmt, null);
	  }
	 

}
