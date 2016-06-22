import java.util.Random;

import com.badlogic.gdx.graphics.Color;


public class Particle extends Entity {
	public int expire = 400;
	
	public Particle(World w) {
		super(w);
		this.width = 1;
		this.height = 1;
		this.xScale = 6;
		this.yScale = 6;
		this.tex = 3;
		this.gravity = 0;
		this.applyCam = true;
		this.z =11;
		float col = new Random().nextFloat()*0.5f+0.5f;
		this.color = new Color(col,col,col,new Random().nextFloat()*0.6f);
	}
	
	public void tick(){
		this.expire--;
		if(this.expire<0)this.remove = true;
		super.tick();
	}

}
