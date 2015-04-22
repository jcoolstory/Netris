package com.netris;

import java.util.Vector;

public class GameE {
	
	private class Point
	{
		public int x;
		public int y;
		
		public Point()
		{
			x = 0;
			y = 0;
		}
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		public Point(Point y)
		{
			this.x = y.x;
			this.y = y.y;
		}
		public void set(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
	}
	private int[][] nblock  = new int[10][25];;
	private Point pt = new Point(0,0);
	private int StartLine = 5;
	private int MaxWidth = 10;
	private int MaxHeight = 25;
	private int select =0;
	private int shape = 0;
	private Block br = new Block();
	private Point fallPt = new Point(0,0);
	
	private int Nextblock =0;
	private int scoreInt = 0;
	private int levelInt = 0;
	private int gameState = GameState.READY;
	
	public GameE()
	{

	}

	protected Vector vt = new Vector();

	class animate
	{
		long x,y;
		int blocks[];
		public animate(int x, int y, int[] blocks)
		{
			this.x = 0;
			this.y = y;
			this.blocks = blocks;
		}
		public long getY()
		{
			return y;
		}
		public void setY(long y)
		{
			this.y = y;
		}
		public int[] getBlocks() {
			// TODO Auto-generated method stub
			return blocks;
		}
	}
//	class DeletingHandler extends Handler
//	{
//		public void handleMessage(Message msg)
//		{
//			
//		}
//		public void update() 
//		{
//			
//		}
//	}
	public void updateBlocks()
	{
		if (vt.isEmpty() == false)
		{
			animate a;
			int x = vt.size();
			System.out.print("------xsize :" + x);
			for (int i = 0 ; i < x ; i++)
			{
				a = (animate) vt.get(i);
				System.out.print("------a.getY :" + a.getY());
				if (a.getY() > 500)
				{
					vt.remove(i);
				}
				else
				{
					a.setY(a.getY() + 5);
				}
			}
		}

	}
	/**
	 * 
	 */
	public void Start()
	{
		this.reset();
		this.gameState = GameState.PLAY;
		setNextBlock((int)Math.random()*7);
		createBlock();
	}
	/**
	 * 
	 */
	public void Ready()
	{
		gameState = GameState.READY;
	}
	/**
	 * 
	 */
	public void Stop()
	{
		this.gameState = GameState.STOP;
	}
	/**
	 * 
	 */
	public void End()
	{
		this.gameState = GameState.END;
	}
	/**
	 * 
	 */
	public void Resume() {
		// TODO Auto-generated method stub
		this.gameState = GameState.PLAY;
	}
	public void setMode(int flag)
	{
		gameState = flag;
	}
	/**
	 * 
	 */
	public void reset()
	{
		scoreInt = 0;
		levelInt = 1;
		for (int i = 0 ; i < 10 ; i++)
		{
			for (int j = 0 ; j <25 ; j++)
			{
				nblock[i][j]= 0;
			}
		}
	}
	/**
	 * @param block
	 */
	public void setNextBlock(int block)
	{
		this.Nextblock = block;
	}
	/**
	 * @return
	 */
	public int getState()
	{
		return this.gameState;
	}
	/**
	 * @return
	 */
	public int getLevel()
	{
		return this.levelInt;
	}
	/**
	 * @return
	 */
	public int getScore()
	{
		return this.scoreInt;
	}
	/**
	 * @param tscore
	 */
	public void addScore(int tscore)
	{
		this.scoreInt += tscore;
		this.levelInt = (int)(scoreInt / 1000) + 1;
	}
	/**
	 * 
	 */
	public void arriveBlock()
	{
		int lengthX = br.block[select][shape].length;
		
		for (int i = 0 ; i < lengthX ; i++)
		{
			int lengthY = br.block[select][shape][i].length;
			for (int j = 0 ; j < lengthY ; j++)
			{
				if (br.block[select][shape][i][j] != 0 )
				{
					this.nblock[pt.x+i][pt.y + j] = br.block[select][shape][i][j];
				}
			}
		}
	}
	/**
	 * @param line
	 */
	public void aniDelete(int line)
	{
		int tempblock[] = new int[10];
		
		//System.arraycopy(nblock[line], 0, tempblock, 0, 10);
		for (int i = 0 ; i < 10 ; i++)
		{
			tempblock[i] = 
				nblock[i][line];
			System.out.println(nblock[i][line] + "/");
		}
		animate v = new animate(0,(line-5)*20, tempblock);
		vt.add(v);
		 
	}
	/**
	 * 
	 */
	public void crackBlock()
	{
		int tscore = 0;
		boolean bt = true;
		for (int i = 24 ; i > 0 ; i--)
		{
			
			for (int j = 0 ; j < 10 ; j++)
			{
				if (this.nblock[j][i] == 0)
				{
					bt = false;			
				}
			}
			
			if (bt)
			{
				aniDelete(i);
				this.deleteLine(i);
//				Vibrator vb = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
//				vb.vibrate(1000);
//				vibhandler.PutMassage();
	
				tscore += 1;
				i++;
			}
			bt = true;
			
		}
		this.addScore(40 * (tscore) );
	}
	public void deleteLine(int line)
	{
		for (int j = line ; j > 1 ; j--)
		{
			for ( int i = 0 ; i < 10 ; i++)
			{
				this.nblock[i][j] = this.nblock[i][j - 1];		
			}
		}
		for ( int i = 0 ; i < 10 ; i++)
		{
			this.nblock[i][0] = 0;
		}
	}
	public void createBlock()
	{
		intoblock(Nextblock);
		Nextblock = (int)(Math.random()*7);
	}
	/* (non-Javadoc)
	 * @see com.netris.NetrisView#downBlock()
	 */
	public boolean downBlock()
	{
		int narrowY  = pt.y + 1;		
		if (!this.isCrashBlock(new Point(pt.x, narrowY)) )
		{
			pt.y = narrowY ;
	
			this.fallWillBlock();
			return false;
		}
		else
		{
			this.arriveBlock();
			this.crackBlock();
			this.addScore(1);
			return true;
		}
	}
	/**
	 * @param i
	 * @return
	 */
	public boolean intoblock(int i)
	{
	
		this.select = i;
		this.shape = 0;
		
		pt.x = 5 - br.block[select][0].length /2 ;
		pt.y = 6- br.block[select][0][0].length;
		if (this.isCrashBlock(new Point(pt.x, pt.y+1)))
		{
			this.gameState = GameState.END;
			this.End();
			return true;
		}
		this.fallWillBlock();
		
		return false;
	}
	/**
	 * @param tempShape
	 * @param npt
	 * @return
	 */
	public int isRotate(int tempShape,Point npt)
	{
		try{
			if (npt.x < 0)
			{
				return Flag.WALL_TOUCH_LEFT;
			}
			if (npt.x + br.block[select][tempShape].length > this.MaxWidth)
			{
				return Flag.WALL_TOUCH_RIGHT;
			}
			if (npt.y + br.block[select][tempShape].length > this.MaxHeight)
			{
				return Flag.WALL_TOUCH_BOTTOM;
			}
			
			int lengthX = br.block[select][tempShape].length;
			int lengthY;
			for (int i = 0 ; i < lengthX ; i++)
			{
				lengthY = br.block[select][tempShape][i].length;
				for (int j = 0 ; j < lengthY ; j++)
				{
					if (br.block[select][tempShape][j][i] != 0 )
					{
						if (this.nblock[npt.x+j][npt.y + i] != 0)
							return Flag.BLOCK_TOUCH;					
					}
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e){

//			if (Integer.parseInt(e.getMessage()) >= 25)
//			{
//				return Flag.WALL_TOUCH_BOTTOM;
//			}
//			else if (Integer.parseInt(e.getMessage()) >= 10)
//			{
//				return Flag.WALL_TOUCH_RIGHT;
//			}
//			else if (Integer.parseInt(e.getMessage()) < 0)
//			{
//				return Flag.WALL_TOUCH_LEFT;
//			}
//			else
//			{
//				return Flag.WALL_TOUCH_BOTTOM;
//			}
		}
		return Flag.NOT_TOUCH;
	}
	/**
	 * @param npx
	 * @return
	 */
	public boolean isCrashBlock(Point npx)
	{
		int lengthY;
		boolean flag =false;
		try{	
			int lengthX = br.block[select][shape].length;
			for (int i = 0 ; i < lengthX ; i++)
			{
				lengthY = br.block[select][shape][i].length;
				for (int j = 0 ; j < lengthY ; j++)
				{
					if (br.block[select][shape][j][i] != 0 )
					{
						if (this.nblock[npx.x+j][npx.y + i] != 0)
							return true;
					}
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e){return true;}
		return flag;
	}
	/**
	 * 
	 */
	public void fallWillBlock()
	{
		int narrowY = pt.y ;
		
		while(!this.isCrashBlock(new Point(pt.x, narrowY+1)))
		{
			narrowY++;
		} 
		this.fallPt.x= pt.x;
		this.fallPt.y = narrowY;
	}
	/**
	 * 
	 */
	public void fallBlock()
	{
		this.pt.set(fallPt.x, fallPt.y);
		this.arriveBlock();
		this.crackBlock();
		this.addScore(1);
	}
	/**
	 * @param x
	 * @param y
	 */
	public void moveBlock(int x, int y)
	{
		Point tempPt = new Point(pt.x + x, pt.y + y);
				
		if (!this.isCrashBlock(tempPt))
		{
			pt.x = tempPt.x;
			pt.y = tempPt.y;
			this.fallWillBlock();
		}
		
		this.fallWillBlock();

	}
	/**
	 * 
	 */
	public void rotate()
	{
		int narrowSelec = (shape + 1) % br.block[select].length;
		
		Point npt = new Point(pt);
		int flag = -1;
		do{
			flag = this.isRotate(narrowSelec, npt);
			
			if (flag == Flag.WALL_TOUCH_RIGHT)
			{
				npt.x --;
			}
			else if (flag == Flag.WALL_TOUCH_LEFT)
			{
				npt.x ++;
			}
			else if (flag == Flag.NOT_TOUCH )
			{
				pt.x = npt.x;
				shape = narrowSelec;
			}
			else 
			{
				break;
			}
		}
		while(flag != Flag.NOT_TOUCH );
		
			
		this.fallWillBlock();
	}

}