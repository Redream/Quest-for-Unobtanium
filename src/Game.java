import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Game implements ApplicationListener {

	public static int WIDTH;
	public static int HEIGHT;
	
	public static float DIAGDIST;
	
	public static float screenRatioY;
	public static float screenRatioX;
	public static boolean debug;
	
	private SpriteBatch batch;
	private Display display;
	private Input input;
	
	private double unprocessed;
	public static final double TICK_TIME = 0.0166667;

	public void create() {
		Camera.cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		Camera.HUDcam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
		
		this.batch = new SpriteBatch();
		this.display = new Display(this.batch);
		
		this.input = new Input();
		Gdx.input.setInputProcessor(input);
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
		Gdx.gl10.glClearColor(0,0,0,1);
		
		this.display.render();
		this.display.renderQueue.clear();
		this.display.renderQueueHUD.clear();

		this.batch.end();
	}

	public static double timeDelta() {
		return Gdx.graphics.getDeltaTime() / TICK_TIME;
	}

	private void tick() {
	}

	public void resize(int width, int height) {
		float dratio = (float)width/(float)height;

		Game.WIDTH = (int) (480 * dratio);
		Game.HEIGHT = 480;
		
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
