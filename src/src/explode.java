package src;

import java.awt.Color;
import java.awt.Graphics;

public class explode 
{
	private boolean live=true;
	int x,y;
	private int step=0;	 
	
	int []diameter = {2,4,7,10,15,20,30,41,45,40,20,5};
	
	TankClient tc;
	
	public explode( int x, int y,TankClient tc) 
	{
		this.x = x;
		this.y = y;
		this.tc=tc;
	}
	
	public boolean isLive() 
	{
		return live;
	}
	
	public void draw(Graphics g)
	{
		if(!live) return ;
		if(step==diameter.length)
		{
			live=false;
			step=0;
		}
		g.setColor(Color.black);
		g.fillOval(x, y, diameter[step], diameter[step]);
		step++;
		
	}
}
