package game;

import java.awt.Color;
import java.util.ArrayList;

import Data.Const;
import Data.Knoten;
import Data.Resourcenfeld;

public class Player {

	private Color color;
	private Game game;

	private int nHolz, nLehm, nErz, nWeizen, nSchaf;

	private static float[][] modTable = {
		{1f, 0.7f, 0.55f, 0.10f},    // Holz
		{1f, 0.7f, 0.5f, 0.10f},     // Lehm
		{0.8f, 0.6f, 0.25f, 0.05f},  // Erz
		{0.8f, 0.65f, 0.25f, 0.05f}, // Getreide
		{0.8f, 0.25f, 0.1f, 0.03f}   // Wolle
	};

	public Player(Game game, Color color) {
		this.color = color;
		this.game = game;
	}

	public float getNeeds(Const.Resource r) {
		switch(r) {
			case Holz:
				return modTable[0][nHolz>3?3:nHolz];
			case Lehm:
				return modTable[1][nLehm>3?3:nLehm];
			case Erz:
				return modTable[2][nErz>3?3:nErz];
			case Weizen:
				return modTable[3][nWeizen>3?3:nWeizen];
			case Schaf:
				return modTable[4][nSchaf>3?3:nSchaf];
			default:
				return 0;
		}
	}

	public float getNeedsModified(Const.Resource r, int n) {
		switch(r) {
			case Holz:
				return modTable[0][(nHolz+n)>3?3:(nHolz+n)];
			case Lehm:
				return modTable[1][(nLehm+n)>3?3:(nLehm+n)];
			case Erz:
				return modTable[2][(nErz+n)>3?3:(nErz+n)];
			case Weizen:
				return modTable[3][(nWeizen+n)>3?3:(nWeizen+n)];
			case Schaf:
				return modTable[4][(nSchaf+n)>3?3:(nSchaf+n)];
			default:
				return 0;
		}
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

	public void setResourceCount(Const.Resource r, int n) {
		switch(r) {
			case Holz:
				nHolz = n;
				break;
			case Lehm:
				nLehm = n;
				break;
			case Schaf:
				nSchaf = n;
				break;
			case Erz:
				nErz = n;
				break;
			case Weizen:
				nWeizen = n;
				break;
			default:
				break;
		}
	}
	public int getResourceCount(Const.Resource r) {
		switch(r) {
			case Holz:
				return nHolz;
			case Lehm:
				return nLehm;
			case Schaf:
				return nSchaf;
			case Erz:
				return nErz;
			case Weizen:
				return nWeizen;
			default:
				return 0;
		}
	}

	public void updateResourceNumbers() {
		for(Resourcenfeld r: getOwnedResourcenFeld()) {
			setResourceCount(r.getRes(), getResourceCount(r.getRes()) + 1);
		}
	}

	public Color getColor() {
		return color;
	}

}
