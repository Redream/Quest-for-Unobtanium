import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.vSyncEnabled = false;
		config.title = "Quest for Unobtanium";
		config.useGL20 = false;
		config.height = 640;
		config.width = 960;
		new LwjglApplication(new Game(), config);
	}
}