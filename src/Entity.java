
public class Entity extends Renderable {	
	public float vx;
	public float vy;
	
	public boolean collides = false;
	
	public float gravity = 0.1f;
	
	public World w;
	public boolean remove;
	
	public Entity(World w){
		this.w = w;
	}
	
	public void tick(){
		this.vy -= gravity;
		this.move();
	}
	
	public void move(){
		this.move2(vx, 0);
		this.move2(0, vy);
	}
	
	public void move2(float x, float y){
		if(this.x+x<0)return;
		this.x += x;
		this.y += y;
		if(this.collides){
			for(Tile t : w.tiles){
				if(!t.collides)continue;
				if(this.getBounds().overlaps(t.getBounds())){
					this.x -= x;
					this.y -= y;
					if(x == 0)this.vy = 0;					
				}
			}
			for(int i=0;i<w.rocks.length;i++){
				if(w.rocks[i] != null){
					Rock t = w.rocks[i];
					if(!t.collides)continue;
					if(this.getBounds().overlaps(t.getBounds())){
						this.x -= x;
						this.y -= y;
						if(x == 0)this.vy = 0;					
					}
				}
			}
		}
	}
}
