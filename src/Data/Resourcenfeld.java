package Data;

import Data.Const.Resource;

public class Resourcenfeld {

	private Const.Resource res;
	private float probability;
	
	private int x;
	private int y;
	
	public Resourcenfeld(Const.Resource type) {
		res = type;
	}
	
	public Resourcenfeld(Resource res, float probability, int x, int y) {
		super();
		this.res = res;
		this.probability = probability;
		this.x = x;
		this.y = y;
	}

	public Const.Resource getRes() {
		return res;
	}

	public void setRes(Const.Resource res) {
		this.res = res;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
		
}
