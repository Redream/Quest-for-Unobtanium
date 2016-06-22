
public class EngineUpgrade extends Upgrade{
	public Miner m;
	public int level;
	
	public EngineUpgrade(int level,Miner m){
		this.m = m;
		this.level = level;
		String[] levels = new String[]{
				"Basic Engine",
				"VroomStar2000",
				"BigVroom9000",
				"UltraVroom X"
		};
		int[] types = new int[]{
				Upgrade.ENGINE_BASIC,
				Upgrade.ENGINE_MEDIUM,
				Upgrade.ENGINE_LARGE,
				Upgrade.ENGINE_ULTRA
		};
		String[] descs = new String[]{
				"Gets you from A to B.",
				"Now you can BOOST with SHIFT!",
				"Go faster, boost more.",
				"Breaking the sound barrier.",
		};
		int[] costs = new int[]{
			0,
			2500,
			12000,
			22000
		};
		
		this.title = levels[level];
		this.desc = descs[level];
		this.cost = costs[level];
		this.type = types[level];
	}
	
	public void init(){
		float[] speeds = new float[]{
			1.4f,
			1.4f,
			1.8f,
			2.2f
		};
		m.speed = speeds[level];
		if(level > 0)m.canboost = true;
	}
}
