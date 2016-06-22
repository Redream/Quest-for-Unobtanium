


public class Vendor extends Tile {
	public int timer = 3;
	public boolean selling = false;
	
	public Vendor(World w){
		super(w);
		
		this.tex = 7;
		this.xScale = 8;
		this.yScale = 8;
		this.width = 29;
		this.height = 25;
		this.collides = false;
	}
	
	public void tick(){
		if(!selling && this.w.player.getBounds().overlaps(this.getBounds())){
			w.showTooltip("SPACEBAR to sell your rocks");
			if(w.player.space != 0 && !w.player.resources.isEmpty()){
				selling = true;
			}
		}
		if(selling){
			w.showTooltip("Selling rocks..");
			timer--;
			if(timer == 0){
				timer = (int) (6-Math.floor(w.player.maxstorage/2000));
				DroppedResource dr = w.player.resources.get(0);
				w.player.resources.remove(0);
				w.player.resourceCount[dr.resourceType]--;
				w.player.storage -= dr.size;
				w.player.gainCash(dr.value);
				if(w.player.resources.isEmpty()){
					selling = false;
				}
			}
		}
	}
	public void queueRender(Display display){
		if(Game.state != Game.STATE_UPGRADE  && Game.state != Game.STATE_TITLE){
			super.queueRender(display);
		}
	}
}
