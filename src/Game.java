import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Game implements ApplicationListener {

	public static int WIDTH;
	public static int HEIGHT;
	
	public static float DIAGDIST;
	
	public static float screenRatioY;
	public static float screenRatioX;
	public static boolean debug = false;
	
	private SpriteBatch batch;
	private Display display;
	private Input input;
	
	private World world;
	
	private double unprocessed;
	public static final double TICK_TIME = 0.0166667;
	
	public static int state = 0;
	public static boolean finished = false;
	public static boolean doReset = false;
	
	public final static int STATE_TITLE = 0;
	public final static int STATE_INTRO = 1;
	public final static int STATE_SURFACE = 2;
	public final static int STATE_MINETRANSITION = 3;
	public final static int STATE_SURFACETRANSITION = 4;
	public final static int STATE_UPGRADE = 5;
	public final static int STATE_MINEINTRO = 6;
	public final static int STATE_MINE = 7;
	public final static int STATE_OUTOFFUEL = 8;
	public final static int STATE_OVERHEAT = 9;
	public final static int STATE_ENDGAME = 10;
	
	public void create() {
		Camera.cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		Camera.HUDcam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		
		this.batch = new SpriteBatch();
		this.display = new Display(this.batch);
		
		this.input = new Input();
		Gdx.input.setInputProcessor(input);
		Gdx.graphics.setVSync(true);
		world = new World();
	}

	public void dispose() {
	}

	public void pause() {
	}

	public void render() {
		if (this.unprocessed < 3) {
			this.unprocessed += Game.timeDelta();
		}

		if (this.unprocessed > 1) {
			this.tick();
			this.unprocessed -= 1;
		}
		
		this.batch.begin();

		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if(Game.state == Game.STATE_MINE || Game.state == Game.STATE_MINEINTRO || Game.state == Game.STATE_SURFACETRANSITION || Game.state == Game.STATE_OUTOFFUEL || Game.state == Game.STATE_OVERHEAT || Game.state == Game.STATE_ENDGAME){
			Gdx.gl10.glClearColor(0.14f,0.14f,0.14f,1);
		}else{
			Gdx.gl10.glClearColor(0.5882f,0.7882f,1,1);
		}
		world.queueRender(display);
		if(Game.state == Game.STATE_TITLE){
			Renderable logo = new Renderable();
			logo.tex = 40;
			logo.width = 164;
			logo.height = 26;
			logo.xScale = 8;
			logo.yScale = 8;
			logo.x = 60;
			logo.y = Game.HEIGHT - 830;
			logo.queueRender(display);
			Camera.cam.zoom = 0.4f;
			Camera.cam.position.y = -320;
			Font f = new Font("Press SPACEBAR to begin", Game.WIDTH/2, 40, Font.POS_CENTER, new Color(0.416f,0.416f,0.416f,1), 1.5f, false);
			f.queueRender(display);
			if(Game.finished){
				world.player.rainbowSmoke = true;
				Font f2 = new Font("Thanks for playing!", Game.WIDTH/2, Game.HEIGHT-200, Font.POS_CENTER, Color.WHITE, 1.5f, false);
				f2.queueRender(display);
			}
			Font f3 = new Font("Made in 48 hours for Ludum Dare 29", 20, Game.HEIGHT-50, Font.POS_LEFT, Color.WHITE, 1.5f, false);
			f3.queueRender(display);
			Camera.scrollspeed = 0.01f;
		}
	
		this.display.render();
		this.display.renderQueue.clear();
		this.display.renderQueueHUD.clear();

		this.batch.end();
	}

	public static double timeDelta() {
		return Gdx.graphics.getDeltaTime() / TICK_TIME;
	}

	private void tick() {
		if(Game.doReset){
			this.world = new World();
			Game.doReset = false;
		}
		Camera.tick();
		world.tick();
		if(Game.state == Game.STATE_TITLE && Gdx.input.isKeyPressed(Keys.SPACE)){
			Game.state = Game.STATE_INTRO;
			Camera.reset();
			Camera.updatePos();
			world.player.rainbowSmoke = false;
		}
	}

	public void resize(int width, int height) {
		float dratio = (float)width/(float)height;

		Game.WIDTH = (int) (960 * dratio);
		Game.HEIGHT = 960;
		
		Game.screenRatioX = (float)Game.WIDTH / (float)width;
		Game.screenRatioY = (float)Game.HEIGHT / (float)height;
		
		Gdx.graphics.setDisplayMode(width, height, false);
		
		DIAGDIST = (float) Math.abs(Math.sqrt(Math.pow(-Game.WIDTH, 2.0D) + Math.pow(Game.HEIGHT, 2.0D)));
		
		Camera.cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		Camera.HUDcam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
	}

	public void resume() {
	}

}
