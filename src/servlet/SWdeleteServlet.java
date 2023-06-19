package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/SWdelete")
public class SWdeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("mlist");
		session.removeAttribute("combilist");
		session.removeAttribute("InErrMsg");
		session.removeAttribute("SWErrMsg");
		session.removeAttribute("combisize");
		session.removeAttribute("isSearch");
		
		session.setAttribute("isSearch",0);
		
		String url = "/WEB-INF/jsp/index.jsp";
		RequestDispatcher dispatcher=request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	
}
