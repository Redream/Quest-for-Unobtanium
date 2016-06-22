import java.util.Random;



public class Diamond extends Rock {
	public int difficulty = 2;
	
	public Diamond(World w){
		super(w);
		this.tex = 12;
		this.mineTime = 40;
		this.type = Rock.DIAMOND;
	}
	
	public DroppedResource getDrop(){
		return new DiamondDrop(w);
	}
	
	public int getDropCount(){
		return new Random().nextInt(4)+1;
	}
}
