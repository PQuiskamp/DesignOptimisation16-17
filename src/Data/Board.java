package Data;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	
	private ArrayList<Knoten> knotenListe;
	private HashMap<String, Resourcenfeld> resourcenfeldHashMap;

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
	
}
