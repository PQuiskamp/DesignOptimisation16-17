package Data;

import java.util.Random;

import game.Player;

public class Knoten implements Comparable<Knoten> {

	private Knoten[] neighbors;
	private Resourcenfeld[] fields;
	private Player owner;

	// Bewertung des Knotens
	private int score;

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Player getOwner() {
		return owner;
	}

	public void updateScore(int round, Player activePlayer) {
		// TODO update Score here!
		if (!isClaimable()) {
			setScore(0);
			return;
		}

		setScore((int) Math.abs(new Random().nextGaussian() * 100));
	}

	public boolean isClaimable() {
		// TODO determine if this knoten is able to claimed by a player

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
		return k.getScore() - getScore();
	}

}
