# SmallWorld
職業訓練最終課題その1、Java中心で開発


===============================================
Read me about "Small World Tool by JSP"
===============================================

Author:	Y.Ksihida
2022.07

0.目次
	1.はじめに
	2.基本的動作
		(2-appendix 仕様上の動作について)
	3.パッケージ
	4.各種ファイル

===========1.はじめに===========

このツールを使うにあたって、前提知識が必要なので先ずは下記リンクをご覧いただきたい。

(リンク先:《スモール・ワールド》 -遊戯王カードWiki)
https://yugioh-wiki.net/index.php?%A1%D4%A5%B9%A5%E2%A1%BC%A5%EB%A1%A6%A5%EF%A1%BC%A5%EB%A5%C9%A1%D5

(※当該ページでは《》内はカード名、【】内はデッキ名を表している)

正直初見では理解に苦しむ様な事ばかりが書かれているが、簡潔に言ってしまえば

「手札のモンスターを始点にして、デッキ内の特定のカードを中継して条件を満たす別のカード(欲しい奴)に変える」(メリット)
「その際手札の始点とデッキの中継点の2枚は裏側で除外(＝再利用がとても難しい状態に)される」(デメリット)

といった物である。この時始点と中継点、及び中継点と終点は

「5つのステータスの内どれか一つだけが一致する(2つ以上の一致、及び完全不一致は許されない)」

という条件を満たしている必要がある。
・
・
・

日本語的にこれらの関係を理解する事は出来るかもしれない(僕の説明が悪い為に分からなかった方も居るかもしれない)が
具体的にこのカードを採用したデッキを組んだとして、実際にその中のどのカード同士がこれらの関係下にあるか、
直感的に把握する事は非常に難しい。
その為に今回は、

『デッキ内のモンスターを打ち込んだ後に、この《スモール・ワールド》関係下にある組合せを全て列挙する』
	(ついでにそれらの結果をtxtファイルに出力してローカルに保存できる)

という検索ツールをJavaで開発した。

Web上には
「始点と終点を入力すれば、それに対応する中継点になれる物を全カードの中から探し出す」
(参考リンク:https://yugilabo.com/smallworld/)
(※2022/07/19時点で何故か閉鎖中だが類似サイトは存在する)

といった物は存在しているが、既に作ってあるデッキに対して考える物はなかったので
今回制作にあたった。

(※追記:一通りファイルを作り終えてこのRead Meを書いているのが三連休明けの07/19であるのだが、
				2022/07/17の夜付けで【遊戯王】「スモール・ワールド」検索＆可視化ツール
					noteリンク:https://note.com/zk_phi/n/nec09e0cbdd72　
					ツールリンク:https://zk-phi.github.io/smallworld-visualizer/
				が公開された様である)


===========2.基本的動作===========


始点となるのはSWMainServletのdoGetメソッドである。
(試験環境でのURL:http://localhost:8080/smallWorld/main-page)
基本的にこのアプリケーションではセッションスコープを軸にして動いており、
txtファイル出力はJavaScript、CSV入出力はサーブレットを用いている。


JavaBeans:MonsterとCombiの2種類を用意した。
	Monster:	各モンスターの名前及び先述した5つのステータスの計6つをフィールドに持つ。
						この5つのステータスは全て数値(int型)で管理している。
						ユーザーの入力内容一つ一つがこのクラスへと変換され、List<Monster>型に格納される。
	Combi:		Monster3つを束ねたクラス。3つのフィールドを持つがそれは全てMonster型である。
						《スモール・ワールド》の始点-中継点-終点の関係をこのクラスに落とし込んだ。
						最終的に出力される検索結果はこれらの集合、即ちList<Combi>型である。
	
index.jsp内で用いられるセッションスコープ一覧:
	mlist:				List<Monster>型。ユーザーの入力内容の一覧である。
	clist:				List<Combi>型。検索によって出力された組合せCombiをまとめた物である。
	InErrMsg:			入力時のエラーメッセージ。
	SWErrMsg:			検索実行時(実行前チェック)のエラーメッセージ。
	combisize:		clistのサイズ。null時の扱い等を簡潔にする為に独自に設定。
	isSearch:			検索実行前/後を管理する為のパラメータ。

定数・テンプレート的な物はutilパッケージ内の3ファイルによって定義されている。(後述)

以下、アプリケーションの流れを幾つかのパートに分けて記す。

・アプリケーション開始＆データ入力時

SWMainServletのdoGetメソッドにてセッションスコープのmlist以外をremoveAttributeして初期化を行う。
isSearchには0を入れておく。
index.jspにフォワード。
↓
まずは上部フォーム欄にて各種数値を入力する
(基本的にrequired・selectの一項目目は全て(未選択)+class="erabenai"に。css処理実装時の為)
(input type="number"は0-5050の範囲で50刻みになる様に設定)
↓
SWMainServletに送信、doPostメソッドに飛ぶ
セッションスコープからmlistを取得、nullならばnew
セッションからmlist・InErrMsgを消去した後に
	
	フォームから受け取った情報(名前以外は予めInteger.parseIntで数値に変換)を基にErrcheck.javaを実行
		↓
	エラーがなければDupcheck.javaで名前の重複を確認、重複が認められなければMonsterを作成しmlistに加える。
		↓
	mlist・InErrMsgをセッションスコープに格納。
	この時エラーがなければInErrMsgはnull、あればJp.java内の何かしらが代入されている。
	↓
	index.jspにフォワード
という流れで処理が行われる。
↓
(以降繰り返し)

・CSVファイルからのデータ復元時
input type="file" name="restoredata"でCSVファイルを選択。
この時accept属性により.csv以外の形式は基本受け付けられない。
それをRestoreMonstersServletにsubmit
↓
doPostにてrequest.getPart()でCSVファイルを受け取り一時的に保存。
この時の絶対アドレスは変数zettaiの中身である。
(試験環境下ではC:￥pleiades￥2022-06￥tomcat￥9￥wtpwebapps￥smallWorld￥WEB-INF￥uploaded)
そのzettaiを渡したCSVReadLogic(の中のFileReader及びBufferReader)でCSVファイルから数値を取り出し
Monsterを作成してmlistに加えるwhileループを回し、得られたmlist及びInErrMsgをスコープに格納。
(この時、NumberFormatException・IOException、CSVの不整合があればmlistはnullである)
一時的に保存したCSVファイルをここで削除し、index.jspにフォワード。

・テンプレートからmlistへの追加
index.jspのselect name="temp"で項目を選択。この時の各項目のvalueはutil.templateConst.TEMPLATEの
インデックス番号と一致させてある。
それをSWtemplateServletへsubmit
↓
doPostで受け取りInteger.parseIntで数字に直した(tempvalue)後、前述のTEMPLATE[tempvalue]を
mlistに加える。これをセッションに格納しindex.jspにフォワード。

・


===========2-appendix.仕様上の動作===========
以下は仕様による物、および注意点である。
	・0と？の区別について
		《スモール・ワールド》の解説を見てもらえると分かる様に、実際には攻撃力・守備力が？である時の事も
		考えなくてはならない。この時、？と0は区別される(？同士・0同士は一致するが？と0は一致しない)べきである。
		攻撃力が？で守備力が0のカードも存在している為一概に処理を行うのは避けるべきである。
		その為このアプリケーション上では5050という数値を用意し、？を入力する時はこれを用いる事で解決しようとした。
		見た目上の問題は残ってしまったがそれは仕様という事にしてもらいたい。
	
	・テンプレート入力後の格納処理
		Dupcheckを行っていない為ここでは重複が発生する可能性がある。
		また、重複に関しての判断基準は完全に名前依存となっており(似通ったステータスの物を区別する為でもある)、
		「正式名称で入力した物」と「俗称や省略等を入力した物」は区別出来ない仕様となっている。
		この辺はユーザーの裁量に任せるしかない。
		
		
	・CSV読み込み後のmlistについて

===========3.パッケージ===========

modelパッケージ:		各種中身の内容について更に細分化(beans/check/logicの3つ)している。
servletパッケージ:	各種サーブレットが属する。
utilパッケージ:			定数・テンプレートの内容を定義したファイルが属している。
										ルール変更及びテンプレート項目の追加の際はこの中のファイルを変更すれば良い。

パッケージではないがWebContentフォルダ内に関しても記述しておく。
js:							JavaScriptファイルを格納。WEB-INFの外である事に注意。
WEB-INF/jsp:		index.jspを格納。今回は1枚絵を想定して開発した為他にファイルは存在しない。

(cssフォルダ・ファイルは現時点では未実装)


===========4.各種ファイル===========

model.beans
→model.check
→model.logic
→util
→servlet
→WEB-INF

の順に記載。


Monster
	今回基本となるJavaBeansのクラス。フィールドにn,c,t,l,a,dを持つ。
	(それぞれ名前/属性/種族/レベル/攻撃力/守備力を表す)
	(厳密には属性の英訳はAttributeだが色々と重複・衝突する為Categoryを基にcを採用した)
	c,t,l,a,dに関してはint型にして後述のJpクラス内の各種配列に対応する様にした。
	setterは作らずにコンストラクタの形をとった。

Combi
	JavaBeansのクラスで今回の検索の出力結果形式を表す。
	Monster型3つで構成される組(並べ替え不可)を表したいが為に作ったクラスなので
	フィールドにはMonster型のx,y,zを持つ。
	setterは作らず(以下略)
	
Dupcheck
	入力内容のnに対して重複の有無の確認を行う。引数はnとmlist。
	forループ(拡張for文)でmlistから取り出したMonsterに対してnが一致するかを確認し続ける。
	一致すれば(＝重複が見つかれば)falseを、一致が見つからず最後までループが回ればtrueを返す。
	
Errcheck
	入力内容から名前以外の5つのステータスについてのチェックを行う。よって引数は5つ。
	チェック内容は
			・数値c,t,l,a,dが適切な範囲内にあるかどうか
				(基本的に上限は後述のLimitConst以下となる様に。
					下限はlのみ1、それ以外は下限0で確認する様になっている)
			・a,dが50刻みの値をとっているかどうか
	である。エラーがなければtrueを返す。
	
AddMonsterLogic
	.execute()メソッドの引数はmとmlist。
	mlistの0番目にmを加えて返す処理を行う。
	ぶっちゃけ簡単な処理なのでこのファイルの必要性は薄いかもしれないが
	DB化等の改変がされる場合を考慮して(今の所その予定はないが)作成した。
	
AddCombiLogic
	.addc()メソッドの引数はm1,m2,m3,clist。
	与えられたm1,m2,m3を基にCombiクラスを作成、それをclistの0番目に加えて返す。
	
CSVReadLogic
	.convert()メソッドの引数はString型のpath。
	受け取って一時的に保存したCSVファイルのパス(絶対パス？現在の試験環境下ではコンピュータ内の物理パス)
	を受け取りFileReaderクラスで読み込みBufferReaderクラスで1行毎に処理を行っていく。
	BufferReader.readLine()がnullになるまでwhileループを回し、各行の内容をString[]型の配列を一旦経由した後
	Monsterクラスを作成、mlistに加えていき、最後にmlistを返す。
	whileループ最後のline再代入はforループのi++に相当する物(＝1行先に進む処理)である。
	また、何かしらのエラーがあった場合はnullを返し、これをRestoreMonstersServletで用いる。
	(各行の項目数の不一致、及びNumberFormatException・IOException)
	
SWLogic
	アーカイブとして残してあるファイル。元々は検索操作の中身を想定して書いていた。
	この内容は後述するSWTriLogicのcomparisonに引き継がれている。よってここでその説明を行う。
	
	《スモール・ワールド》の関係性(始点-中継点及び中継点-終点)を満たしているかを
	Monsterクラス2者間で確認する。引数はm1,m2でbooleanを返す。
	関係性を満たせばtrue、満たさなければfalseである。
	因みに判定はif文で行われ、「cが一致している時、aが一致すればfalse」という様に
	1項目が一致した時点で他項目の一致が見つかればfalse、1項目一致した後の他項目による篩い分けを抜けきればtrue、
	最後に一致項目がそもそも一つも存在しなければfalse
	という風に記述している。
	
SWTriLogic
	.execute()メソッドの引数はm1,m2,m3でbooleanを返す。
	前項目の中で述べたcomparisonメソッドを用いて
	「m1とm2が関係性を満たす」且つ「m2とm3が関係性を満たす」ならばtrueを返す。
	
SWTriSearchLogic
	.execute()メソッドの引数はmlistとnum(ループ回数制御に用いる。mlist.sizeを想定)。
	検索結果の集合としてclistを返す。
	3つの引数をとるSWTriLogic.execute()を完全に回しきる為に3重forループを行い、
	trueが返ってきた組合せに対してAddCombiLogic(＝Combiクラス作成+clist追加)を行う。
	3重forループを行う際、
						・同じ物が隣り合う事はない為i==j,j==kの時にcontinue
						・始点と終点が一致してしまうと意味が無い(これ自体はSWTriLogicをすり抜ける)ので除外
	の、以上2操作をこの段階で行っている。
	
Jp
	種族・属性の日本語をまとめたString[]型の定数、及び各種エラーメッセージを定義している。
	
LimitConst
	各種数値の上限値(Monster関連+CSVファイルのパラメータ数)をここで定数として定義している。
	その為JSPファイル内のループ制御は一部＜＝で行われている事に留意。
	
TemplateConst
	テンプレート入力の各種数値をMonsterクラスの定数として定義し、
	Monster[]型の定数TEMPLATE内にまとめている。
	テンプレート追加時はMonster追加とその内容のTEMPLATE追加の2つを行う必要がある。
	
SWMainServlet
	doGet
		アプリケーション開始時を想定した動作を行う。
		アプリケーション実行中に飛んでしまう事を想定してmlistのみ初期化を行わない様にしている。
	doPost
		フォームの入力内容を受け取って各種チェックを行った後にMonsterを作成、
		AddMonsterLogic.execute()を呼び出しmlistに加える。
		各種チェックの内容は先述したErrcheck・Dupcheckを参照。
		これらの処理を行った後、mlistとInErrMsgをスコープに格納(エラーの有無に関わらず)してフォワード
				
SWexeServlet
	doGet
		セッションスコープ内のmlistを基に検索を実行する。
		最後にclistとcombisizeとSWErrMsg(とisSearch)をスコープに入れるのでこの4つを消去。
		isSearchを1にした後numを設定。mlistがnullの時0、それ以外の時はmlist.size()となる様に。
		numが3以上の時にSWTriSearchLogicを呼び出し、検索結果を得る。(3未満の時は対応する言葉をSWErrMsgに代入)
		得られたclist,combisize,SWErrMsgをスコープに格納してフォワード

SWdeleteServlet
	doGet
		セッションスコープ内の6項目(一覧は2.基本的動作を参照。84行目辺り)を消去。
		isSearchのみ0を設定した後フォワード

SWtemplateServlet
	doPost
		フォームから送られたname="temp"の値を基に、TemplateConst.TEMPLATEの対応した項目を呼び出しmlistに加える。
		mlistがnullの時は新たにnewする。
		セッションスコープにmlistを加えてフォワード。

OutputCSVServlet
	doGet
		スコープからmlistを入手してその内容を基にCSVファイルを出力する。
		mlistがnullの時は何も行わずにフォワード。
		それ以外の場合はresponse.setHeaderで各種設定後
		拡張for文を用いてmlistの中身を書き起こし、CSVファイルとして出力する。
		この時のファイル名はInputLogSW_(日付).csvとなる。
		
RestoreMonstersServlet
	doPost
		前述のOutputCSVServletで出力したCSVファイルからmlistを復元する。
		フォームから送信されたCSVファイルをrequest.getPart()で受け取り
		一時的に保存する。
		この時サーブレット内のzettaiがそのファイルの絶対パス(試験環境下では物理パス？)となり、
		part.write(zettai)が実際に保存を行う処理である。
		
		その後先述のCSVReadLogic.convert()からmlistを作成し(nullが返ってきた場合はInErrMsgに代入)
		mlist,InErrMsgをセッションスコープに格納。
		一時ファイルを消去(part.delete())した後にフォワード。
