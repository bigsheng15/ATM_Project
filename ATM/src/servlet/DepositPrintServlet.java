package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import atm.ATM;

/**
     * @ClassName: DepositPrintServlet
     * @Description: TODO描述 存款打印
     * @author Huang_shengjun
     * @date 2020年5月20日 下午2:01:04
     *
 */
public class DepositPrintServlet extends HttpServlet {
 
		private static final long serialVersionUID = 1L;
		
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			int num = Integer.parseInt(req.getParameter("num"));
			ATM.getInstance().getSession().getTransaction().print1(num);
			System.out.println("==DepositPrintServlet执行存款处理打印0:打印 1:不打印--print(num)-->"+num);
			
			String json = ATM.getInstance().getResponse();
			resp.setContentType("text/json");  
			resp.setCharacterEncoding("UTF-8"); 
			resp.getWriter().write(json);
		}

		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req,resp);
		}

}
