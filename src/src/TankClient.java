package src;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import src.Tank.TankDirection;

public class TankClient extends Frame
{
	private Image offScreamImag = null;//用于解决双缓冲问题
	
	
	public static final int GAME_LENGH=500;
	public static final int GAME_WIDE=500;
	
	Tank mytank = new Tank(30,30,true,TankDirection.STOP,this);
	List<Tank> enemyTanks  =new ArrayList<Tank>();
	List<explode> explodes = new ArrayList<explode>();
	//explode e= new explode(70,70,this);
	//Tank t1 = new Tank(70,30,false,this);
	
	 List<Misssle> m  =new ArrayList<Misssle>();
	
	
	public void launchFrame()
	{
		for(int i=1;i<=10;i++)
		{
			enemyTanks.add(new Tank(30+40*i,30,false,TankDirection.D,this));
		}
		this.setSize(GAME_WIDE, GAME_LENGH);
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
			
		}
		);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.black);
		g.drawString("missle count "+m.size(), 30, 20);
		g.drawString("ememytank count  "+enemyTanks.size(), 200, 20);
		g.setColor(c);
		
		//t1.draw(g);
		for(int i=0;i<enemyTanks.size();i++)
		{
			Tank kk = enemyTanks.get(i);
			kk.draw(g);
		}
		for(int i=0;i<m.size();i++)
		{
			Misssle m1=m.get(i);
			if(m1.isLive())
			{
				if(m1.hitTanKs(enemyTanks)) m.remove(i);
				m1.draw(g);
				m1.hitTanK(mytank);
			}
		}
		for(int i=0;i<explodes.size();i++)
		{
			explode e= explodes.get(i);
			e.draw(g);
		}
		mytank.draw(g);
	}
	
	public static void main(String[] args)
	{
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable
	{
		public void run()
		{
				while(true)
				{
					repaint();
					try 
					{
						Thread.sleep(100);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
		}

	}
	public void update(Graphics g) 
	{
		if(offScreamImag==null)
		{
			offScreamImag=this.createImage(GAME_WIDE, GAME_LENGH);
		}
		Graphics goffScreamImag=offScreamImag.getGraphics();
		goffScreamImag.setColor(Color.GREEN);
		goffScreamImag.fillRect(0, 0, GAME_WIDE, GAME_LENGH);
		paint(goffScreamImag);
		g.drawImage(offScreamImag, 0, 0, null);
	}
	
	private class KeyMonitor extends  KeyAdapter
	{
		public void keyPressed(KeyEvent e) 
		{
			mytank.keyPressed1(e);
//			int keyValue=e.getKeyCode();
//		
//			switch(keyValue)
//			{
//				case KeyEvent.VK_RIGHT:
//					x+=5;break;
//				case KeyEvent.VK_LEFT:
//					x-=5;break;
//				case KeyEvent.VK_UP:
//					y-=5;break;
//				case KeyEvent.VK_DOWN:
//					y+=5;break;
//			}
		}

		public void keyReleased(KeyEvent e)
		{
			mytank.keyReased1(e);
		}
	}
}
