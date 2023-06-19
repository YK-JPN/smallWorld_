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
import model.check.Dupcheck;
import model.check.Errcheck;
import model.logic.AddMonsterLogic;
import util.Jp;

@WebServlet("/main-page")
public class SWMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer isSearch=0;
		
		HttpSession session = request.getSession();
		session.removeAttribute("combilist");
		session.removeAttribute("InErrMsg");
		session.removeAttribute("SWErrMsg");
		session.removeAttribute("combisize");
		session.setAttribute("isSearch",isSearch);
		@SuppressWarnings("unchecked")
		List<Monster> mlist = (List<Monster>) session.getAttribute("mlist");
		if (mlist == null) {
			mlist = new ArrayList<>();
		}
		String url = "/WEB-INF/jsp/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String n = request.getParameter("n");
		int c = Integer.parseInt(request.getParameter("c"));
		int t = Integer.parseInt(request.getParameter("t"));
		int l = Integer.parseInt(request.getParameter("l"));
		int a = Integer.parseInt(request.getParameter("a"));
		int d = Integer.parseInt(request.getParameter("d"));
		String InErrMsg = null;
		
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		List<Monster> mlist = (List<Monster>) session.getAttribute("mlist");
		if(mlist==null) {
			mlist=new ArrayList<>();
		}
		session.removeAttribute("mlist");
		session.removeAttribute("InErrMsg");

		boolean isInputErr = Errcheck.execute(c, t, l, a, d);// trueならばエラーなし

		if (isInputErr) {
			if (Dupcheck.execute(n, mlist)) {

				Monster m = new Monster(n, c, t, l, a, d);
				AddMonsterLogic aml = new AddMonsterLogic();
				aml.execute(m, mlist);
			} else {
				InErrMsg = Jp.NAME_DUPULICATION;
			}
		} else {
			InErrMsg = Jp.INPUT_IS_ERR;
		}
		
		session.setAttribute("mlist", mlist);
		session.setAttribute("InErrMsg", InErrMsg);

		String url = "/WEB-INF/jsp/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
