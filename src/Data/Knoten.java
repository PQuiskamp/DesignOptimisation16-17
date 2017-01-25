package Data;

import java.util.*;

import game.Player;

public class Knoten implements Comparable<Knoten> {

	private Knoten[] neighbors;
	private Resourcenfeld[] fields;
	private Player owner;

	public Knoten() {
		neighbors = new Knoten[]{};
		fields = new Resourcenfeld[]{};
	}

	// Bewertung des Knotens
	private float score;

	public Knoten getNeighbor(int index) {
		return neighbors[index];
	}

	public void setNeighbor(int index, Knoten tempk) {
		neighbors[index] = tempk;
	}

	public Knoten[] getAllNeighbor() {
		return neighbors;
	}

	public void setAllNeighbor(Knoten[] tempk) {
		neighbors = tempk;
	}

	public Resourcenfeld getField(int index) {
		return fields[index];
	}

	public void setField(int index, Resourcenfeld tempf) {
		fields[index] = tempf;
	}

	public Resourcenfeld[] getAllField() {
		return fields;
	}

	public void setAllField(Resourcenfeld[] tempf) {
		fields = tempf;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Player getOwner() {
		return owner;
	}

	public void updateScore(int round, Player activePlayer) {
		if(!isClaimable() || hasOwner()){
			setScore(0);
			return;
		}
		
		float score = 0f;

		HashMap<Const.Resource, Integer> resourceIntegerHashMap = new HashMap<>();
		ArrayList<Resourcenfeld> resfelder = new ArrayList<>(Arrays.asList(fields));
		resfelder.sort((o1, o2) -> Float.compare(o2.getProbability(), o1.getProbability()));
		for(Resourcenfeld f: resfelder) {
			if(f == null)
				continue;
			float prob = f.getProbability();
			prob = prob * activePlayer.getNeedsModified(f.getRes(),
					resourceIntegerHashMap.get(f.getRes())==null?0:resourceIntegerHashMap.get(f.getRes()));
			score += prob;

			if(resourceIntegerHashMap.containsKey(f.getRes())) {
				resourceIntegerHashMap.put(f.getRes(), resourceIntegerHashMap.get(f.getRes())+1);
			} else {
				resourceIntegerHashMap.put(f.getRes(), 1);
			}
		}

		setScore(score);
	}

	public boolean isClaimable() {
		for(Knoten k: neighbors) {
			if(k == null)
				continue;
			if (k.hasOwner())
				return false;
		}
		return !hasOwner();
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public boolean hasOwner() {
		return getOwner() != null;
	}

	@Override
	public int compareTo(Knoten k) {
		return Float.compare(k.getScore(), getScore());
	}

}
