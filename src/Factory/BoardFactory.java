package Factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import Data.Board;
import Data.Const;
import Data.Const.KnotenName;
import Data.Const.Resource;
import Data.Knoten;
import Data.Resourcenfeld;

/**
 * 
 * @author Patrick
 *
 *         Factory für neue Spiel Graphn Hex Grid Ref :
 *         http://www.redblobgames.com/grids/hexagons/#rotation
 *
 */
abstract public class BoardFactory {

	static public Board creatBoard(Resource[][] myBoard) {

		Board resultBoard = new Board();
		resultBoard.setGameBoard(myBoard);

		// Erstellen aller Resourcenfeld

		HashMap<String, Resourcenfeld> rfHash = new HashMap<String, Resourcenfeld>();

		for (int y = 0; y < myBoard.length; y++) {
			for (int x = 0; x < myBoard[y].length; x++) {
				Resource res = myBoard[y][x];
				// TODO add probability
				// TODO add diceValue
				Resourcenfeld resf = new Resourcenfeld(res, 0f, x, y);

				if (res != Resource.Wasser && res != Resource.Wüste) {
					resf.setDiceValue(new Random().nextInt(11) + 1);
				}

				rfHash.put(x + ":" + y, resf);
			} // END FOR
		} // END FOR

		// Erzeugen aller Knoten und Verknüpfung mit Feldern

		ArrayList<Knoten> knotenListe = new ArrayList<Knoten>();

		for (int y = 0; y < myBoard.length; y++) {
			for (int x = 0; x < myBoard[y].length; x++) {
				Resourcenfeld resf = rfHash.get(x + ":" + y);
				if (resf != null) {
					// Top Mid
					// x=0 , y=-1
					// x=+1 , y=-1
					Resourcenfeld res1 = rfHash.get((x) + ":" + (y - 1));
					Resourcenfeld res2 = rfHash.get((x + 1) + ":" + (y - 1));

					if (res1 != null && res2 != null) {

						ArrayList<Resourcenfeld> list = new ArrayList<Resourcenfeld>();

						if (resf.getRes() != Resource.Wasser) {
							list.add(resf);
						}
						if (res1.getRes() != Resource.Wasser) {
							list.add(res1);
						}
						if (res2.getRes() != Resource.Wasser) {
							list.add(res2);
						}

						// Erstelle knoten wenn es min. 1 Feld gibt mit Res !=
						// Wasser
						if (list.size() > 0) {
							Knoten k = new Knoten();
							Resourcenfeld[] kArray = new Resourcenfeld[list.size()];
							kArray = list.toArray(kArray);
							k.setAllField(kArray);
							knotenListe.add(k);

							resf.setKnoten(KnotenName.TopMid, k);
							res1.setKnoten(KnotenName.BottomRight, k);
							res2.setKnoten(KnotenName.BottomLeft, k);
						}

					}
					;

					// Bottom Mid
					// x=0 , y=+1
					// x=-1 , y=+1

					Resourcenfeld res3 = rfHash.get((x) + ":" + (y + 1));
					Resourcenfeld res4 = rfHash.get((x - 1) + ":" + (y + 1));

					if (res3 != null && res4 != null) {

						ArrayList<Resourcenfeld> list = new ArrayList<Resourcenfeld>();

						if (resf.getRes() != Resource.Wasser) {
							list.add(resf);
						}
						if (res3.getRes() != Resource.Wasser) {
							list.add(res1);
						}
						if (res4.getRes() != Resource.Wasser) {
							list.add(res2);
						}

						// Erstelle knoten wenn es min. 1 Feld gibt mit Res !=
						// Wasser
						if (list.size() > 0) {
							Knoten k = new Knoten();
							Resourcenfeld[] kArray = new Resourcenfeld[list.size()];
							kArray = list.toArray(kArray);
							k.setAllField(kArray);
							knotenListe.add(k);

							resf.setKnoten(KnotenName.BottomMid, k);
							res3.setKnoten(KnotenName.TopLeft, k);
							res4.setKnoten(KnotenName.TopRight, k);
						}

					}
				} // END IF
			} // END FOR
		} // END FOR

		// Nachbarschaft verküpfen der Knoten
		// Erst möglich nachdem alle Knoten erstellt wurden

		for (int y = 0; y < myBoard.length; y++) {
			for (int x = 0; x < myBoard[y].length; x++) {
				Resourcenfeld resf = rfHash.get(x + ":" + y);
				if (resf != null) {
					// Top Mid
					// x=0 , y=-1
					// x=+1 , y=-1
					Resourcenfeld res1 = rfHash.get((x) + ":" + (y - 1));
					Resourcenfeld res2 = rfHash.get((x + 1) + ":" + (y - 1));

					if (res1 != null && res2 != null) {

						ArrayList<Knoten> list = new ArrayList<Knoten>();

						Knoten targetK = resf.getKnoten(KnotenName.TopMid);

						Knoten k1 = res1.getKnoten(KnotenName.BottomMid); // ==
																			// TopLeft
						Knoten k2 = res2.getKnoten(KnotenName.BottomMid); // ==
																			// TopRight
						Knoten k3 = res1.getKnoten(KnotenName.TopRight); // ==
																			// k4
						Knoten k4 = res2.getKnoten(KnotenName.TopLeft);

						if (k1 != resf.getKnoten(KnotenName.TopLeft)) {
							list.add(k1);
						}
						if (k2 != resf.getKnoten(KnotenName.TopRight)) {
							list.add(k2);
						}
						if (k3 != k4) {
							list.add(k3);
						}

						if (list.size() > 0) {
							targetK.setAllNeighbor((Knoten[]) list.toArray());
						}
					}

					// Bottom Mid
					// x=0 , y=+1
					// x=-1 , y=+1

					Resourcenfeld res3 = rfHash.get((x) + ":" + (y + 1));
					Resourcenfeld res4 = rfHash.get((x - 1) + ":" + (y + 1));

					if (res3 != null && res4 != null) {

						ArrayList<Knoten> list = new ArrayList<Knoten>();

						Knoten targetK = resf.getKnoten(KnotenName.BottomMid);

						Knoten k1 = res3.getKnoten(KnotenName.TopMid); // ==
																		// BottomRight
						Knoten k2 = res4.getKnoten(KnotenName.TopMid); // ==
																		// BottomLeft
						Knoten k3 = res3.getKnoten(KnotenName.BottomLeft); // ==
																			// k4
						Knoten k4 = res4.getKnoten(KnotenName.BottomRight);

						if (k1 != resf.getKnoten(KnotenName.BottomRight)) {
							list.add(k1);
						}
						if (k2 != resf.getKnoten(KnotenName.BottomLeft)) {
							list.add(k2);
						}
						if (k3 != k4) {
							list.add(k3);
						}

						if (list.size() == 3) {
							targetK.setAllNeighbor((Knoten[]) list.toArray());
						}

					}

				} // END IF
			} // END FOR
		} // END FOR

		resultBoard.setKnotenListe(knotenListe);
		resultBoard.setResourcenfeldHashMap(rfHash);

		return resultBoard;

	} // End FUNCTION

	static public Board creatBoard() {
		return creatBoard(Const.defaultBoard);
	};
}
