package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Monster;
import model.logic.AddMonsterLogic;
import util.TemplateConst;

@WebServlet("/template")
public class SWtemplateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int tempvalue = Integer.parseInt(request.getParameter("temp"));
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Monster> mlist = (List<Monster>) session.getAttribute("mlist");
		if(mlist==null) {
			mlist=new ArrayList<>();
		}
		session.removeAttribute("mlist");
		session.removeAttribute("InErrMsg");
		
		AddMonsterLogic aml = new AddMonsterLogic();
		aml.execute(TemplateConst.TEMPLATE[tempvalue], mlist);
		
		session.setAttribute("mlist", mlist);
		
		String url = "/WEB-INF/jsp/index.jsp#area-temp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
