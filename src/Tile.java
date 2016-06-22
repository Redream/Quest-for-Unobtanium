
public class Tile extends Renderable {
	public boolean collides = true;
	public boolean remove;
	public World w;
	
	public Tile(World w){
		this.w = w;
		this.applyCam = true;
		this.tex = 1;
		this.xScale = 8;
		this.yScale = 8;
		this.width = 16;
		this.height = 16;
	}
}
