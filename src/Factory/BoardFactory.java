package Factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

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
 *         Factory f�r neue Spiel Graphn Hex Grid Ref :
 *         http://www.redblobgames.com/grids/hexagons/#rotation
 *
 */
abstract public class BoardFactory {

	static public Board creatBoard(Resource[][] myBoard) {

		Board resultBoard = new Board();
		resultBoard.setGameBoard(myBoard);

		Integer[] plaettchen = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};
		ArrayList<Integer> plaettchenList = new ArrayList<>(Arrays.asList(plaettchen));
		Collections.shuffle(plaettchenList);

		// Erstellen aller Resourcenfeld

		HashMap<String, Resourcenfeld> rfHash = new HashMap<String, Resourcenfeld>();

		int i = 0;
		for (int y = 0; y < myBoard.length; y++) {
			for (int x = 0; x < myBoard[y].length; x++) {
				Resource res = myBoard[y][x];

				int diceValue = 0;
				if (res != Resource.Wasser && res != Resource.W�ste && res != Resource.Void) {
					// diceValue = plaettchenList.get(i);
					diceValue = Const.defaultBoardDice[y][x];
					i++;
				}

				Resourcenfeld resf = new Resourcenfeld(res, diceValue, x, y);

				rfHash.put(x + ":" + y, resf);
			} // END FOR
		} // END FOR

		// Erzeugen aller Knoten und Verkn�pfung mit Feldern

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

						if (resf.getRes() != Resource.Wasser && resf.getRes() != Resource.Void) {
							list.add(resf);
						}
						if (res1.getRes() != Resource.Wasser && res1.getRes() != Resource.Void) {
							list.add(res1);
						}
						if (res2.getRes() != Resource.Wasser && res2.getRes() != Resource.Void) {
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

						if (resf.getRes() != Resource.Wasser && resf.getRes() != Resource.Void) {
							list.add(resf);
						}
						if (res3.getRes() != Resource.Wasser && res3.getRes() != Resource.Void) {
							list.add(res3);
						}
						if (res4.getRes() != Resource.Wasser && res4.getRes() != Resource.Void) {
							list.add(res4);
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

		// Nachbarschaft verk�pfen der Knoten
		// Erst m�glich nachdem alle Knoten erstellt wurden

		for (int y = 0; y < myBoard.length; y++) {
			for (int x = 0; x < myBoard[y].length; x++) {
				Resourcenfeld resf = rfHash.get(x + ":" + y);
				if (resf != null) {
					// Top Mid
					// x=0 , y=-1
					// x=+1 , y=-1
					Resourcenfeld res1 = rfHash.get((x) + ":" + (y - 1));
					Resourcenfeld res2 = rfHash.get((x + 1) + ":" + (y - 1));
					Resourcenfeld res3 = rfHash.get((x + 1) + ":" + (y - 2));

					if (res1 != null && res2 != null && res3 != null) {

						Knoten[] list = new Knoten[3];

						Knoten targetK = resf.getKnoten(KnotenName.TopMid);

						Knoten k1 = res1.getKnoten(KnotenName.BottomMid);

						Knoten k2 = res2.getKnoten(KnotenName.BottomMid);

						Knoten k3 = res3.getKnoten(KnotenName.BottomMid);


						if (k1 != null) {
							list[0] = k1;
						}
						if (k2 != null) {
							list[1] = k2;
						}
						if (k3 != null) {
							list[2] = k3;
						}

						if(targetK != null)
							targetK.setAllNeighbor(list);

					}

					// Bottom Mid
					// x=0 , y=+1
					// x=-1 , y=+1

					Resourcenfeld res4 = rfHash.get((x) + ":" + (y + 1));
					Resourcenfeld res5 = rfHash.get((x - 1) + ":" + (y + 1));
					Resourcenfeld res6 = rfHash.get((x - 1) + ":" + (y + 2));

					if (res4 != null && res5 != null && res6 != null) {

						Knoten[] list = new Knoten[3];

						Knoten targetK = resf.getKnoten(KnotenName.BottomMid);

						Knoten k1 = res4.getKnoten(KnotenName.TopMid);

						Knoten k2 = res5.getKnoten(KnotenName.TopMid);

						Knoten k3 = res6.getKnoten(KnotenName.TopMid);


						if (k1 != null) {
							list[0] = k1;
						}
						if (k2 != null) {
							list[1] = k2;
						}
						if (k3 != null) {
							list[2] = k3;
						}

						if(targetK != null)
							targetK.setAllNeighbor(list);

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
