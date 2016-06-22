import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public class Cloud extends Tile {
	
	public float parallax = 4;
	
	public Cloud(World w){
		super(w);
		this.tex = 38;
		this.xScale = 8;
		this.yScale = 8;
		this.width = 44;
		this.height = 7;
		this.applyCam = true;
		this.collides = false;
	}
	
	public void render(SpriteBatch batch) {
		float dx = x*(Camera.cam.position.x/(1000*parallax)+1) - Game.WIDTH / 2;
		float dy = y - Game.HEIGHT / 2;

		AtlasRegion ar = getTexture();
		if(width == 0 || height == 0){
			width = ar.getRegionWidth();
			height = ar.getRegionHeight();
		}
		batch.setColor(color);
		batch.draw(ar, dx, dy, 0, 0, width, height, xScale, yScale, 0);
	}
}
