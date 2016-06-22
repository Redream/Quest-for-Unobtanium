import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;


public class Miner extends Entity {
	public int right;
	public int left;
	public int up;
	public int down;
	public int space;
	public int shift;
	
	public int animtick = 60;
	public int animtick2 = 30;
	public int orientation = 0;
	
	public float fuel = 500;
	public float maxfuel = 1000;
	
	public float storage = 0;
	public float maxstorage = 1000;
	
	public float temp = 0;
	public float maxtemp = 100000;
	public float tempresist = 1;
	
	public float speed = 1.2f;
	public boolean canboost = false;
	
	public boolean btnHover = false;
	
	public int money = 500;
	
	public ArrayList<DroppedResource> resources = new ArrayList<DroppedResource>();
	public int[] resourceCount = new int[6];
	
	public int drillLevel = 1;
	
	public ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	public boolean rainbowSmoke = false;
	
	public int alertTimer = 50;
	
	
	public Miner(World w){
		super(w);
		this.tex = 2;
		this.z = 9;
		this.xScale = 8;
		this.yScale = 8;
		this.height = 13;
		this.width = 17;
		this.y = 128;
		this.x = 1400;
		
		this.collides = true;
		this.applyCam =true;
		
		
		Input.registerListener(this);
		
		Camera.setTarget(this);
		
		Camera.reset();
		Camera.updatePos();
		
		this.addUpgrade(new DrillUpgrade(0, this));
		this.addUpgrade(new FuelUpgrade(0, this));
		this.addUpgrade(new EngineUpgrade(0, this));
		this.addUpgrade(new StorageUpgrade(0, this));
		this.addUpgrade(new ArmorUpgrade(0, this));
		this.addUpgrade(new PaintUpgrade(0, this));
		
		if(Game.debug){
			this.collides = false;
			this.speed= 20;
			this.gravity = 0;
		}
	}
	
	public void queueRender(Display display){
		for(Upgrade u : this.upgrades){
			u.queueRender(display);
		}
		
		if(Game.state != Game.STATE_INTRO && Game.state != Game.STATE_MINEINTRO && Game.state != Game.STATE_ENDGAME  && Game.state != Game.STATE_TITLE){
			Renderable cash = new Renderable();
			cash.tex= 21;
			cash.xScale = 8;
			cash.yScale = 8;
			cash.z = 999;
			cash.y = 62;
			cash.x = 50;
			cash.queueRender(display);
			
			Font f = new Font(Integer.toString(money), 150, 96, Font.POS_LEFT, Color.WHITE, 3f, false);
			f.z = 999;
			f.queueRender(display);
		}
		
		if(Game.state != Game.STATE_UPGRADE && Game.state != Game.STATE_TITLE && Game.state != Game.STATE_INTRO && Game.state != Game.STATE_MINEINTRO && Game.state != Game.STATE_ENDGAME){
			float c1 = 1;
			float c2 = 1;
			if(alertTimer < 15){
				if(fuel/maxfuel < 0.3f)c1 = 0;
				if(temp/maxtemp > 0.7f)c2 = 0;
			}
			alertTimer--;
			if(alertTimer == 0){
				alertTimer = 30;
			}
			
			this.renderGauge("Fuel", new Color(0.458f,0.827f,0.454f,c1), Game.WIDTH-200, 70, fuel/maxfuel, 1.5f,display);
			this.renderGauge("Temp", new Color(0.819f,0.47f,0.439f,c2),  Game.WIDTH-200, 200, temp/maxtemp, 1.5f,display);
			if(w.shownMineIntro)this.renderGauge("Unobtanium Proximity", Color.WHITE,  Game.WIDTH-200, 330, 1-((w.bigdiamond.y-this.y)/-11543), 0.75f,display);
			
			
			int shown = 0;
			for(int i=0;i<resourceCount.length;i++){
				DroppedResource r = DroppedResource.getResource(i, w);
				if(r==null || resourceCount[i] == 0)continue;
				r.applyCam = false;
				r.x = Game.WIDTH-380;
				r.z = 999;
				r.y = Game.HEIGHT-645-260+50*shown;
				r.queueRender(display);
				Font t = new Font(": "+resourceCount[i], Game.WIDTH-320, Game.HEIGHT-645-262+50*shown, Font.POS_CENTER, Color.WHITE, 1.5f, false);
				t.z = 999;
				t.queueRender(display);
				shown++;
			}
			
			Renderable bt = new Renderable();
			bt.tex = 3;
			int height = 110+shown*50;
			bt.xScale = 160;
			bt.yScale = height;
			bt.x = Game.WIDTH-420;
			bt.z =1;
			bt.color = new Color(0,0,0,0.6f);
			bt.y = 30;
			bt.queueRender(display);
			
			Font s = new Font("Storage", Game.WIDTH-340, Game.HEIGHT-645-220+50*shown, Font.POS_CENTER, Color.WHITE, 1.5f, false);
			s.z = 999;
			s.queueRender(display);
			Font s2 = new Font(Math.round(storage/maxstorage*100)+"% full", Game.WIDTH-340, Game.HEIGHT-645-260+50*shown, Font.POS_CENTER, Color.WHITE, 1.5f, false);
			s2.z = 999;
			s2.queueRender(display);
		}
		super.queueRender(display);
	}
	
	public boolean touchMoved(int x, int y) {
		y = Game.HEIGHT - y;

		return true;
	}
	
	public void spendCash(int amount){
		if(money >= amount)money -= amount;
		TextParticle t = new TextParticle(w,"-"+amount,false,3f);
		t.x = 215;
		t.y = 150;
		w.addEntity(t);
	}
	
	public void gainCash(int amount){
		money += amount;
		TextParticle t = new TextParticle(w,"+"+amount,false,3f);
		t.x = 215;
		t.y = 150;
		w.addEntity(t);
	}
	
	public void renderGauge(String name,Color color, float x,float y,float percent,float textScale, Display display){
		percent = Math.max(Math.min(percent,1), 0);
		Renderable gauge = new Renderable();
		gauge.xScale = 8;
		gauge.yScale = 8;
		gauge.x = x;
		gauge.y = y;
		gauge.tex = 20;
		gauge.z = 1000;
		gauge.queueRender(display);
		
		Color col = Color.WHITE;
		if(Game.state == Game.STATE_MINE)col=Color.GRAY;
		Font f = new Font(name.toUpperCase(), x+77, y-40, Font.POS_CENTER, col, textScale, false);
		f.z = 999;
		f.queueRender(display);
		
		Renderable bar = new Renderable();
		bar.xScale = 128*percent;
		bar.yScale = 40;
		bar.x = x+8;
		bar.y = y+8;
		bar.tex = 3;
		bar.z = 999;
		bar.color = color;
		bar.queueRender(display);
		
		Renderable bg = new Renderable();
		bg.xScale = 128;
		bg.yScale = 40;
		bg.x = x+8;
		bg.y = y+8;
		bg.tex = 3;
		bg.applyCam = false;
		bg.z = 998;
		bg.color = new Color(0.6f,0.6f,0.6f,1);
		bg.queueRender(display);
	}
	
	public boolean hasUpgrade(Upgrade u){
		for(Upgrade u2 : upgrades){
			if(u2.type == u.type)return true;
		}
		return false;
	}	
	
	public void addUpgrade(Upgrade u){
		u.init();
		this.upgrades.add(u);
	}
	
	public void tick(){
		super.tick();
		float boost = 0;
		if(canboost && shift != 0)boost = 0.5f;
		
		if(Game.state == Game.STATE_SURFACE || Game.state == Game.STATE_MINE){
			if(right != 0){
				vx = speed+boost;
			}
			
			if(left != 0){
				vx = -speed-boost;
			}
			
			if(right == 0 && left == 0){
				vx *= 0.97f;
			}else if(Game.state == Game.STATE_MINE){
				vy = 0;
			}
		}
		if(Game.debug){
			if(up != 0){
				vy = speed+boost;
			}
			
			if(down != 0){
				vy = -speed-boost;
			}
		}
		if(Game.state == Game.STATE_MINE){	
			if(1-((w.bigdiamond.y-this.y)/-11543) > 0.97f){
				w.showTooltip("Press SPACE to mine the unobtanium!");
				if(this.space != 0 && w.tooltipPos < -79){
					Camera.setTarget(w.bigdiamond);
					Game.state = Game.STATE_ENDGAME;
				}
			}
			if(fuel/maxfuel < 0.01f){
				Game.state = Game.STATE_OUTOFFUEL;
				w.transitionFade = 0;
			}
			if(temp/maxtemp > 0.99f){
				Game.state = Game.STATE_OVERHEAT;
				w.transitionFade = 0;
			}
			
			temp += (16/this.tempresist)*(this.y / -10000);
			
			xScale = 7;
			yScale = 7;
			
			if(up != 0){
				vy = speed+boost;
			}
			
			if(down != 0){
				vy = -speed-boost;
			}
			
			if(up == 0 && down == 0){
				vy *= 0.97f;
			}else{
				vx = 0;
			}
			
			if(vy > 0 && tex < 17){
				this.mirrorY = true;
			}else if(vy < 0){
				this.mirrorY = false;
			}
			
			if((right != 0 || left != 0) && tex < 17){
				tex = 17;
			}
			if((up != 0 || down != 0)  && tex > 16){
				tex = 16;
			}
			
			if(space != 0){
				float rx = this.x;
				float ry = this.y;
				if(tex>16){
					if(mirrorX){
						rx -= 128;
					}else{
						rx += 128;
					}
				}else{
					if(mirrorY){        
						ry += 128;
					}else{
						ry -= 128;
					}
				}
				Rock r = w.getRockat(rx, ry);
				if(r != null){
					r.mine(0.6f + this.drillLevel*0.06f);
					this.fuel -= 0.14f + this.drillLevel*0.03f;
				}
			}
		}else{
			if(temp > 0)temp += -temp*0.001f;
		}
		
		if(vx < 0 && (Game.state != Game.STATE_MINE || tex > 16)){
			this.mirrorX = true;
		}else{
			this.mirrorX = false;
		}
		
		Random r = new Random();
		if(r.nextInt(2) == 1){
			Particle p = new Particle(w);
			p.x = x+18;
			p.y = y+110;
			if(this.mirrorX)p.x = x+114;
			if(Game.state == Game.STATE_MINE){
				if(tex > 16){
					if(mirrorX){
						p.x = x+96;
					}
					p.y = y+24;
				}else{
					if(mirrorY){
						p.y = y+18;
					}
					p.x = x+25;
				}
			}
			
			p.xScale = r.nextInt(5)+3;
			p.yScale = p.xScale;
			p.vy = new Random().nextFloat() * 0.15f+0.12f;
			p.vx = (float) (new Random().nextGaussian() * 0.04f);
			if(rainbowSmoke){
				float col = r.nextFloat();
				int n = r.nextInt(2);
				float red = 1;
				float g = 1;
				float b = 1;
				if(n == 0){
					b = col;
					if(r.nextBoolean()){
						g = 0;
					}else{
						red = 0;
					}
				}
				if(n == 1){
					red = col;
					if(r.nextBoolean()){
						g = 0;
					}else{
						b = 0;
					}
				}
				if(n == 2){
					g = col;
					if(r.nextBoolean()){
						red = 0;
					}else{
						b = 0;
					}
				}
				p.color = new Color(red,g,b,0.2f);
			}
			w.addEntity(p);
		}
		if(Game.state == Game.STATE_SURFACE || Game.state == Game.STATE_MINETRANSITION || Game.state == Game.STATE_TITLE){
			animtick--;
			if(animtick == 0){
				animtick =(int) (50-(Math.min(1.2f,Math.abs(vx))*30));
				if(this.yScale == 8){
					this.yScale=8.2f;
				}else{
					this.yScale=8;
				}
			}
			
		}

		if(Math.abs(vx) > 0.1f || Math.abs(vy) > 0.1f || space != 0)animtick2--;
		if(animtick2 <= 0){
			if(Game.state == Game.STATE_MINE && (Math.abs(vx) > 0.1f || Math.abs(vy) > 0.1f))this.fuel -= 0.4f;
			if(this.shift != 0 && this.canboost)this.fuel -= 0.4f;
			animtick2 = 30;
			
			if(this.tex == 2){
				this.tex = 4;
			}else if(tex == 4){
				this.tex = 2;
			}else if(tex == 15){
				this.tex = 16;
			}else if(tex == 16){
				this.tex = 15;
			}else if(tex == 17){
				this.tex = 18;
			}else if(tex == 18){
				this.tex = 17;
			}
		}
		if(tex > 16)mirrorY = false;
		
		
	}
	
	public boolean keyDown(int keycode) {
		if(keycode == Keys.D || keycode == Keys.RIGHT){
			right = keycode;
		}
		if(keycode == Keys.A || keycode == Keys.LEFT){
			left = keycode;
		}
		if(keycode == Keys.W || keycode == Keys.UP){
			up = keycode;
		}
		if(keycode == Keys.S || keycode == Keys.DOWN){
			down = keycode;
		}
		if(keycode == Keys.SPACE){
			space = keycode;
		}
		if(keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT){
			shift = keycode;
		}
		return true;
	}
	
	public boolean keyUp(int keycode) {
		if(right == keycode){
			right = 0;
		}
		if(left == keycode){
			left = 0;
		}
		if(up == keycode){
			up = 0;
		}
		if(down == keycode){
			down = 0;
		}
		if(space == keycode){
			space = 0;
		}
		if(shift == keycode){
			shift = 0;
		}
		return true;
	}
	
	public void mineInit(){
		this.tex = 15;
		this.height = 15;
		this.width = 15;
		this.xScale = 7;
		this.yScale = 7;
	}
	
	public void leaveMine(){
		this.tex = 2;
		this.xScale = 8;
		this.yScale = 8;
		this.height = 13;
		this.width = 17;
		this.mirrorY = false;
		this.mirrorX = true;
		this.gravity = 0.1f;
	}

	public void giveResource(DroppedResource dropped) {
		this.storage += dropped.size;
		this.resources.add(dropped);
		this.resourceCount[dropped.resourceType]++;
	}

}
