
public class FuelUpgrade extends Upgrade{
	public Miner m;
	public int level;
	
	public FuelUpgrade(int level,Miner m){
		this.m = m;
		this.level = level;
		String[] levels = new String[]{
				"Basic Fuel",
				"Medium Tank",
				"Large Tank",
				"ULTRA TANK"
		};
		int[] types = new int[]{
				Upgrade.FUEL_BASIC,
				Upgrade.FUEL_MEDIUM,
				Upgrade.FUEL_LARGE,
				Upgrade.FUEL_ULTRA
		};
		String[] descs = new String[]{
				"Holds fuel. Does the job well.",
				"Larger capacity + increased efficiency.",
				"Big capacity tank.",
				"HUGE capacity! You'll go far.",
		};
		int[] costs = new int[]{
			0,
			2500,
			12500,
			20000
		};
		
		this.title = levels[level];
		this.desc = descs[level];
		this.cost = costs[level];
		this.type = types[level];
	}
	
	public void init(){
		int[] fuelsize = new int[]{
			1000,
			1500,
			2000,
			4000
		};
		m.maxfuel = fuelsize[level];
	}
}
