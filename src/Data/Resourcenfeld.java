package Data;

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

	private static float[] probabilities = {0,0,
			2.778f,
			5.556f,
			8.333f,
			11.111f,
			13.889f,
			16.667f,
			13.889f,
			11.111f,
			8.333f,
			5.556f,
			2.778f
	};

	public Resourcenfeld(Resource type) {
		res = type;
		knotenMap = new HashMap<KnotenName, Knoten>();
	}

	public Resourcenfeld(Resource res, int diceValue, int x, int y) {
		this.res = res;
		this.probability = calculateProbability(diceValue);
		this.x = x;
		this.y = y;
		this.diceValue = diceValue;
		knotenMap = new HashMap<KnotenName, Knoten>();
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

	public static float calculateProbability(int diceValue) {
		if(diceValue < 0 || diceValue > 12)
			return 0f;
		return probabilities[diceValue];
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
