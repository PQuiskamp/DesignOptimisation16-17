package game;

import java.awt.Color;
import java.util.ArrayList;

import Data.Knoten;
import Data.Resourcenfeld;

public class Player {

	private Color color;
	private Game game;

	public Player(Game game, Color color) {
		this.color = color;
		this.game = game;
	}

	public ArrayList<Knoten> getOwnedKnoten() {
		ArrayList<Knoten> liste = new ArrayList<>();
		for (Knoten k : game.getBoard().getKnotenListe()) {
			if (k.getOwner() == this) {
				liste.add(k);
			}
		}

		return liste;
	}

	public ArrayList<Resourcenfeld> getOwnedResourcenFeld() {
		ArrayList<Resourcenfeld> liste = new ArrayList<>();

		for (Knoten k : getOwnedKnoten()) {
			for (Resourcenfeld feld : k.getAllField()) {
				if (!liste.contains(feld)) {
					liste.add(feld);
				}
			}
		}

		return liste;
	}

	public Color getColor() {
		return color;
	}

}
