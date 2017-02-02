package Factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import Data.Board;
import Data.Const;
import Data.Const.KnotenName;
import Data.Const.Resource;
import log.Log;
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

	static public Board creatBoard(Resource[][] myBoard, Random rng) {
		Board resultBoard = new Board();
		resultBoard.setGameBoard(myBoard);
		LinkedList<Integer> plaettchenList = null;
		if (rng != null) {
			Integer[] plaettchen = { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 };
			plaettchenList = new LinkedList<>(Arrays.asList(plaettchen));
			Collections.shuffle(plaettchenList, rng);
		}

		// Erstellen aller Resourcenfeld

		HashMap<String, Resourcenfeld> rfHash = new HashMap<String, Resourcenfeld>();

		for (int y = 0; y < myBoard.length; y++) {
			for (int x = 0; x < myBoard[y].length; x++) {
				Resource res = myBoard[y][x];

				int diceValue = 0;
				if (res != Resource.Wasser && res != Resource.Wüste && res != Resource.Void) {
					int dv = Const.defaultBoardDice[y][x];
					if (rng != null && dv != 0) {
						Log.log("Remaining resource value plättchen in the bag: "
								+ Arrays.toString(plaettchenList.toArray()));
						dv = plaettchenList.pop();
					}

					diceValue = dv;
				}

				Resourcenfeld resf = new Resourcenfeld(res, diceValue, x, y);

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
					Resourcenfeld res3 = rfHash.get((x + 1) + ":" + (y - 2));


					Knoten[] list = new Knoten[3];

					Knoten targetK = resf.getKnoten(KnotenName.TopMid);

					if (res1 != null) {
						Knoten k1 = res1.getKnoten(KnotenName.BottomMid);
						if (k1 != null) {
							list[0] = k1;
						}
					}
					if (res2 != null) {
						Knoten k2 = res2.getKnoten(KnotenName.BottomMid);
						if (k2 != null) {
							list[1] = k2;
						}
					}
					if (res3 != null) {
						Knoten k3 = res3.getKnoten(KnotenName.BottomMid);
						if (k3 != null) {
							list[2] = k3;
						}
					}

					if (targetK != null)
						targetK.setAllNeighbor(list);


					// Bottom Mid
					// x=0 , y=+1
					// x=-1 , y=+1

					Resourcenfeld res4 = rfHash.get((x) + ":" + (y + 1));
					Resourcenfeld res5 = rfHash.get((x - 1) + ":" + (y + 1));
					Resourcenfeld res6 = rfHash.get((x - 1) + ":" + (y + 2));

					list = new Knoten[3];

					targetK = resf.getKnoten(KnotenName.BottomMid);

					if(res4 != null) {
						Knoten k1 = res4.getKnoten(KnotenName.TopMid);
						if (k1 != null) {
							list[0] = k1;
						}
					}
					if(res5 != null) {
						Knoten k2 = res5.getKnoten(KnotenName.TopMid);
						if (k2 != null) {
							list[1] = k2;
						}
					}
					if(res6 != null) {
						Knoten k3 = res6.getKnoten(KnotenName.TopMid);
						if (k3 != null) {
							list[2] = k3;
						}
					}

					if (targetK != null)
						targetK.setAllNeighbor(list);


				} // END IF
			} // END FOR
		} // END FOR

		resultBoard.setKnotenListe(knotenListe);
		resultBoard.setResourcenfeldHashMap(rfHash);

		return resultBoard;

	} // End FUNCTION

	static public Board creatBoard(Random rng) {
		LinkedList<Resource> resourceBag = new LinkedList<>();
		Resource[][] defaultResource = Const.defaultBoard;
		Resource[][] boardSetup = new Resource[defaultResource.length][defaultResource[0].length];

		int removedCount = 0;
		for (int i = 0; i < boardSetup.length; i++)
			for (int j = 0; j < boardSetup[0].length; j++) {
				Resource res = defaultResource[i][j];
				if (res != Resource.Wüste && res != Resource.Wasser && res != Resource.Void) {
					resourceBag.add(res);
					removedCount++;
					boardSetup[i][j] = null;
				} else {
					boardSetup[i][j] = res;
				}
			}
		Log.log("Removed tiles: " + removedCount + " Bag size: " + resourceBag.size());
		Collections.shuffle(resourceBag, rng);

		for (int i = 0; i < boardSetup.length; i++)
			for (int j = 0; j < boardSetup[0].length; j++) {
				if (boardSetup[i][j] == null) {
					boardSetup[i][j] = resourceBag.pop();
				}
			}
		Log.log("After shuffle bag size: " + resourceBag.size());

		return creatBoard(boardSetup, rng);
	};

	static public Board creatBoard() {
		return creatBoard(Const.defaultBoard, null);
	};
}
