package it.cnr.to.geoclimalp.dbalps.bean.ubicazione;

public class Coordinate {
	Double x;
	Double y;
	public Coordinate(){
		x = 0.0;
		y = 0.0;
	}
	
	public Double getX(){
		return x;
	}
	public Double getY(){
		return y;
	}
	public void setX(Double x){
		this.x = x;
	}
	public void setY(Double y){
		this.y = y;
	}
	public String toDB(){
		String out = "'POINT("+getX()+" "+getY()+")'";
		return out;
	}
	public String toString(){
		String out = ""+x+""+y+"";
		return out;
	}
	public boolean isEmpty(){
		if(x==0.0 && y==0.0)
			return true;
		else return false;
	}
}
