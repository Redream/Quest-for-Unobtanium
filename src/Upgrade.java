
public class Upgrade extends Renderable{
	public final static int DRILL_STONE = 1;
	public final static int DRILL_STEEL = 2;
	public final static int DRILL_PLATINUM = 3;
	public final static int DRILL_DIAMOND = 4;
	
	public final static int FUEL_BASIC = 5;
	public final static int FUEL_MEDIUM = 6;
	public final static int FUEL_LARGE = 7;
	public final static int FUEL_ULTRA = 8;
	
	public final static int ENGINE_BASIC = 9;
	public final static int ENGINE_MEDIUM = 10;
	public final static int ENGINE_LARGE = 11;
	public final static int ENGINE_ULTRA = 12;
	
	public final static int STORAGE_BASIC = 13;
	public final static int STORAGE_MEDIUM = 14;
	public final static int STORAGE_LARGE = 15;
	public final static int STORAGE_ULTRA = 16;
	
	public final static int PAINT_BASIC = 17;
	public final static int PAINT_RED = 18;
	public final static int PAINT_BLUE = 19;
	public final static int PAINT_SMOKE = 20;
	
	public final static int ARMOR_BASIC = 21;
	public final static int ARMOR_MEDIUM = 22;
	public final static int ARMOR_LARGE = 23;
	public final static int ARMOR_ULTRA = 24;
	
	
	public int type = 0;
	public int cost = 100;
	public String title = "Upgrade title";
	public String desc = "Default description!";
	
	public void queueRender(Display display){
		
	}
	
	public void init(){
	}
}
