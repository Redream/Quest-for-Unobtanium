import java.util.Random;



public class Platinum extends Rock {	
	public Platinum(World w){
		super(w);
		this.tex = 35;
		this.mineTime = 80;
		this.type = Rock.PLATINUM;
	}
	
	public DroppedResource getDrop(){
		return new PlatinumDrop(w);
	}
	
	public int getDropCount(){
		return new Random().nextInt(15)+8;
	}
}
