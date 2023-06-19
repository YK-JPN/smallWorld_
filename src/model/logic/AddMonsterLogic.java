package model.logic;

import java.util.List;

import model.beans.Monster;

public class AddMonsterLogic {

	public void execute(Monster m, List<Monster> mlist) {

		mlist.add(0, m);
	}

}
