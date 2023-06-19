package util;

public class Jp {
//	各種フィールドのintの値を日本語に対応させる。
	public static final String[] CJP = { "光", "闇", "地", "水", "炎", "風", "神" };
//　6属性+1	
	
	public static final String[] TJP = {"ドラゴン","魔法使い","アンデット","戦士","獣戦士","獣","鳥獣","悪魔","天使","昆虫","恐竜","爬虫類","魚","海竜","機械","雷","水","炎","岩石","植物","サイキック","幻竜","サイバース","幻神獣","創造神"};
//	25種族
	
//	String型のInErrMsgの中身。Errcheck及びDupcheckに引っかかった場合である。
//	(追記:RestoreMonstersServletの結果でエラーを吐いた時の処理も追加した)
	public static final String INPUT_IS_ERR = "入力された数値に誤りがあります。";
	public static final String NAME_DUPULICATION="名前が重複しています。";
	public static final String CSV_ERR = "CSVの読み込みに失敗しました。";
	
//	SWexeサーブレットにPOSTする直前のチェックでエラーが見つかった場合の文言。
	public static final String NO_MONSTER="入力内容がありません。";
	public static final String TOO_SMALL_SIZE="入力内容が少な過ぎます。";
}
