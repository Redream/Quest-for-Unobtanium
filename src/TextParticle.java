import com.badlogic.gdx.graphics.Color;


public class TextParticle extends Particle {
	public String txt;
	public float scale = 3f;
	public TextParticle(World w, String txt,boolean applycam, float scale) {
		super(w);
		this.txt = txt;
		this.scale = scale;
		this.expire = 60;
		this.vy = 0.6f;
		this.z = 5000;
		this.applyCam = applycam;
	}
	
	public void queueRender(Display display){
		Font f = new Font(txt, x, y, Font.POS_LEFT, new Color(1,1,1,((float)expire/60)), scale, this.applyCam);
		f.queueRender(display);
	}
}
