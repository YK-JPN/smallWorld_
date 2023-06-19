package model.beans;

import java.io.Serializable;

public class Monster implements Serializable {
//	各種ステータス設定のフィールド
//	それぞれ上から名前・属性・種族・レベル・攻撃力・守備力で、
//	属性などの日本語名はutil内のファイル(Jp)に記述

	private String n;
	private int c;// range: 0 - 6
	private int t;// range: 0 - 24
	private int l;// range: 1 - 12
	private int a;// range: 0 - 5050
	private int d;// range: 0 - 5050

	public Monster() {
	}

	public Monster(String n, int c, int t, int l, int a, int d) {
		this.n = n;
		this.c = c;
		this.t = t;
		this.l = l;
		this.a = a;
		this.d = d;
	}

	public String getN() {
		return n;
	}

	public int getC() {
		return c;
	}

	public int getT() {
		return t;
	}

	public int getL() {
		return l;
	}

	public int getA() {
		return a;
	}

	public int getD() {
		return d;
	}
}