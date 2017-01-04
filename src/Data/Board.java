package Data;

import java.util.ArrayList;
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

	public void setGameBoard(Resource[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

}
