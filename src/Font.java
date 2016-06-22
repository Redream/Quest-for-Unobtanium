
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Font extends Renderable{
	private String txt;
	private float scale = 1f;
	
	public static int POS_LEFT = 0;
	public static int POS_RIGHT = 1;
	public static int POS_CENTER = 2;
	
	private Color color;
	private int pos = 0;
	
	public Font(String txt,float x,float y,int pos,Color color, float textScale,boolean applycam){
		this.txt = txt;
		this.x = x;
		this.y = y;
		this.pos = pos;
		this.color = color;
		this.applyCam = applycam;
		this.scale = textScale;
	}
	
	public void render(SpriteBatch batch){
		float x = this.x - Game.WIDTH / 2;
		float y = this.y - Game.HEIGHT / 2 + 29;
	
		Resources.font.setScale(scale);
		Resources.font.setColor(color);
		
		if(pos == POS_RIGHT) x -= Resources.font.getBounds(txt).width;
		if(pos == POS_CENTER) x -= Resources.font.getBounds(txt).width/2;
		
		Resources.font.draw(batch, txt, x, y);
	}
}
