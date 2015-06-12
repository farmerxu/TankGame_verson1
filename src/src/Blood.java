package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Blood 类，为坦克补充血块
 * @author xbb
 *
 */
public class Blood 
{
	private int x;
	private int y;
	private int w;
	private int h;
	private int step=0;
	private boolean live=true;
	TankClient tc;
	/**
	 * 模拟血块移动的位置
	 */
	private int[][] bloodPos={{350,300},{360,300},{375,310},{400,355},{360,355},{310,310}};
	
	public Blood( TankClient tc) 
	{
		this.x =bloodPos[0][0];
		this.y = bloodPos[0][1];
		this.w = 10;
		this.h = 10;
		this.tc = tc;
	}
	
	public void draw(Graphics g)
	{
		if(!this.live) return ;
		Color c = g.getColor();
		g.setColor(Color.pink);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		move();
	}
	
	private void move()
	{
		step++;
		if(step==bloodPos.length)
		{
			step=0;
		}
		x=bloodPos[step][0];
		x=bloodPos[step][1];
	}

	public boolean getLive()
	{
		return live;
	}

	public void setLive(boolean live)
	{
		this.live = live;
	}
	public Rectangle getRec() 
	{
		return new Rectangle(x,y,w,h);
	}
}
