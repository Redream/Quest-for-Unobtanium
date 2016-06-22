

public class PetrolStation extends Tile {
	public int timer = 20;
	
	public PetrolStation(World w){
		super(w);
		
		this.tex = 8;
		this.width = 25;
		this.height = 30;
		this.xScale = 8;
		this.yScale = 8;
		this.collides = false;
	}
	
	public void tick(){
		if(this.w.player.getBounds().overlaps(this.getBounds())){
			w.showTooltip("SPACEBAR to refuel");
			int amt = (int) Math.round(Math.min(w.player.maxfuel-w.player.fuel, 40));
			if(w.player.space != 0 && w.player.fuel < w.player.maxfuel && w.player.money>=amt){
				timer--;
				if(timer == 0){
					
					w.player.fuel+= amt;
					w.player.spendCash(amt);
					timer = 10;
					Resources.guzzle.play();
					if(w.player.fuel+1 >= w.player.maxfuel)Resources.upgrade.play();
				}
			}
			if(w.player.fuel+1 >= w.player.maxfuel){
				w.player.fuel = w.player.maxfuel;
				w.showTooltip("You're fueled up and ready to go!");
			}
		}
	}
	public void queueRender(Display display){
		if(Game.state != Game.STATE_UPGRADE  && Game.state != Game.STATE_TITLE){
			super.queueRender(display);
		}
	}
}
