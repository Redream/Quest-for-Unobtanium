import java.util.Random;



public class Gold extends Rock {
	public int difficulty = 2;
	
	public Gold(World w){
		super(w);
		this.tex = 33;
		this.mineTime = 60;
		this.type = Rock.GOLD;
	}
	
	public DroppedResource getDrop(){
		return new GoldDrop(w);
	}
	
	public int getDropCount(){
		return new Random().nextInt(8)+3;
	}
}
