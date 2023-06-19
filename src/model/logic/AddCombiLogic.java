package model.logic;

import java.util.List;

import model.beans.Combi;
import model.beans.Monster;

public class AddCombiLogic {
	public void addc(Monster m1, Monster m2, Monster m3, List<Combi> clist) {
		Combi combi = new Combi(m1, m2, m3);
		clist.add(0, combi);
	}
}
