import java.util.Random;



public class Cobalt extends Rock {
	public Cobalt(World w){
		super(w);
		this.tex = 31;
		this.mineTime = 50;
		this.type = Rock.COBALT;
	}
	
	public DroppedResource getDrop(){
		return new CobaltDrop(w);
	}
	
	public int getDropCount(){
		return new Random().nextInt(10)+20;
	}
}
