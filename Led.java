package bekay.light.common;

public class Led {
	public int r;
	public int g;
	public int b;
	
	public void setColor(double r,double g,double b){
		setColor((int)Math.round(r),(int) Math.round(g),(int) Math.round(b));
	}
	
	public void setColor(int r,int g,int b){
		if(r < 0){
			r = 0;
		}
		if(g < 0){
			g = 0;
		}
		if(b < 0){
			b = 0;
		}
		if(r > 255){
			r = 255;
		}
		if(g > 255){
			g = 255;
		}
		if(b > 255){
			b = 255;
		}
		this.r = r;
		this.b = b;
		this.g = g;
		
	}
}
