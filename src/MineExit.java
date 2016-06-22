
public class MineExit extends Tile {
	public MineExit(World w){
		super(w);
		this.tex = 24;
		this.width = 32;
		this.height = 23;
		this.xScale=8;
		this.yScale=8;
		this.collides = false;
	}
	
	public void tick(){
		if(Game.state == Game.STATE_MINE && w.player.y > -1150){
			w.showTooltip("SPACEBAR to return to the surface");
			if(w.player.space != 0){
				Game.state = Game.STATE_SURFACETRANSITION;
				w.transitionFade = 0;
			}
		}
	}
}
