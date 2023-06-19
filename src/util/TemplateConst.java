package util;

import model.beans.Monster;

public class TemplateConst {

	private static Monster urara = new Monster("灰流うらら", 4, 2, 3, 0, 1800);
	private static Monster zoushokug = new Monster("増殖するG", 2, 9, 2, 500, 200);
	private static Monster droll = new Monster("ドロール＆ロックバード", 5, 1, 1, 0, 0);
	private static Monster nibiru = new Monster("原始生命態ニビル", 2, 18, 11, 3000, 600);
	private static Monster veiler = new Monster("エフェクト・ヴェーラー", 0, 1, 1, 0, 0);
	private static Monster lancea = new Monster("アーティファクト-ロンギヌス", 0, 8, 5, 1700, 2300);
	private static Monster gadarla = new Monster("怪粉壊獣ガダーラ", 5, 9, 8, 2700, 1600);
	private static Monster ghrabbit = new Monster("幽鬼うさぎ", 0, 20, 3, 0, 1800);
	private static Monster warashi = new Monster("屋敷わらし", 2, 2, 3, 0, 1800);
	private static Monster rerabbit = new Monster("レスキューラビット", 2, 5, 4, 300, 100);
	private static Monster pankratops = new Monster("パンクラトプス", 2, 10, 7, 2600, 0);
	private static Monster crow = new Monster("D.D.クロウ", 1, 6, 1, 100, 100);
	private static Monster gamma = new Monster("PSYフレームギア・γ", 0, 20, 2, 1000, 0);
	private static Monster corridor = new Monster("ネメシス・コリドー", 5, 15, 4, 1900, 600);
	private static Monster keystone = new Monster("ネメシス・キーストーン", 2, 18, 1, 700, 0);
	private static Monster envoy = new Monster("開闢", 0, 3, 8, 3000, 2500);
	private static Monster snow = new Monster("シラユキ", 0, 1, 4, 1850, 1000);
	private static Monster reflector = new Monster("リフレクター", 1, 20, 1, 400, 300);
	private static Monster shifter = new Monster("アトラクター", 1, 1, 6, 1200, 2200);
	
//	private static Monster =new Monster("",,,,,);

	public static final Monster[] TEMPLATE = { urara, zoushokug, nibiru, veiler, gamma, ghrabbit, warashi, lancea,
			droll, shifter, crow, pankratops, gadarla, corridor, keystone, rerabbit, snow, reflector, envoy };
}
