package Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Data.Const.Resource;

public class Board {

	private ArrayList<Knoten> knotenListe;
	private HashMap<String, Resourcenfeld> resourcenfeldHashMap;

	private Resource[][] gameBoard; // vgl. Const.defaultBoard

	public ArrayList<Knoten> getKnotenListe() {
		return knotenListe;
	}

	public void setKnotenListe(ArrayList<Knoten> knotenListe) {
		this.knotenListe = knotenListe;
	}

	public HashMap<String, Resourcenfeld> getResourcenfeldHashMap() {
		return resourcenfeldHashMap;
	}

	public void setResourcenfeldHashMap(HashMap<String, Resourcenfeld> resourcenfeldHashMap) {
		this.resourcenfeldHashMap = resourcenfeldHashMap;
	}

	public Resource[][] getGameBoard() {
		return gameBoard;
	}
	
	public Knoten getBestKnoten(){
		ArrayList<Knoten> list = new ArrayList<>(knotenListe);
		Collections.sort(list);
		return list.get(0);
	}

	public Resourcenfeld getResourceAt(int i, int j) {
		HashMap<String, Resourcenfeld> map = getResourcenfeldHashMap();
		String key = i + ":" + j;
		if (map.containsKey(key)) {
			return map.get(key);
		}
		System.err.println("ResourceMaps does not contain key " + key);
		return null;
	}

	public void setGameBoard(Resource[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	public int getWidth() {
		return getGameBoard().length;
	}

	public int getHeight() {
		return getGameBoard()[0].length;
	}

}
