import com.badlogic.gdx.graphics.Color;


public class DrillUpgrade extends Upgrade {
	public Miner m;
	public int level;
	
	public DrillUpgrade(int level,Miner m){
		this.m = m;
		this.level = level;
		String[] levels = new String[]{
				"Stone",
				"Steel",
				"Platinum",
				"Diamond"
		};
		int[] types = new int[]{
				Upgrade.DRILL_STONE,
				Upgrade.DRILL_STEEL,
				Upgrade.DRILL_PLATINUM,
				Upgrade.DRILL_DIAMOND
		};
		String[] descs = new String[]{
				"This is a basic drill.",
				"Mine harder rocks with greater speed.",
				"Get further down in the mines with this!",
				"UNOBTANIUM HERE WE COME",
		};
		int[] costs = new int[]{
			0,
			8000,
			14000,
			20000
		};
		
		this.title = levels[level]+" Drill";
		this.desc = descs[level];
		this.cost = costs[level];
		this.type = types[level];
	}
	
	public void init(){
		m.drillLevel = level;
	}
	
	public void queueRender(Display display){
		Color[] colors = new Color[]{
				new Color(0.6f,0.6f,0.6f,1),
				new Color(1,1,1,1),
				new Color(0.85f,1,0.9f,1),
				new Color(0.68f,1,1,1),
		};
		Renderable drill = new Renderable();
		drill.tex = 26;
		drill.xScale = 8;
		drill.yScale = 8;
		drill.width = 4;
		drill.height= 5;
		drill.color = colors[level];
		drill.x = m.x+ 112;
		drill.y = m.y+24;

		if(m.yScale == 8.2f){
			drill.y = m.y+25;
			drill.yScale = 8.2f;
		}
		drill.z = 10;
		drill.applyCam = true;
		
		
		if(m.tex > 16){
			drill.tex = 27;
			drill.width = 6;
			drill.height= 11;
			drill.x = m.x+ 56;
			drill.y = m.y+ 8;
		}else if(m.tex > 14){
			drill.tex = 28;
			drill.width = 11;
			drill.height= 6;
			drill.x = m.x+ 9;
			drill.y = m.y;
			if(m.mirrorY){
				drill.mirrorY = true;
				drill.y = m.y + 58;
			}
		}
		
		if(m.mirrorX){
			drill.mirrorX = true;
			drill.x = m.x;
		}
		drill.queueRender(display);
	}
}
