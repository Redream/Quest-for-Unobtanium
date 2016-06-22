import java.util.Random;



public class Copper extends Rock {
	public int difficulty = 2;
	
	public Copper(World w){
		super(w);
		this.tex = 13;
		this.mineTime = 40;
		this.type = Rock.COPPER;
	}
	
	public DroppedResource getDrop(){
		return new CopperDrop(w);
	}
	
	public int getDropCount(){
		return new Random().nextInt(10)+1;
	}
}
