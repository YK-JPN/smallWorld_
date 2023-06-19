<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="util.Jp"%>
<%@ page import="util.LimitConst"%>
<%@ page import="util.TemplateConst"%>
<%@ page import="model.beans.Combi"%>
<%@ page import="model.beans.Monster"%>
<%@ page import="java.util.List"%>

<%
List<Monster> mlist = (List<Monster>) session.getAttribute("mlist");
%>
<%
List<Combi> clist = (List<Combi>) session.getAttribute("combilist");
%>
<%
String InErrMsg = (String) session.getAttribute("InErrMsg");
%>
<%
String SWErrMsg = (String) session.getAttribute("SWErrMsg");
%>
<%
Integer combisize = (Integer) session.getAttribute("combisize");
%>
<%
Integer isSearch = (Integer) session.getAttribute("isSearch");
%>
<!DOCTYPE html>
<html>
<head>
<!-- CSVを扱う際は以下のメタタグが必要 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="robots" content="noindex,nofollow">
<meta name="description" content="これは最終課題作品です">
<title>Small World</title>
</head>
<body>
	<h1>スモール・ワールド入力＆検索ツール</h1>
	
<!-- 入力フォーム -->
	<div id="inputarea">
		<form action="/smallWorld/main-page" method="post">
			<input type="text" name="n" required>
			<select name="c" required>
				<option value="" selected disabled class="erabenai">(未選択)</option>
				<%
				for (int i = 0; i <= LimitConst.CLIMIT; i++) {
				%>
				<option value="<%=i%>"><%=Jp.CJP[i]%></option>
				<%
				}
				%>
			</select>
			<select name="t" required>
				<option value="" selected disabled class="erabenai">(未選択)</option>
				<%
				for (int i = 0; i <= LimitConst.TLIMIT; i++) {
				%>
				<option value="<%=i%>"><%=Jp.TJP[i]%></option>
				<%
				}
				%>
			</select>
			<br>
			<select name="l" required>
				<option value="" selected disabled class="erabenai">(未選択)</option>
				<%
				for (int i = 1; i <= LimitConst.LLIMIT; i++) {
				%>
				<option value="<%=i%>"><%=i%></option>
				<%
				}
				%>
			</select>
			<input type="number" name="a" min=0 max=5050 step=50 required>
			<input type="number" name="d" min=0 max=5050 step=50 required>
			<input type="submit" value="入力">
		</form>
	</div>
	

<!-- 入力内容エラーはここに表示 -->
	<p class="errmsg">
		<%
		if (InErrMsg != null) {
		%>
		<%=InErrMsg%>
		<%
		}
		%>
	</p>
	<!-- スコープ内にある入力された内容の表示領域 -->
	<article>
		<%
		if (mlist != null) {
		%>
		<!-- この下はtxtファイルに出力する為
				フォーマットする際も余計な改行が行われない様注意する事 -->
		<p id="SWinput">
		<%
			for (Monster m : mlist) {
		%>
		<%=m.getN()%> --- <%=Jp.CJP[m.getC()]%>:<%=Jp.TJP[m.getT()]%>・・・星<%=m.getL()%>/<%=m.getA()%>/<%=m.getD()%><br>
		<%
		}
		%>
		</p>
		<%
		}
		%>
	</article>
	<!-- テンプレート化されている項目の入力領域 -->
	<div id="area-temp">
	<form action="/smallWorld/template#area-temp" method="post">
		<select name="temp" required>
		<option value="" selected disabled class="erabenai">(未選択)</option>
		<%
		for(int i=0;i<TemplateConst.TEMPLATE.length;i++){
		%>
		<option value="<%=i%>"><%=TemplateConst.TEMPLATE[i].getN()%></option>
		<%
		}
		%>
		</select>
		<input type="submit" value="テンプレートを追加">
	</form>
</div>
<!-- CSVファイルを読み込む領域 -->
<div id="reader">
	<form action="/smallWorld/restore" method="post" enctype="multipart/form-data">
		<input type="file" name="restoredata" accept=".csv" required>
		<input type="submit" value="復元">
	</form>
</div>
<!-- 各種ボタン表示(スコープ削除・ログ出力・検索実行) -->
	<a href="/smallWorld/SWdelete">
		<button>データ消去</button>
	</a>
	
	<input type="button" value="ファイル出力" onclick="SWinput_LogOutput();"/>
	<input type="button" value="ログのCSV出力" onclick="location.href='OutputCSV'"/>

	<a href="/smallWorld/SWexe#result">
		<button>SW検索</button>
	</a>
<!-- ここからスモールワールド検索結果表示 -->
	<article id="result">
		<p class="errmsg">
			<%
			if (isSearch==1) {
				if(SWErrMsg!=null){
			%>
			<%=SWErrMsg%>
			<%
			} else {
				if(combisize==null){
					combisize=0;
				}
			%>
			検索適合結果: <%= combisize %>件
			<%	if(combisize!=0){ %>
			<input type="button" value="検索結果txt保存" onclick="SWresults_LogOutput();"/>
			<%
			}
			}
			}
			%>
		</p>
<!--  この下のSWresultsはJavaScriptを使って内容をそのまま保存する。
		なのでtxtファイルに不要な改行が発生しない様フォーマットの際は気を付ける必要がある。  -->
	
		<div id="SWresults">
		<%
		if (clist != null) {
			for (Combi com : clist) {
		%>
		<%=com.getX().getN()%> --- <%=com.getY().getN()%> --- <%=com.getZ().getN()%><br>
		<%
		}
		%>
		</div>
		<%
		}
		%>
	</article>

<script src="<%=request.getContextPath()%>/js/script.js"></script>
</body>
</html>