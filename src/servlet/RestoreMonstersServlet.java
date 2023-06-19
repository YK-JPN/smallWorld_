package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.beans.Monster;
import model.logic.CSVReadLogic;
import util.Jp;

// 参考リンク:　https://code-kitchen.dev/html/input-file/
//				https://qiita.com/ohke/items/bec00a69d3f538aab06b
//				https://kanda-it-school-kensyu.com/java-jsp-servlet-contents/jjs_ch07/jjs_0703/
//				http://www.coderesume.com/modules/answer/?quiz=j02502
//				https://www.sejuku.net/blog/20924

@WebServlet("/restore")
@MultipartConfig(location = "/tmp", maxFileSize = 1048576)
public class RestoreMonstersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("mlist");
		session.removeAttribute("InErrMsg");

		Part part = request.getPart("restoredata");
		String name = this.getFileName(part);
		String zettai = getServletContext().getRealPath("/WEB-INF/uploaded") + "/" + name;
		part.write(zettai);

//        注釈:サンプル環境下においてzettaiの中身(物理パス)は
//        C:￥pleiades￥2022-06￥tomcat￥9￥wtpwebapps￥smallWorld￥WEB-INF￥uploaded
//        となった。(バックスラッシュは全角に置換)

		CSVReadLogic csvrl = new CSVReadLogic();
		String InErrMsg = null;

		List<Monster> mlist = csvrl.convert(zettai);
		if (mlist == null) {
			InErrMsg = Jp.CSV_ERR;
		}

		session.setAttribute("mlist", mlist);
		session.setAttribute("InErrMsg", InErrMsg);

		part.delete();

		String url = "/WEB-INF/jsp/index.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	private String getFileName(Part part) {
		String name = null;
		for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
			if (dispotion.trim().startsWith("filename")) {
				name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
				name = name.substring(name.lastIndexOf("\\") + 1);
				break;
			}
		}
		return name;
	}

}
