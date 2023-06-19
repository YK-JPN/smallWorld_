package model.check;

import java.util.List;

import model.beans.Monster;

public class Dupcheck {
	public static boolean execute(String n, List<Monster> mlist) {
		if (mlist != null) {
			for (Monster m : mlist) {
				boolean dupHit = n.equals(m.getN());
				if (dupHit) {
					return false;
				}
			}
		}
		return true;
	}
}
