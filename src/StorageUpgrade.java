
public class StorageUpgrade extends Upgrade{
	public Miner m;
	public int level;
	
	public StorageUpgrade(int level,Miner m){
		this.m = m;
		this.level = level;
		
		String[] levels = new String[]{
				"Basic Storage",
				"Spacious Storage",
				"MuchStorage Unit",
				"Luxurious Storage"
		};
		int[] types = new int[]{
				Upgrade.STORAGE_BASIC,
				Upgrade.STORAGE_MEDIUM,
				Upgrade.STORAGE_LARGE,
				Upgrade.STORAGE_ULTRA
		};
		String[] descs = new String[]{
				"Stores rocks.",
				"Now store even more useless rocks!",
				"Wow, such rocks! Very storage.",
				"Okay, this is getting out of hand.",
		};
		
		int[] costs = new int[]{
			0,
			2000,
			8000,
			15000
		};
		
		this.title = levels[level];
		this.desc = descs[level];
		this.cost = costs[level];
		this.type = types[level];
	}
	
	public void init(){
		float[] storage = new float[]{
			2000,
			3000,
			4000,
			6000
		};
		m.maxstorage = storage[level];
	}
}
