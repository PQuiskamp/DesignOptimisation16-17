package Data;
public class Resourcenfeld {

	private Const.Resource res;
	private float probability;
	
	public Resourcenfeld(Const.Resource type) {
		res = type;
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
		
}
