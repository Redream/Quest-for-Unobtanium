import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Resources {
	public static TextureAtlas atlas = new TextureAtlas(file("pack"));

	public static boolean[] flipX = new boolean[500];
	public static boolean[] flipY = new boolean[500];

	public static AtlasRegion[] regions = new AtlasRegion[500];

	public static BitmapFont font = new BitmapFont(file("volter.fnt"),file("volter.png"),false);
	public static Sound pickup = Gdx.audio.newSound(file("pickup.wav"));
	public static Sound mine = Gdx.audio.newSound(file("break.wav"));
	public static Sound upgrade = Gdx.audio.newSound(file("upgrade.wav"));
	public static Sound guzzle = Gdx.audio.newSound(file("guzzle.wav"));
	
	public static FileHandle file(String src) {
		return Gdx.files.internal(src);
	}

}
