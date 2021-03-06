package src;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * 
 * @author xbb
 *tank class  
 */
public class Tank 
{
	private int x;
	private int y;
	private int oldX;
	private int oldY;
	private boolean good;
	private boolean live=true;
	private static Random r = new Random();
	private int life=100;
	private BloodBar bb= new BloodBar();
	
	public static final int WIDTH=30;
	public static final int HEIGH=30;
	public static final int XSPEED=4;
	public static final int YSPEED=4;
	private boolean left=false;
	private boolean right=false;
	private boolean up=false;
	private boolean down=false;
	TankClient tc;
	
	/*
	 *炮筒方向和坦克运动方向，打出的子弹方向和炮筒一样 
	 */
	private TankDirection dir = TankDirection.STOP;
	private TankDirection ptdir=TankDirection.D;
	private int step=r.nextInt((12)+5);
	 
	/*
	 * 坦克运动的八个方向
	 */
	enum TankDirection{U,D,L,R,LU,RU,LD,RD,STOP};
	/**
	 * 构造方法
	 * @param x
	 * @param y
	 * @param good
	 */
	public Tank(int x, int y,boolean good)
	{
		this.x = x;
		this.y = y;
		this.oldX=x;
		this.oldY=y;
		this.good=good;
	}
	
	//持有对方引用
	public Tank(int x, int y,boolean good,TankDirection dir,TankClient tc) 
	{
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
	}
	/**
	 * 话坦克和炮筒
	 * @param g
	 */
	public void draw(Graphics g)
	{
		if(!live)	return ;
		Color c = null;
		c=g.getColor();
		if(!this.good)	
		{
			g.setColor(Color.blue);
			
			//tc.enemyTanks.add(this);
		}
		else	
		{
			g.setColor(Color.red);
			bb.draw(g);
			
		}
		g.fillOval(x, y, WIDTH,HEIGH );
		g.setColor(c);
		
	switch(ptdir)
		{
		case L:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x, y + Tank.HEIGH/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH, y + Tank.HEIGH/2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH, y + Tank.HEIGH);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH/2, y + Tank.HEIGH);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x, y + Tank.HEIGH);
			break;
		default:
			break;
		}
		/*for(int i=0;i<m.size();i++)
		{
			Misssle m1=m.get(i);
			if(m1.isLive())
			{
				m1.draw(g);
			}
		}*/
		move();//注意move（）的地方
	}
	/**
	 * 坦克移动
	 */
	public void move ()
	{
		//int keyValue=e.getKeyCode();
		//MoveDir(e);
		oldX=x;
		oldY=y;
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
		if(this.dir!=TankDirection.STOP)
		{
			this.ptdir=this.dir;
		}
	
		/**
		 *  防止坦克出界
		 */
		if(x<0) stay();
		if(y<0) stay();
		if(x+Tank.WIDTH>TankClient.GAME_WIDE) stay();
		if(y+Tank.HEIGH>TankClient.GAME_LENGH) stay();
		if(!good)
		{
			TankDirection []dirs=TankDirection.values();
			if(step==0)
			{
				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
				step=r.nextInt((12)+3);
			}
			step--;
			/**
			 * 此处代码防止坦克发出的子弹太快
			 */
			if(r.nextInt(40)>35)
			{
				tc.m.add(this.fire());
			}
		}
	}
	/**
	 * 按键的处理，按下　向上、向下、向左、向右箭头分别向不同的方向移动
	 * 按下ctrl主坦克发射炮弹
	 * 按下f2，主坦克复活
	 * @param e
	 */
	public void keyPressed1(KeyEvent e)
	{
		int keyValue=e.getKeyCode();
		switch(keyValue)
	{
			case KeyEvent.VK_CONTROL:
				tc.m.add(this.fire());break;
			case KeyEvent.VK_RIGHT:
				right=true;break;
			case KeyEvent.VK_LEFT:
				left=true;break;
			case KeyEvent.VK_UP:
				up=true;break;
			case KeyEvent.VK_DOWN:
				down=true;break;
		}
				MoveDir();
		if(this.dir!=TankDirection.STOP)
		{
			this.ptdir=this.dir;
		}
	}
	
	public void keyReased1(KeyEvent e)
	{
		int keyValue=e.getKeyCode();
		switch(keyValue)
		{
			case KeyEvent.VK_F2:
				this.live=true;
				this.setLife(100);
				break;
			case KeyEvent.VK_RIGHT:
				right=false;break;
			case KeyEvent.VK_LEFT:
				left=false;break;
			case KeyEvent.VK_UP:
				up=false;break;
			case KeyEvent.VK_DOWN:
				down=false;break;
		}
		MoveDir();
	}
	
	/**
	 * 
	 * 根据案件判断坦克运动的方向
	 */
	public void MoveDir()
	{
		if((left)&(!right)&(!up)&(!down))
		{
			dir=TankDirection.L;
		}
		
		if((!left)&right&(!up)&(!down))
		{
			dir=TankDirection.R;
		}
		
		if((!left)&(!right)&up&(!down))
		{
				dir=TankDirection.U;
		}
		
		if((!left)&(!right)&(!up)&(down))
		{
			dir=TankDirection.D;
		}
		
		if((left)&(!right)&(!up)&(down))
		{
			dir=TankDirection.LD;
		}
		
		if((left)&(!right)&(up)&(!down))
		{
			dir=TankDirection.LU;
		}
		
		if((!left)&(right)&(!up)&(down))
		{
			dir=TankDirection.RD;
		}
		
		if((!left)&(right)&(up)&(!down))
		{
			dir=TankDirection.RU;
		}
		if((!left)&(!right)&(!up)&(!down))
		{
			dir=TankDirection.STOP;
		}
	}
	
	/**
	 * 发射炮弹
	 * @return　Misssle类的对象
	 */
	
	public Misssle fire()
	{
		int x = this.x + Tank.WIDTH/2 - Misssle.WIDTH/2;
		int y = this.y + Tank.HEIGH/2 - Misssle.HEIGH/2;
		if(this.good)	
			{
			Misssle m = new Misssle(x, y, this.ptdir,true,this.tc);
			return m;
			}
		else 		
			{	
			
			Misssle m = new Misssle(x, y, this.ptdir,false,this.tc);
			return m;
			}
		
		
	}

	public boolean isGood() 
	{
		return good;
	}
	
	public boolean isLive()
	{
		return live;
	}
	
	public void setLive(boolean live)
	{
		this.live=live;
	}
	/**
	 * 用作判断是否发生撞击
	 * @return
	 */
	
	public Rectangle getRec()
	{
		return new Rectangle(x,y,WIDTH,HEIGH);
	}
	/**
	 * 坦克碰墙
	 * @param w
	 * @return
	 */
	 public boolean collepsWithWall (Wall w)
	{ 
		if(this.getRec().intersects(w.getRec())&&(!this.good))
		{
			//live=false;
			//explode e = new explode(this.x,this.y,tc);
			//this.tc.explodes.add(e);
			stay();
			return true;
			
		}
		return false;
	}
	 /**
	  * 坦克不能碾压坦克，碰到会返回ｓ
	  * 注意此处的List
	  * @param tanks
	  * @return
	  */
	 public boolean collepsWithTanks (java.util.List<Tank> tanks)
		{ 
			for(int i =0;i<tanks.size();i++)
			{
				Tank t=tanks.get(i);
				if(this!=t)
				{
					if(this.live&&t.isLive()&&this.getRec().intersects(t.getRec()))
					{
						this.stay();
						t.stay();
						return true;
					}
				}
			}
			return false;
		}

	 public boolean collepsWithBlood (Blood b)
		{ 
			if(this.getRec().intersects(b.getRec())&&(this.good))
			{
				//live=false;
				//explode e = new explode(this.x,this.y,tc);
				//this.tc.explodes.add(e);
				this.setLife(100);
				b.setLive(false);
				return true;
				
			}
			return false;
		}
	 
	private void stay()
	{
		x=oldX;
		y=oldY;
	}

	public int getLife() 
	{
		return life;
	}

	public void setLife(int life) 
	{
		this.life = life;
	}

	private class BloodBar
	{
		public void draw(Graphics g)
		{
			Color c =g.getColor();
			g.setColor(Color.red);
			g.drawRect(x, y-10,WIDTH , 10);
			g.fillRect(x, y-10, WIDTH*life/100, 10);
			g.setColor(c);
		}
	}

	
}

