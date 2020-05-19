package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atm.ATM;


/**
* 插入银行卡
* @author  何希
* @version 10/06/2018
*/
public class CardInsertedServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cardNo = req.getParameter("cardNo");
		System.out.println("==插入的卡号--CardInsertedServlet--cardNo-->" + cardNo);
		System.out.println("==读取卡号后开始cardInserted(cardNo)" );
		ATM.getInstance().cardInserted(cardNo);
		
		
		
		String json = ATM.getInstance().getResponse();
		resp.setContentType("text/json");  
		resp.setCharacterEncoding("UTF-8"); 
		resp.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
