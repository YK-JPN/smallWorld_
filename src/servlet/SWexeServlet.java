package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Combi;
import model.beans.Monster;
import model.logic.SWTriSearchLogic;
import util.Jp;

@WebServlet("/SWexe")
public class SWexeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Monster> mlist = (List<Monster>) session.getAttribute("mlist");
//		今回はこの処理以降もまだまだ使う予定があるのでremoveAttribute()は行わない

		session.removeAttribute("combilist");
		session.removeAttribute("combisize");
		session.removeAttribute("SWErrMsg");
		session.removeAttribute("isSearch");

		session.setAttribute("isSearch", 1);

		int num = 0;
		if (mlist != null) {
			num = mlist.size();
		}

		int combisize = 0;
		String SWErrMsg = null;

		if (num == 0) {
			SWErrMsg = Jp.NO_MONSTER;
		} else if (num < 3) {
			SWErrMsg = Jp.TOO_SMALL_SIZE;
		} else {
			SWTriSearchLogic search = new SWTriSearchLogic();
			List<Combi> combilist = search.execute(mlist, num);
			combisize = combilist.size();
			session.setAttribute("combisize", combisize);
			session.setAttribute("combilist", combilist);
		}
		session.setAttribute("SWErrMsg", SWErrMsg);

		String url = "/WEB-INF/jsp/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
