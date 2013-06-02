import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.vSyncEnabled = false;
		config.title = "LibGDX Base";
		config.useGL20 = false;
		config.height = 320;
		config.width = 480;
		new LwjglApplication(new Game(), config);
	}
}