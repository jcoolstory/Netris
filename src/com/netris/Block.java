package com.netris;

class Block
{
	public int block[][][][] = {
	{		
		{{0,0,0,0},
		 {1,1,1,1},
		 {0,0,0,0}, 
		 {0,0,0,0}},
		 
		{{0,1,0,0},
		 {0,1,0,0}, 
		 {0,1,0,0}, 
		 {0,1,0,0}}
					},
	//
	{
		{{2,0,0}, 
		 {2,0,0},
		 {2,2,0}},
		
		{{0,0,0},
		 {0,0,2},
		 {2,2,2}},
		 
		{{0,2,2},
		 {0,0,2},
		 {0,0,2}},
		  
	  	{{2,2,2},
	  	 {2,0,0},
	  	 {0,0,0}}
						},
	{
		{{0,0,3}, 
		 {0,0,3},  
		 {0,3,3}},
		 
		{{3,3,3}, 
		 {0,0,3}, 
		 {0,0,0}},
		 
		{{3,3,0}, 
		 {3,0,0}, 
		 {3,0,0}},
	
		{{0,0,0}, 
		 {3,0,0}, 
		 {3,3,3}},
					},
	 //
	{
		{{0,4,0}, 
		 {0,4,4},   
		 {0,4,0}},
		 
		{{0,4,0}, 
		 {4,4,4},  
		 {0,0,0}},
		 
		{{0,4,0}, 
		 {4,4,0}, 
		 {0,4,0}},
		 
		{{0,0,0},  
		 {4,4,4}, 
		 {0,4,0}}
					},
	{
//	 ///
//		{{0,0,0,0}, 
//		 {0,1,1,0},   
//		 {0,1,1,0},   
//		 {0,0,0,0}}
//					},
		 ///
	 
		 {{5,5},   
		 {5,5}}
		 
					},
//	 

	///	 
	{
		{{0,6,0}, 
		 {0,6,6},   
		 {0,0,6}},
		 
		{{0,0,0}, 
		 {0,6,6},  
		 {6,6,0}}
				},
//	 
	{
		{{0,0,7}, 
		 {0,7,7}, 
		 {0,7,0}},
		 
		{{0,0,0}, 
		 {7,7,0}, 
		 {0,7,7}}
					}
	};
}
class Flag
{
	static final int WALL_TOUCH_LEFT = 2;
	static final int WALL_TOUCH_RIGHT = 4;
	static final int WALL_TOUCH_BOTTOM = 8;
	static final int BLOCK_TOUCH = 16;
	static final int NOT_TOUCH = 0;
}
class GameState
{
	static final int PLAY = 2;
	static final int STOP = 4;
	static final int END = 8;
	static final int READY = 10;
	
	public static String toString(int i)
	{
		switch(i)
		{
			case PLAY:
				return "PLAY";
			case STOP:
				return "STOP";
			case END:
				return "END";
			case READY:
				return "READY";
		}
		return "";
	}
}


