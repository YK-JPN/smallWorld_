package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Monster;

@WebServlet("/OutputCSV")
public class OutputCSVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Monster> mlist = (List<Monster>) session.getAttribute("mlist");

		if (mlist == null) {
			String url = "/WEB-INF/jsp/index.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
//		日付の取得
			Date now = new Date();
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			String nowString = f.format(now);

//		セッションスコープの取得

			String filename = "InputLogSW_" + nowString + ".csv";
			response.setHeader("Content-Type", "text/csv; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

			PrintWriter out = response.getWriter();
			for (Monster m : mlist) {
				out.append(m.getN() + "," + m.getC() + "," + m.getT() + "," + m.getL() + "," + m.getA() + "," + m.getD() + "\n");
			}
		}
	}

}
