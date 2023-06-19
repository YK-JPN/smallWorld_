/**
 * 
 */
 
"use strict";


// 参考:　https://cyzennt.co.jp/blog/2021/02/04/
//	(FileReader)		https://qumeru.com/magazine/465
//
//	(CSV)				https://rainbow-engine.com/jspservlet-csv-download/
//						https://rainbow-engine.com/java-csv-write/



function SWinput_LogOutput(){
        let str = document.getElementById('SWinput').textContent; // 出力文字列
        let ary = str.split(''); // 配列形式に変換（後述のBlobで全要素出力）
        let blob = new Blob(ary,{type:"text/plan"}); // テキスト形式でBlob定義
        let link = document.createElement('a'); // HTMLのaタグを作成
        link.href = URL.createObjectURL(blob); // aタグのhref属性を作成
        link.download = 'Log_SWinput.txt'; // aタグのdownload属性を作成
        link.click(); // 定義したaタグをクリック（実行）
      }



function SWresults_LogOutput(){
        let str = document.getElementById('SWresults').textContent; // 出力文字列
        let ary = str.split(''); // 配列形式に変換（後述のBlobで全要素出力）
        let blob = new Blob(ary,{type:"text/plan"}); // テキスト形式でBlob定義
        let link = document.createElement('a'); // HTMLのaタグを作成
        link.href = URL.createObjectURL(blob); // aタグのhref属性を作成
        link.download = 'Log_SWresults.txt'; // aタグのdownload属性を作成
        link.click(); // 定義したaタグをクリック（実行）
      }