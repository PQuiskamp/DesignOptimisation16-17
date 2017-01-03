package Data;

public class Knoten {

	private Knoten[] neighbors;
	private Resourcenfeld[] fields;
	
	// Bewertung des Knotens
	private int Score;
	
	public Knoten getNeighbor(int index) {
		return neighbors[index];
	}
	
	public void setNeighbor(int index, Knoten tempk) {
		neighbors[index] = tempk;
	}
	
	public Knoten[] getAllNeighbor() {
		return neighbors;
	}
	
	public void getAllNeighbor(Knoten[] tempk) {
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
	
	public void getAllField(Resourcenfeld[] tempf) {
		fields = tempf;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}
	
}
