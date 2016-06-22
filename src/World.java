import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;


public class World extends Renderable {
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Rock[] rocks = new Rock[200000];
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public ArrayList<Entity> entityqueue = new ArrayList<Entity>();
	private String[] introdialog = new String[]{
			"Welcome to the desert!",
			"You've been recruited by the best in order to command a new mining operation.",
			"We need to use the MINETRON 2000 to find a rare, hidden gem.",
			"Use WASD or ARROW KEYS to move, SPACEBAR to interact.",
			"Fuel up, then head BENEATH THE SURFACE. I'll explain more.",
	};
	private String[] minedialog = new String[]{
			"These are the mines.",
			"You'll need to collect enough materials in order to make some cash.",
			"Then we'll be able to upgrade the MINETRON 2000 and get deeper.",
			"Eventually we'll be able to get what we want..",
			"This rare unobtainium gem - that's the prize.",
			"Remember - don't overheat or run out of fuel.",
			"The deeper you go, the more valuable the rocks are.",
			"Anyway, you best get to work. Use SPACE to mine.",
	};
	private String[] enddialog = new String[]{
			"You attempt to mine the unobtanium.",
			"But, as you pierce into the reflective surface, something happens.",
			"The unobtanium shatters into thousands of pieces.",
			"This was obvious - you should have seen it coming.",
			"Beneath the surface, unobtainium is - indeed - unobtainable."
	};
	private String transtitionDialog = "Entering the MINES...";
	private String surfaceDialog = "Returning to the SURFACE...";
	private int dialogTimer = 4;
	public String activeDialog = "";
	private int dialogProg = 0;
	private int dialogfade = 0;
	private float dialogFadeout;
	
	public String tooltip = "";
	public int tooltiptimer = 0;
	public float tooltipPos = 200;
	

	public boolean shownMineIntro = false;
	
	public float transitionFade = 0;
	public Miner player = new Miner(this);
	public Color fontColor;
	
	public int heatPulse = 0;
	
	public BigDiamond bigdiamond;
	
	public World(){
		for(int y =0;y<4;y++){
			for(int x =0;x<32;x++){
				Tile t;
				if(y==0){
					t = new Tile(this);
				}else{
					t = new Dirt(this);
				}
				t.x = x*t.width*t.xScale;
				t.y = -y*t.height*t.yScale;
				this.tiles.add(t);
			}
		}
		
		int width = 100;
		int height = 70;
		for(int y =0;y<height;y++){
			for(int x=0;x<width;x++){
				if(x>48 && x < 51 && y < 14 && y > 5 || (y == 5 && x >48 && x < 51) || (Math.sqrt(Math.pow(x-50, 2)+Math.pow(y-(height-5), 2)) < 5 && y < height-5))continue;
				Rock r = null;
				Random rn = new Random();
				if(y<6 || x<6 || x>width-7 || y > height-7){
					r = new HardRock(this,999);
				}else{
					if(y > height *0.18 && y < height*0.25 && rn.nextInt(8) == 1){
						r = new HardRock(this,1);
					}else if(y > height *0.19 && y < height*0.25 && rn.nextInt(4) == 1){
						r = new HardRock(this,1);
					}else if(y > height *0.2 && y < height*0.25 && rn.nextBoolean()){
						r = new HardRock(this,1);
					}else if(y > height *0.48&& y < height*0.55 && rn.nextInt(8) == 1){
						r = new HardRock(this,2);
					}else if(y > height *0.49 && y < height*0.55 && rn.nextInt(4) == 1){
						r = new HardRock(this,2);
					}else if(y > height *0.5 && y < height*0.55 && rn.nextBoolean()){
						r = new HardRock(this,2);
					}else if(y > height *0.73&& y < height*0.8 && rn.nextInt(8) == 1){
						r = new HardRock(this,3);
					}else if(y > height *0.74 && y < height*0.8 && rn.nextInt(4) == 1){
						r = new HardRock(this,3);
					}else if(y > height *0.75 && y < height*0.8 && rn.nextBoolean()){
						r = new HardRock(this,3);
					}else if(y < height*0.5 && (rn.nextInt(20) == 1 || (this.rocktypeTouching(Rock.COPPER, x, y) && rn.nextInt(4) == 1))){
						r = new Copper(this);
					}else if(y > height*0.1 && y < height * 0.4 && (rn.nextInt(50) == 1 || (this.rocktypeTouching(Rock.COBALT, x, y) && rn.nextInt(3) == 1))){
						r = new Cobalt(this);
					}else if(y > height*0.3 && y < height * 0.9 && (rn.nextInt(60) == 1 || (this.rocktypeTouching(Rock.GOLD, x, y) && rn.nextInt(5) == 1))){
						r = new Gold(this);
					}else if(y > height*0.45 && y < height * 0.85 && (rn.nextInt(40) == 1 || (this.rocktypeTouching(Rock.PLATINUM, x, y) && rn.nextInt(4) == 1))){
						r = new Platinum(this);
					}else if(y > height*0.64 && y < height * 0.95 && (rn.nextInt(60) == 1 || (this.rocktypeTouching(Rock.DIAMOND, x, y) && rn.nextInt(12) == 1))){
						r = new Diamond(this);
					}
				}
				
				if(r == null)r = new Rock(this);
				r.y = -512 -y*r.height*r.yScale;
				r.x = 0 + x*r.width*r.xScale;
				rocks[width*y+x] = r;
			}
		}
		
		this.entities.add(player);
		
		this.bigdiamond = new BigDiamond(this);
		bigdiamond.x = 6164;
		bigdiamond.y = -512 -(height-5)*128;
		this.tiles.add(bigdiamond);
		
		
		Mechanic m = new Mechanic(this);
		m.x = 300;
		m.y = 120;
		this.tiles.add(m);
		
		PetrolStation p = new PetrolStation(this);
		p.x = 1000;
		p.y = 120;
		this.tiles.add(p);
		
		Vendor v = new Vendor(this);
		v.x = 632;
		v.y = 120;
		this.tiles.add(v);
		
		MineEntrance mine = new MineEntrance(this);
		mine.x = 1800;
		mine.y = 128;
		mine.z = 1;
		this.tiles.add(mine);
		
		MineExit me = new MineExit(this);
		me.y = -512 -5*128;
		me.x = 0 + 49*128;
		this.tiles.add(me);
		
		Cloud c = new Cloud(this);
		c.y =  420;
		c.x = 1200;
		c.z = 0;
		c.parallax = 5;
		c.color = new Color(1,1,1,0.8f);
		this.tiles.add(c);
		
		Cloud c1 = new Cloud(this);
		c1.y =  480;
		c1.x = 600;
		c1.z = 0;
		c1.parallax = 1.5f;
		c1.color = new Color(1,1,1,0.9f);
		this.tiles.add(c1);
		
		Cloud c2 = new Cloud(this);
		c2.y =  400;
		c2.x = 200;
		c2.z = 0;
		c2.parallax = 1.2f;
		c2.color = new Color(1,1,1,0.6f);
		this.tiles.add(c2);
		
//		Game.state = Game.STATE_ENDGAME;
//		player.x = this.bigdiamond.x;
//		player.y = this.bigdiamond.y;
	}
	
	public Rock getRockat(float x, float y){
		y = Math.abs(Math.round((y+512)/128));
		x = Math.round(x/128);
		Rock r = rocks[(int) (y*100+x)];
		return r;
	}
	
	public Rock getRockat(int x, int y){
		Rock r = rocks[(int) (y*100+x)];
		return r;
	}
	
	public boolean rocktypeTouching(int type,int x, int y){
		if(this.getRockat(x+1, y) != null && this.getRockat(x+1, y).type == type)return true;
		if(this.getRockat(x-1, y) != null && this.getRockat(x-1, y).type == type)return true;
		if(this.getRockat(x, y+1) != null && this.getRockat(x, y+1).type == type)return true;
		if(this.getRockat(x, y-1) != null && this.getRockat(x, y-1).type == type)return true;
		if(this.getRockat(x-1, y-1) != null && this.getRockat(x-1, y-1).type == type)return true;
		if(this.getRockat(x-1, y+1) != null && this.getRockat(x-1, y+1).type == type)return true;
		if(this.getRockat(x+1, y-1) != null && this.getRockat(x+1, y-1).type == type)return true;
		if(this.getRockat(x+1, y+1) != null && this.getRockat(x+1, y+1).type == type)return true;
		return false;
	}
	
	public void tick(){
		for(int i=0;i<entities.size();i++){
			entities.get(i).tick();
			if(entities.get(i).remove){
				entities.remove(i--);
			}
		}
		for(int i=0;i<rocks.length;i++){
			if(rocks[i] != null){
				rocks[i].tick();
				if(rocks[i].remove)rocks[i]=null;
			}
		}
		for(Entity e : entities){
			e.tick();
		}
		for(Tile t : tiles){
			t.tick();
		}
		this.entities.addAll(entityqueue);
		this.entityqueue.clear();
		
		if(Game.state == Game.STATE_INTRO){
			dialogTimer--;
			fontColor = new Color(0.2f,0.2f,0.2f,1-dialogFadeout);
			if(dialogTimer <= 0){
				dialogTimer = 4;
				if(dialogProg == introdialog.length){
					dialogFadeout += 0.02f;
					if(dialogFadeout >= 1){
						Game.state = Game.STATE_SURFACE;
						activeDialog = "";
					}
				}else if(dialogfade >= introdialog[dialogProg].length()){
					dialogTimer = 120;
					dialogfade=0;
					dialogProg++;
				}else{
					if(dialogfade == 0)activeDialog = "";
					activeDialog = activeDialog + this.introdialog[dialogProg].charAt(dialogfade);
					dialogfade++;
				}
			}
		}
		
		if(Game.state == Game.STATE_UPGRADE){
			Camera.setTarget(null);
			Camera.cam.position.x = player.x-Game.WIDTH/2-95;
		}
		
		if(Game.state == Game.STATE_SURFACE && player.x > 2000){
			Game.state = Game.STATE_MINETRANSITION;
			dialogTimer = 4;
			dialogfade = 0;
			fontColor = Color.WHITE;
		}
		
		if(tooltiptimer == 0){
			this.tooltip = "";
		}
		if(tooltiptimer >= 0)tooltiptimer--;
		
		
		if(Game.state == Game.STATE_MINETRANSITION){
			player.right = 1000;
			this.transitionFade += 0.005f;
			if(transitionFade > 0.3f){
				dialogTimer--;
				if(dialogTimer == 0){
					if(dialogfade == this.transtitionDialog.length()){
						Game.state = Game.STATE_MINEINTRO;
						if(this.shownMineIntro)Game.state = Game.STATE_MINE;
						dialogProg = 0;
						dialogfade =0;
						dialogTimer = 4;
						dialogFadeout=0;
						player.mineInit();
						Camera.disableCollisionX = true;
					
						this.activeDialog = "";
						player.right = 0;
						player.x = 6336;
						player.y = -1400;
						player.vx=player.vy=0;
						player.gravity = 0;
						Camera.reset();
						Camera.updatePos();
						Camera.angle = 0;
					}else{
						if(dialogfade < this.transtitionDialog.length())activeDialog = activeDialog + this.transtitionDialog.charAt(dialogfade);
						dialogTimer = 4;
						dialogfade++;
						if(dialogfade == this.transtitionDialog.length()){
							dialogTimer = 140;
						}
					}
				}
			}
		}
		if(Game.state == Game.STATE_SURFACETRANSITION){
			this.transitionFade += 0.005f;
			if(transitionFade > 0.3f){
				dialogTimer--;
				if(dialogTimer == 0){
					if(dialogfade == this.surfaceDialog.length()){
						Game.state = Game.STATE_SURFACE;
						dialogProg = 0;
						dialogfade=0;
						dialogFadeout=0;
						dialogTimer = 4;
						player.leaveMine();
						Camera.disableCollisionX = false;
					
						this.activeDialog = "";
						player.right = 0;
						player.x = 1800;
						player.y = 150;
						player.vx=-1f;
						player.vy=0;
						Camera.reset();
						Camera.updatePos();
						Camera.angle = 0;
					}else{
						if(dialogfade < this.surfaceDialog.length())activeDialog = activeDialog + this.surfaceDialog.charAt(dialogfade);
						dialogTimer = 4;
						dialogfade++;
						if(dialogfade == this.surfaceDialog.length()){
							dialogTimer = 140;
						}
					}
				}
			}
		}
		String dialog ="";
		if(Game.state == Game.STATE_OVERHEAT){
			dialog = "Your miner overheated, your cargo has been lost. :(";
		}else{
			dialog = "You ran out of fuel, your cargo has been lost. :(";
		}
	
		if(Game.state == Game.STATE_OUTOFFUEL || Game.state == Game.STATE_OVERHEAT){
			this.transitionFade += 0.005f;
			fontColor = Color.WHITE;
			if(transitionFade > 0.3f){
				dialogTimer--;
				if(dialogTimer == 0){
					if(dialogfade == dialog.length()){
						Game.state = Game.STATE_SURFACE;
						dialogProg = 0;
						dialogfade=0;
						dialogFadeout=0;
						dialogTimer = 4;
						player.leaveMine();
						Camera.disableCollisionX = false;
						
						for(int i=0;i<player.resourceCount.length;i++){
							player.resourceCount[i]=0;
						}
						player.resources.clear();
						player.storage = 0;
						
						this.activeDialog = "";
						player.right = 0;
						player.x = 1800;
						player.y = 150;
						player.vx=-1f;
						player.vy=0;
						Camera.reset();
						Camera.updatePos();
						Camera.angle = 0;
					}else{
						if(dialogfade < dialog.length())activeDialog = activeDialog + dialog.charAt(dialogfade);
						dialogTimer = 4;
						dialogfade++;
						if(dialogfade == dialog.length()){
							dialogTimer = 140;
						}
					}
				}
			}
		}
		
		if(Game.state == Game.STATE_ENDGAME){
			dialogTimer--;
			fontColor = new Color(1f,1f,1f,1-dialogFadeout);
			if(dialogTimer <= 0){
				dialogTimer = 4;
				if(dialogProg == enddialog.length){
					dialogFadeout += 0.02f;
					this.transitionFade = dialogFadeout;
					if(dialogFadeout >= 1){
						Game.state = Game.STATE_TITLE;
						Game.finished = true;
						Game.doReset = true;
						activeDialog = "";
						dialogProg = 0;
						dialogfade=0;
						dialogFadeout=0;
						dialogTimer = 4;
					}
				}else if(dialogfade >= enddialog[dialogProg].length()){
					dialogTimer = 120;
					dialogfade=0;
					dialogProg++;					
				}else{
					if(dialogfade == 0){
						activeDialog = "";
						if(dialogProg == 2){
							this.bigdiamond.shatter();
						}
					}
					activeDialog = activeDialog + this.enddialog[dialogProg].charAt(dialogfade);
					dialogfade++;
				}
			}
		}
		
		if(Game.state == Game.STATE_MINEINTRO){
			dialogTimer--;
			fontColor = new Color(1f,1f,1f,1-dialogFadeout);
			if(dialogTimer <= 0){
				dialogTimer = 4;
				if(dialogProg == minedialog.length){
					dialogFadeout += 0.02f;
					if(dialogFadeout >= 1){
						Game.state = Game.STATE_MINE;
						this.shownMineIntro = true;
						activeDialog = "";
						dialogProg = 0;
						dialogfade=0;
						dialogFadeout=0;
						dialogTimer = 4;
					}
				}else if(dialogfade >= minedialog[dialogProg].length()){
					dialogTimer = 120;
					dialogfade=0;
					dialogProg++;
					if(dialogProg == 4){
						Camera.setTarget(bigdiamond);
					}
					
				}else{
					if(dialogfade == 0){
						activeDialog = "";
						if(dialogProg == 5){
							Camera.setTarget(player);
						}
					}
					activeDialog = activeDialog + this.minedialog[dialogProg].charAt(dialogfade);
					dialogfade++;
				}
			}
		}
		
	}
	
	public void showTooltip(String txt){
		this.tooltiptimer = 1000;
		this.tooltip = txt;
	}
	
	public void queueRender(Display display){
		if(Game.state != Game.STATE_ENDGAME){
			for(Entity e : entities){
				e.queueRender(display);
			}
			for(Tile t : tiles){
				t.queueRender(display);
			}
			for(int i=0;i<rocks.length;i++){
				if(rocks[i] != null){
					rocks[i].queueRender(display);
				}
			}
		}else{
			player.queueRender(display);
			bigdiamond.queueRender(display);
		}
		
		if(activeDialog != ""){
			Font f = new Font(activeDialog, Game.WIDTH/2, Game.HEIGHT-100, Font.POS_CENTER, fontColor, 1.5f,false);
			f.z = 1001;
			f.queueRender(display);
		}
		
		if(tooltiptimer > 0){
			tooltiptimer--;
			Color col = Color.BLACK;
			if(Game.state == Game.STATE_MINE || Game.state == Game.STATE_MINEINTRO){
				col = Color.WHITE;
			}
			Font f = new Font(tooltip, Game.WIDTH/2, Game.HEIGHT+tooltipPos, Font.POS_CENTER, col, 1.5f,false);
			f.z = 1001;
			f.queueRender(display);
		}
		
		if(tooltiptimer > 0 && tooltiptimer < 990){
			tooltipPos += (80 - tooltipPos)*0.05f;
		}
		if(tooltipPos > -80 && tooltiptimer > 990){
			tooltipPos += (-80 - tooltipPos)*0.05f;
		}
		
		
		
		if(Game.state == Game.STATE_MINETRANSITION || Game.state == Game.STATE_SURFACETRANSITION || Game.state == Game.STATE_OUTOFFUEL || Game.state == Game.STATE_OVERHEAT || this.transitionFade > 0){
			if(Game.state != Game.STATE_MINETRANSITION && Game.state != Game.STATE_SURFACETRANSITION && Game.state != Game.STATE_OUTOFFUEL && Game.state != Game.STATE_OVERHEAT)this.transitionFade -= 0.006f;
			Renderable overlay = new Renderable();
			overlay.tex = 3;
			overlay.xScale = Game.WIDTH;
			overlay.yScale = Game.HEIGHT;
			overlay.z = 1000;
			overlay.color = new Color(0,0,0,this.transitionFade);
			overlay.queueRender(display);
		}
		
		
		Renderable overlay = new Renderable();
		overlay.tex = 14;
		overlay.xScale = Game.WIDTH/200f;
		overlay.yScale = overlay.xScale;
		overlay.y= -100;
		overlay.z = 990;
		if(Game.state != Game.STATE_MINE && Game.state != Game.STATE_MINEINTRO && Game.state != Game.STATE_SURFACETRANSITION && Game.state != Game.STATE_OUTOFFUEL && Game.state != Game.STATE_OVERHEAT)overlay.color=new Color(1,1,1,0.3f);
		overlay.queueRender(display);
		
		if(Game.state == Game.STATE_MINE || Game.state == Game.STATE_SURFACETRANSITION || Game.state == Game.STATE_OUTOFFUEL || Game.state == Game.STATE_OVERHEAT || Game.state == Game.STATE_MINEINTRO){
			Renderable heat = new Renderable();
			heat.tex = 37;
			heat.xScale = Game.WIDTH/200f + (player.temp/player.maxtemp)*1.2f;
			heat.yScale = overlay.xScale;
			heat.y= -1000 + Math.abs(heatPulse-200);
			heat.z = 991;
			heat.width = 200;
			heat.height = 200;
			heat.x = Game.WIDTH/2 - heat.width*heat.xScale/2;
			heat.color=new Color(1,1,1,0.3f+(player.temp/player.maxtemp)*0.4f);
			heat.queueRender(display);
			if(heatPulse <= 0){
				heatPulse = 400;
			}
			heatPulse--;
		}
		
	}
	
	public void addEntity(Entity e){
		this.entityqueue.add(e);
	}
}
