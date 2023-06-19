package model.beans;

import java.io.Serializable;

public class Combi implements Serializable {
	private Monster x;
	private Monster y;
	private Monster z;

	public Combi() {
	}

	public Combi(Monster x, Monster y, Monster z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Monster getX() {
		return x;
	}

	public Monster getY() {
		return y;
	}

	public Monster getZ() {
		return z;
	}

}
