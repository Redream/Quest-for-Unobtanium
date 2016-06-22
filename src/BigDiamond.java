import java.util.Random;

import com.badlogic.gdx.graphics.Color;


public class BigDiamond extends Tile {
	public boolean shattered = false;
	public float shatterscale = 1;
	public BigDiamond(World w) {
		super(w);
		this.tex = 29;
		this.xScale = 8;
		this.yScale = 8;
		this.width = 75;
		this.height = 43;
	}
	
	public void tick(){
		if(new Random().nextInt(20) == 0){
			float angle = (float) Math.toRadians(new Random().nextGaussian() * 45f + 90);
			float x = (float) (280 * Math.cos(angle) + this.x + this.width*this.xScale/2 - 40);
			float y = (float) (180 * Math.sin(angle) + this.y + this.height*this.yScale/2);
			TextParticle t = new TextParticle(w, "*sparkle*",true,0.75f);
			t.x = x;
			t.y = y;
			t.expire = 150;
			t.vy = 0.1f;
			w.addEntity(t);
		}
	}
	
	public void shatter(){
		this.shattered = true;
	}
	
	public void queueRender(Display display){
		if(shattered && (15-(shatterscale-0.5f))/15 > 0){
			Renderable shard = new Renderable();
			shard.tex = 39;
			shard.height = 45;
			shard.width = 71;
			shard.xScale = shatterscale;
			shard.yScale = shatterscale;
			shatterscale += 0.04f;
			shard.x = this.x + this.width*this.xScale/2 - shard.width*shard.xScale/2;
			shard.y = this.y;
			shard.color = new Color(1,1,1,Math.max((15-(shatterscale-1))/15, 0));
			shard.applyCam =true;
			shard.queueRender(display);
			this.color = new Color(1,1,1,Math.max((15-(shatterscale-0.5f))/15, 0));
		}
		super.queueRender(display);
	}
}
