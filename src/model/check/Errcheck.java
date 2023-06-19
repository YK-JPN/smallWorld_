package model.check;

import util.LimitConst;

public class Errcheck {
	public static boolean execute(int c, int t, int l, int a, int d) {

		if (c <= LimitConst.CLIMIT && c >= 0			// 属性
				&& t <= LimitConst.TLIMIT && t >= 0	// 種族
				&& l <= LimitConst.LLIMIT && l >= 1	// レベル
				&& a <= LimitConst.ALIMIT && a >= 0	// 攻撃力
				&& d <= LimitConst.DLIMIT && d >= 0	// 守備力
				&& a % 50 == 0							// 攻撃力の刻み
				&& d % 50 == 0) {						// 守備力の刻み
			return true;
		} else {
			return false;
		}
	}
}
