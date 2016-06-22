import com.badlogic.gdx.graphics.Color;


public class PaintUpgrade extends Upgrade{
	public Miner m;
	public int level;
	
	public PaintUpgrade(int level,Miner m){
		this.m = m;
		this.level = level;
		
		String[] levels = new String[]{
				"Basic Paint",
				"Red Paint",
				"Rainbow Smoke"
		};
		int[] types = new int[]{
				Upgrade.PAINT_BASIC,
				Upgrade.PAINT_RED,
				Upgrade.PAINT_SMOKE
		};
		String[] descs = new String[]{
				"Over 50 shades of grey included.",
				"Makes it go faster!",
				"No unicorns are harmed.",
		};
		
		int[] costs = new int[]{
			0,
			1400,
			9999
		};
		
		this.title = levels[level];
		this.desc = descs[level];
		this.cost = costs[level];
		this.type = types[level];
	}
	
	public void init(){
		Color[] color = new Color[]{
			Color.WHITE,
			new Color(1,0.4f,0.4f,1),
		};
		if(level < 2){
			m.color = color[level];
		}else{
			m.rainbowSmoke = true;
		}
	}
}
