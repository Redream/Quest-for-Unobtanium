import java.util.Random;

import com.badlogic.gdx.graphics.Color;


public class Rock extends Tile {
	public float mineTime = 10;
	public int difficulty = 0;
	public float mined = 0;
	
	public int type = STONE;
	
	public static final int STONE = 0;
	public static final int COPPER = 1;
	public static final int COBALT = 2;
	public static final int GOLD = 3;
	public static final int PLATINUM = 4;
	public static final int DIAMOND = 5;
	
	public Rock(World w){
		super(w);
		this.applyCam = true;
		this.tex = 11;
		this.xScale = 8;
		this.yScale = 8;
		this.width = 16;
		this.height = 16;
	}
	
	public void tick(){
		if(mined > 0)mined--;
		if(mined > mineTime){
			this.remove = true;
			Resources.mine.play();
			for(int i=0;i<this.getDropCount();i++){
				DroppedResource dr = this.getDrop();
				Random r = new Random();
				dr.x = x+r.nextFloat()*100+14;
				dr.y = y+r.nextFloat()*100+14; 
				w.addEntity(dr);
			}
		}
	}
	
	public DroppedResource getDrop(){
		return new DroppedResource(w);
	}
	
	public int getDropCount(){
		return 3;
	}
	
	public void queueRender(Display display){
		if(mined > 0){
			Renderable o = new Renderable();
			o.tex = 19;
			o.xScale = 8;
			o.yScale = 8;
			o.color = new Color(1,1,1,mined/mineTime);
			o.applyCam = true;
			o.x = this.x;
			o.y = this.y;
			o.z = 1000;
			o.queueRender(display);
		}
		super.queueRender(display);
	}
	
	public void mine(float speed){
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
