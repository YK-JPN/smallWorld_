package model.logic;

import java.util.ArrayList;
import java.util.List;

import model.beans.Combi;
import model.beans.Monster;

public class SWTriSearchLogic {
	public List<Combi> execute(List<Monster> mlist, int num) {

		List<Combi> combilist = new ArrayList<>();
		AddCombiLogic acl = new AddCombiLogic();

		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				if (j == i) {
					continue;
				}
				for (int k = 0; k < num; k++) {
					if (k == j) {
						continue;
					} else if (SWTriLogic.execute(mlist.get(i), mlist.get(j), mlist.get(k)) && i != k) {
						acl.addc(mlist.get(i), mlist.get(j), mlist.get(k), combilist);
					}
				}
			}
		}
		return combilist;
	}
}
