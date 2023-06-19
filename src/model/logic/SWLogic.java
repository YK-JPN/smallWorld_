package model.logic;

import model.beans.Monster;

public class SWLogic {
	
//	結果としてこのファイルは使用しない
	public boolean execute(Monster m1, Monster m2) {
		int c1 = m1.getC();
		int t1 = m1.getT();
		int l1 = m1.getL();
		int a1 = m1.getA();
		int d1 = m1.getD();

		int c2 = m2.getC();
		int t2 = m2.getT();
		int l2 = m2.getL();
		int a2 = m2.getA();
		int d2 = m2.getD();

		if (c1 == c2) {
			if (t1 == t2) {
				return false;
			} else if (l1 == l2) {
				return false;
			} else if (a1 == a2) {
				return false;
			} else if (d1 == d2) {
				return false;
			} else {
				return true;// cのみ一致
			}
		} else if (t1 == t2) {
			if (l1 == l2) {
				return false;
			} else if (a1 == a2) {
				return false;
			} else if (d1 == d2) {
				return false;
			} else {
				return true;// tのみ一致
			}
		} else if (l1 == l2) {
			if (a1 == a2) {
				return false;
			} else if (d1 == d2) {
				return false;
			} else {
				return true;// lのみ一致
			}
		} else if (a1 == a2) {
			if (d1 == d2) {
				return false;
			} else {
				return true;// aのみ一致
			}
		} else if (d1 == d2) {
			return true;// dのみ一致
		} else {
			return false;// 全て不一致
		}
	}

}
