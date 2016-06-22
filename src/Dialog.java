import com.badlogic.gdx.graphics.Color;


public class Dialog extends Renderable {
	public String text = "test!";
	public float textScale = 1;
	public Dialog(String text, float scale){
		this.tex = 5;
		this.width = 24;
		this.height = 14;
		this.xScale = 10;
		this.yScale = 10;
		this.applyCam =true;
		this.text = text;
		this.textScale = scale;
	}
	
	public void queueRender(Display display){
		Font f = new Font(this.text, x+this.width*this.xScale/2, y+this.height*this.xScale/2, Font.POS_CENTER,new Color(0.2f,0.2f,0.2f,1),textScale,true);
		f.z  =100;
		f.queueRender(display);
		super.queueRender(display);
	}
}