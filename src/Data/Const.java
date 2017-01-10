package Data;

public abstract class Const {

	static public enum Resource {
		Lehm, Holz, Erz, Schaf, Weizen, Wüste, Wasser
	};
	
	static public enum KnotenName {
		TopLeft, TopMid, TopRight, BottomLeft, BottomMid, BottomRight
	}
	
	static public Resource[][] defaultBoard = {
			{Resource.Wasser, Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser},
			{Resource.Wasser, Resource.Wasser, 	Resource.Wasser, 	Resource.Holz, 		Resource.Weizen, 	Resource.Erz, 		Resource.Wasser},
			{Resource.Wasser, Resource.Wasser, 	Resource.Lehm, 		Resource.Schaf, 	Resource.Lehm, 		Resource.Erz, 		Resource.Wasser},
			{Resource.Wasser, Resource.Erz, 	Resource.Schaf, 	Resource.Wüste, 	Resource.Weizen, 	Resource.Schaf , 	Resource.Wasser},
			{Resource.Wasser, Resource.Lehm, 	Resource.Holz, 		Resource.Weizen, 	Resource.Holz, 		Resource.Wasser, 	Resource.Wasser},
			{Resource.Wasser, Resource.Weizen, 	Resource.Holz, 		Resource.Schaf, 	Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser},
			{Resource.Wasser, Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser, 	Resource.Wasser}
	};
	
}
