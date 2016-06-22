
public class ArmorUpgrade extends Upgrade{
	public Miner m;
	public int level;
	
	public ArmorUpgrade(int level,Miner m){
		this.m = m;
		this.level = level;
		
		String[] levels = new String[]{
				"Basic Armor",
				"Stronger Armor",
				"Platinum Armor",
				"Titanium Armor"
		};
		int[] types = new int[]{
				Upgrade.ARMOR_BASIC,
				Upgrade.ARMOR_MEDIUM,
				Upgrade.ARMOR_LARGE,
				Upgrade.ARMOR_ULTRA
		};
		String[] descs = new String[]{
				"Protects from some heat.",
				"Allows deeper travel.",
				"High heat resistance.",
				"Protects you from very high temperatures",
		};
		
		int[] costs = new int[]{
			0,
			4000,
			8000,
			16000
		};
		
		this.title = levels[level];
		this.desc = descs[level];
		this.cost = costs[level];
		this.type = types[level];
	}
	
	public void init(){
		float[] tempresist = new float[]{
			1,
			1.5f,
			2f,
			4f,
		};
		m.tempresist = tempresist[level];
	}
}
