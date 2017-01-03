package Factory;

import java.util.HashMap;
import java.util.Iterator;

import Data.Const;
import Data.Const.Resource;
import Data.Resourcenfeld;

public class BoardFactory {
	
	int offset = 2;
	
	Resource[][] resourceBoard = Const.defaultBoard;
	
	HashMap<String, Resourcenfeld> rfHash;
	
	public void creatBoard(){
		
		rfHash = new HashMap<String, Resourcenfeld>();
		
		for (int y = 0; y < resourceBoard.length; y++) {
			for (int x = 0; x < resourceBoard[y].length; x++) {
				Resource res = resourceBoard[y][x];
				if(res != Resource.Wasser){
					//TODO add probability
					Resourcenfeld resf = new Resourcenfeld(res, 0f, x, y);
					rfHash.put(x+":"+y, resf);
				}
			}
		}
		
		
		for (int y = 0; y < resourceBoard.length; y++) {
			for (int x = 0; x < resourceBoard[y].length; x++) {
				Resourcenfeld resf = rfHash.get(x+":"+y);
				if(resf != null){
					// Top Left
					// x=0  , y=-1
					// x=-1 , y=0
					// Top Mid 
					// x=0  , y=-1
					// x=+1 , y=-1
					// Top Right
					// x=+1 , y=0
					// x=+1 , y=-1
					// Bottom Left
					// x=-1 , y=0
					// x=-1 , y=+1
					// Bottom Mid
					// x=0  , y=+1
					// x=-1 , y=+1
					// Bottom Right
					// x=0  , y=+1
					// x=+1 , y=0
				}
			}
		}
		
	}

}
