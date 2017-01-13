package Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import Data.Const.KnotenName;
import Data.Const.Resource;

public class Resourcenfeld {

	private Const.Resource res;
	private float probability;

	private HashMap<KnotenName, Knoten> knotenMap;

	private int x;
	private int y;
	private int diceValue;

	public Resourcenfeld(Resource type) {
		res = type;
		knotenMap = new HashMap<KnotenName, Knoten>();
		setDiceValue(-1);
	}

	public Resourcenfeld(Resource res, float probability, int x, int y) {
		this.res = res;
		this.probability = probability;
		this.x = x;
		this.y = y;
		knotenMap = new HashMap<KnotenName, Knoten>();
		setDiceValue(-1);
	}

	public int getDiceValue() {
		return diceValue;
	}

	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
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

	public Knoten getKnoten(KnotenName key) {
		return knotenMap.get(key);
	}

	public Set<KnotenName> getKnotenKeys() {
		return knotenMap.keySet();
	}

	public void setKnoten(KnotenName key, Knoten knoten) {

		// TODO Debug
		if (knotenMap.get(key) != null)
			System.out.println("Override Knoten :" + key.toString() + " at" + x + " , " + y);
		if (knotenMap.values().size() > 6)
			System.out.println("Overflow Knoten :" + key.toString() + " at" + x + " , " + y);

		knotenMap.put(key, knoten);
	}

	public Collection<Knoten> getKnotenList() {
		return knotenMap.values();
	}

}
