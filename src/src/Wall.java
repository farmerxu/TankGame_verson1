package src;

import java.awt.*;

public class Wall 
{
	private int x;
	private int y;
	private int width;
	private int height;
	
	TankClient tc;
	
	public Wall(int x, int y, int width, int height,TankClient tc) 
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tc=tc;
	}
	
	public void draw(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}

	public Rectangle getRec() 
	{
		return new Rectangle(x,y,width,height);
	}
}
