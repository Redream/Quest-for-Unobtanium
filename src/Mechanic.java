import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;


public class Mechanic extends Tile {
	public boolean upgrading = false;
	public int catHover = 0;
	public int catSelect = 0;
	public int itemHover = 0;
	
	public Upgrade[][] items = new Upgrade[][]{
		new Upgrade[]{
			new DrillUpgrade(0,w.player),
			new DrillUpgrade(1,w.player),
			new DrillUpgrade(2,w.player),
			new DrillUpgrade(3,w.player),
		},
		new Upgrade[]{
			new FuelUpgrade(0,w.player),
			new FuelUpgrade(1,w.player),
			new FuelUpgrade(2,w.player),
			new FuelUpgrade(3,w.player),
		},
		new Upgrade[]{
			new EngineUpgrade(0,w.player),
			new EngineUpgrade(1,w.player),
			new EngineUpgrade(2,w.player),
			new EngineUpgrade(3,w.player),
		},
		new Upgrade[]{
			new StorageUpgrade(0,w.player),
			new StorageUpgrade(1,w.player),
			new StorageUpgrade(2,w.player),
			new StorageUpgrade(3,w.player),
		},
		new Upgrade[]{
			new ArmorUpgrade(0,w.player),
			new ArmorUpgrade(1,w.player),
			new ArmorUpgrade(2,w.player),
			new ArmorUpgrade(3,w.player),
		},
		new Upgrade[]{
			new PaintUpgrade(0,w.player),
			new PaintUpgrade(1,w.player),
			new PaintUpgrade(2,w.player),
		},
	};
	
	public String[] cats = new String[]{
		"Drill",
		"Fuel",
		"Engine",
		"Storage",
		"Armor",
		"Paint"
	};
	private boolean exitHover;
	
	public Mechanic(World w){
		super(w);
		this.tex = 9;
		this.width = 25;
		this.height = 30;
		this.xScale = 8;
		this.yScale = 8;
		this.collides = false;
		Input.registerListener(this);
	}
	
	public void tick(){
		if(this.w.player.getBounds().overlaps(this.getBounds()) && Game.state != Game.STATE_UPGRADE){
			w.showTooltip("SPACEBAR to upgrade the MINETRON2000");
			if(w.player.space != 0){
				upgrading = true;
				
				Camera.reset();
				Camera.cam.zoom = 0.4f;
				Camera.updatePos();
				w.player.vx=w.player.vy=0;
				Game.state = Game.STATE_UPGRADE;
			}
		}
	}
	
	public void queueRender(Display display){
		if( Game.state == Game.STATE_TITLE)return;
		if(Game.state != Game.STATE_UPGRADE){
			super.queueRender(display);
		}else{
			Font f = new Font("Upgrade MINETRON 2000", Game.WIDTH-80, Game.HEIGHT-100, Font.POS_RIGHT, Color.WHITE, 2f, false);
			f.queueRender(display);
			
			
			for(int i=0;i<cats.length;i++){
				Font cat = new Font(cats[i], 90, Game.HEIGHT-100-(60*i), Font.POS_LEFT, Color.GRAY, 1.5f, false);
				cat.z = 2;
				cat.queueRender(display);
				
				Color col = new Color(0.9f,0.9f,0.9f,1);
				if((catHover != 0 && catHover-1 == i) || (catSelect-1 == i)){
					col = Color.WHITE;
				}
				Renderable bt = new Renderable();
				bt.tex = 3;
				bt.xScale = 140;
				bt.yScale = 50;
				bt.x = 80;
				bt.z =1;
				bt.color = col;
				bt.y = Game.HEIGHT-110-(60*i);
				bt.queueRender(display);
			}
			
			if(catSelect != 0 && catSelect-1 < items.length){
				if(items[catSelect-1] != null){
					for(int i = 0;i<items[catSelect-1].length;i++){
						Upgrade u = items[catSelect-1][i];
						Font title = new Font(u.title, 250, Game.HEIGHT-100-(115*i), Font.POS_LEFT, Color.GRAY, 1.5f, false);
						title.z = 2;
						title.queueRender(display);
						
						Font desc = new Font(u.desc, 250, Game.HEIGHT-140-(115*i), Font.POS_LEFT, Color.GRAY, 0.75f, false);
						desc.z = 2;
						desc.queueRender(display);
						
						Font cost = new Font("Cost: "+u.cost, 250, Game.HEIGHT-165-(115*i), Font.POS_LEFT, Color.BLACK, 0.75f, false);
						cost.z = 2;
						cost.queueRender(display);
						
						Renderable bt = new Renderable();
						bt.tex = 3;
						bt.xScale = 350;
						bt.yScale = 100;
						bt.x = 240;
						bt.z =1;
						bt.y = Game.HEIGHT-160-(115*i);
						bt.queueRender(display);
						
						if(w.player.hasUpgrade(u) || (u.cost < w.player.money && (i==0 || w.player.hasUpgrade(items[catSelect-1][i-1])))){
							Renderable bg = new Renderable();
							
							Color col2 = new Color(0.85f,0.85f,0.85f,1);
							
							bg.tex = 3;
							bg.xScale = 150;
							bg.yScale = 60;
							
							
							Color col = Color.BLACK;
							String txt = "Buy";
							if(w.player.hasUpgrade(u)){
								col = Color.GRAY;
								txt = "Bought";
							}else if(itemHover-1 == i){
								col2 = Color.WHITE;
							}
							Font buy = new Font(txt, 675, Game.HEIGHT-125-(115*i), Font.POS_CENTER, col, 1.5f, false);
							buy.z = 2;
							buy.queueRender(display);
							
							bg.color = col2;
							bg.x = 600;
							bg.z =1;
							bg.y = Game.HEIGHT-140-(115*i);
							bg.queueRender(display);
						}
						
					}
				}
			}
			
			Renderable bt = new Renderable();
			bt.tex = 3;
			bt.xScale = 350;
			bt.yScale = 100;
			bt.x = Game.WIDTH-400;
			bt.z =1;
			bt.y = 40;
			Color col2 = new Color(0.9f,0.9f,0.9f,1);
			if(exitHover)col2=Color.WHITE;
			bt.color = col2;
			bt.queueRender(display);
			
			Font exit = new Font("Exit", Game.WIDTH-225, 90, Font.POS_CENTER, Color.BLACK, 3f, false);
			exit.z = 2;
			exit.queueRender(display);
		}
	}
	
	public boolean touchMoved(int x, int y) {
		if(Game.state == Game.STATE_UPGRADE){
			if(x > 600 && x < 750){
				int over = Math.round((y+60)/115);
				if((y+60)%115 > 85 || (y+60)%115 < 25){
					itemHover = 0;
					return true;
				}
				itemHover = over;
			}else if(x > 80 && x < 220){	
				int over = Math.round(y/60);
				if(y%60 > 50){
					catHover = 0;
					return true;
				}
				if(over > 6 || over < 1){
					catHover = 0;
				}else{
					catHover = over;
				}
			}else{
				catHover = 0;
				itemHover = 0;
			}
			if(new Rectangle(1040,820,350,100).contains(x, y)){
				exitHover = true;
			}else{
				exitHover = false;
			}
		}
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer) {
		if(Game.state == Game.STATE_UPGRADE){
			if(catSelect == catHover){
				catSelect = 0;
			}else if(x > 80 && x < 280 && catHover != 0){	
				catSelect = catHover;
			}
			
			if(exitHover){
				Camera.reset();
				Camera.setTarget(w.player);
				Camera.updatePos();
				Game.state = Game.STATE_SURFACE;
			}
			
			if(x > 600 && x < 750 && itemHover != 0){
				Upgrade u = this.items[catSelect-1][itemHover-1];
				if(!w.player.hasUpgrade(u) &&  (itemHover-1 == 0 || w.player.hasUpgrade(this.items[catSelect-1][itemHover-2])) && w.player.money > u.cost){
					w.player.spendCash(u.cost);
					w.player.addUpgrade(u);
					Resources.upgrade.play();
				}
			}
		}
		return true;
	}
}
