import java.util.Random;


public class HardRock extends Rock {

	public HardRock(World w,int difficulty) {
		super(w);
		this.tex = 25;
		this.difficulty = difficulty;
	}
	
	public void mine(float speed){
		if(w.player.drillLevel < this.difficulty){
			if(this.difficulty == 1)w.showTooltip("You need a STEEL DRILL to mine this.");
			if(this.difficulty == 2)w.showTooltip("You need a PLATINUM DRILL to mine this.");
			if(this.difficulty == 3)w.showTooltip("You need a DIAMOND DRILL to mine this.");
			
			return;
		}
		this.mined += speed;
		Random r = new Random();
		if(r.nextInt(4) == 1){
			Particle p = new Particle(w);
			p.x = x+r.nextFloat()*100+14;
			p.y = y+r.nextFloat()*100+14;
			p.xScale = r.nextInt(15)+3;
			p.yScale = p.xScale;
			p.expire = 150;
			w.addEntity(p);
		}
	}
	
	

}
