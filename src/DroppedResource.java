import com.badlogic.gdx.graphics.Color;


public class DroppedResource extends Particle {
	
	public int resourceType = 0;
	public int size = 10;
	public int value = 5;
	
	public static final int STONE = 0;
	public static final int COPPER = 1;
	public static final int COBALT = 2;
	public static final int GOLD = 3;
	public static final int PLATINUM = 4;
	public static final int DIAMOND = 5;


	public DroppedResource(World w) {
		super(w);
		this.color = Color.WHITE;
		this.expire = 100000;
		this.xScale = 4;
		this.yScale = 4;
		this.width = 5;
		this.height = 5;
		this.tex = 22;
		this.z = 1;
	}
	
	public static DroppedResource getResource(int id,World w){
		if(id == STONE)return new DroppedResource(w);
		if(id == COPPER)return new CopperDrop(w);
		if(id == COBALT)return new CobaltDrop(w);
		if(id == GOLD)return new GoldDrop(w);
		if(id == PLATINUM)return new PlatinumDrop(w);
		if(id == DIAMOND)return new DiamondDrop(w);
		
		return null;
	}
	
	public void tick(){
		if(w.player.storage+this.size  <= w.player.maxstorage 
				&& (Math.abs(w.player.x+w.player.width*w.player.xScale/2-x) < 60
				 && Math.abs(w.player.y+w.player.height*w.player.yScale/2-y) < 60
						)){
			w.player.giveResource(this);
			Resources.pickup.play();
			this.remove = true;
		}
		super.tick();
	}

}
