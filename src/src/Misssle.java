package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

/**
 * 
 * @author xbb
 * it is a little TankGame
 * it is written to practise basic javaSE ability
 *
 */
public class Misssle
{
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	public static final int WIDTH=10;
	public static final int HEIGH=10;
	
	private boolean live=true;
	private boolean good;
	
	int x;
	int y;
	Tank.TankDirection dir ;// the direction of missle(pay attention)
	TankClient tc;
	
	public Misssle(int x, int y, Tank.TankDirection dir,boolean good, TankClient tc) 
	 {
		this.x = x;
		this.y = y;
		this.good=good;
		this.dir = dir;
		this.tc=tc;
	}
	 
	public void draw(Graphics g)
	{
		if(!live) return ;
		Color c = null;
		c=g.getColor();
		if(this.good)	g.setColor(Color.black);
		else 	g.setColor(Color.yellow);
		g.fillOval(x, y, HEIGH,WIDTH);
		g.setColor(c);
		move();
	}

	private void move() 
	{
		switch(dir)
		{
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if((x<0)||(y<0)||(y>TankClient.GAME_WIDE)||(x>TankClient.GAME_LENGH))
		{
			live = false;
			tc.m.remove(this);
		}
	}
	
	public boolean isLive()
	{
		return live;
	}
	
	public Rectangle getRec()
	{
		return new Rectangle(x,y,WIDTH,HEIGH);
	}

	public boolean hitTanK (Tank t)
	{ 
		if(this.getRec().intersects(t.getRec())&&t.isLive()&&(this.isGood()!=t.isGood()))
		{
			t.setLive(false);
			live=false;
			explode e = new explode(this.x,this.y,tc);
			this.tc.explodes.add(e);
			return true;
			
		}
		return false;
	}
	
	public boolean hitTanKs (List<Tank> tanks )
	{
		for(int i=0;i<tanks.size();i++)
		{
			if(hitTanK(tanks.get(i)))
			{
				tanks.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean isGood()
	{
		return good;
	}

	public void setGood(boolean good) 
	{
		this.good = good;
	}

}
