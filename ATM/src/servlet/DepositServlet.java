package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atm.ATM;

/**
     * @ClassName: DepositServlet
     * @Description: TODO描述  存款
     * @author Huang_shengjun
     * @date 2020年5月20日 下午1:54:37
     *
 */
public class DepositServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int num = Integer.parseInt(req.getParameter("num"));
		System.out.println("==WithdrawInfoServlet获取取款金额,执行存款--->"+num);
		ATM.getInstance().getSession().getTransaction().setAmount(num);
		ATM.getInstance().getSession().getTransaction().execute1();
		
		
		
		String json = ATM.getInstance().getResponse();
		resp.setContentType("text/json");  
		resp.setCharacterEncoding("UTF-8"); 
		resp.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
}
