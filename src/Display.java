import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public class Display {
	public boolean applyCam = true;
	private final SpriteBatch batch;

	public boolean debug = false;

	private final Comparator<Renderable> zSort = new Comparator<Renderable>() {
		public int compare(Renderable p1, Renderable p2) {
			return (p1.z > p2.z ? 1 : (p1.z == p2.z ? 0 : -1));
		}
	};
	public List<Renderable> renderQueue = new ArrayList<Renderable>();
	public List<Renderable> renderQueueHUD = new ArrayList<Renderable>();

	private double culldist;

	public Display(SpriteBatch batch) {
		this.batch = batch;
	}

	public void render() {
		culldist = (Game.DIAGDIST*Camera.cam.zoom)/2;

		Collections.sort(this.renderQueue, this.zSort);

		this.batch.setProjectionMatrix(Camera.cam.combined);
		this.renderList(this.renderQueue);

		this.batch.setProjectionMatrix(Camera.HUDcam.combined);
		this.renderList(this.renderQueueHUD);
	}

	private void renderList(List<Renderable> renderQueue) {
		int rs = renderQueue.size();
		for (int i = 0; i < rs; i++) {
			this.renderIndividual(renderQueue.get(i));
		}
	}

	private void renderIndividual(Renderable r) {
		float x = r.x - Game.WIDTH / 2;
		float y = r.y - Game.HEIGHT / 2;

		AtlasRegion ar = r.getTexture();

		if(r.width == 0 || r.height == 0){
			r.width = ar.getRegionWidth();
			r.height = ar.getRegionHeight();
		}

		this.batch.draw(ar, x, y, 0, 0, r.width, r.height, r.xScale, r.yScale, 0);
	}

	public void queueRender(Renderable r) {
		if (r.applyCam && this.applyCam) {
			float xd = (Camera.cam.position.x+Game.WIDTH/2) - r.x;
			float yd = (Camera.cam.position.y+Game.HEIGHT/2) - r.y;
			double distance = Math.abs(Math.sqrt(xd*xd+yd*yd)) - Math.max(r.width*r.xScale,r.height*r.yScale);
			distance *= 0.8;
			if (distance > culldist)
				return;

			this.renderQueue.add(r);
		} else {
			this.renderQueueHUD.add(r);
		}

	}

}