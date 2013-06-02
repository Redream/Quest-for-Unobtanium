import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class Camera {
	public static boolean disableCollisionX = false;
	public static boolean disableCollisionY = false;
	public static int BoundaryX;
	public static int BoundaryY;
	public static int BoundaryStartX;
	public static int BoundaryStartY;

	public static float angle = 0;

	public static boolean disable = false;

	public static OrthographicCamera cam;
	public static OrthographicCamera HUDcam;

	public static void tick() {
		if(!disable){
			if (Gdx.input.isKeyPressed(Keys.NUM_9)) {
				cam.zoom -= 0.01;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
				cam.zoom += 0.01;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_0)) {
				cam.zoom = 1;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_6)) {
				cam.rotate(-0.5f, 0, 0, 1);
				Camera.angle += 0.5;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
				cam.rotate(0.5f, 0, 0, 1);
				Camera.angle -= 0.5;
			}

			if (Gdx.input.isKeyPressed(Keys.NUM_7)) {
				Camera.reset();
//				Camera.updatePos();
				Camera.angle = 0;
			}

//			final float moveX = (float) (((target.x + (target.width*target.xScale)/2 - Game.WIDTH / 2) - cam.position.x) * 0.1);
//			final float moveY = (float) (((target.y + (target.height*target.yScale)/2 - Game.HEIGHT / 2) - cam.position.y) * 0.1);
//			cam.translate(moveX, moveY, 0);
		}

		if(!Camera.disableCollisionY){
			if (cam.position.y - (Game.HEIGHT / 2 * (cam.zoom - 1)) < Camera.BoundaryStartY) {
				cam.position.y = Camera.BoundaryStartY + (Game.HEIGHT / 2 * (cam.zoom - 1));
			}
			if (cam.position.y + Game.HEIGHT > Camera.BoundaryY) {
				cam.position.y = Camera.BoundaryY - Game.HEIGHT;
			}
		}
		if(!Camera.disableCollisionX){
			if (cam.position.x - (Game.WIDTH / 2 * (cam.zoom - 1)) < Camera.BoundaryStartX) {
				cam.position.x = Camera.BoundaryStartX + (Game.WIDTH / 2 * (cam.zoom - 1));
			}
			if (cam.position.x + Game.WIDTH > Camera.BoundaryX) {
				cam.position.x = Camera.BoundaryX - Game.WIDTH;
			}
		}

		cam.update();
	}
	
//	public static void setTarget(Renderable target){
//		Camera.target = target;
//	}

//	public static void updatePos() {
//		if(lm.getActiveLevel() != null){
//			final float moveX = (target.x + 13 - Game.WIDTH / 2) - cam.position.x;
//			final float moveY = (target.y + 35 - Game.HEIGHT / 2) - cam.position.y;
//			cam.translate(moveX, moveY, 0);
//		}
//	}

	public static void reset() {
		Camera.cam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
//		Camera.ParallaxCam = new OrthographicCamera(Game.WIDTH,Game.HEIGHT);
	}
}

