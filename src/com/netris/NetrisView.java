package com.netris;

import java.io.InputStream;

import com.netris.MainView.animate;

import android.os.Bundle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class NetrisView extends MainView {


    class DownHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
        	if (getState() == GameState.END)
        	{
        		 this.removeMessages(0);
        		 invalidate();
        	}
        	else
        	{
        		updateDown(this);
        		
        		invalidate(); 
            }
        }
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
            
        }
        public void stop()
        {
        	this.removeMessages(0);
        }
    }
	    
    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
        	
        	if (getState() == GameState.END)
        	{
        		 this.removeMessages(0);
        		 
        	}
        	else
        	{
        		update(this);
        		
        		invalidate();
        	}
        }
        
        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
        public void stop()
        {
        	this.removeMessages(0);
        }
    }

	Typeface mFace;
	Bitmap tr ;
	Bitmap[] blockBitmap = new Bitmap[8];
	private Paint mPaint ;
	private boolean touchDrag = false;
	private final RefreshHandler mRedrawHandler = new RefreshHandler();
	private final DownHandler mDownHandler = new DownHandler();
	private int framerate = 1000/24;
	protected int count = 0;
	protected int count1 = 0;
	protected long delay1 = 500;
	private Bitmap leftBitmap ;
	private Bitmap rightBitmap ;
	private Bitmap downBitmap ;
	private boolean bshowguide = true;
	private PointF touchPastP = new PointF();
	
	public NetrisView(Context context)
	{
		super(context);
		
		InputStream is = context.getResources().openRawResource(R.drawable.left);
		leftBitmap = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.right);
		rightBitmap = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.down);
		downBitmap = BitmapFactory.decodeStream(is);
		
		is = context.getResources().openRawResource(R.drawable.block1);
		blockBitmap[1] = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.block2);
		blockBitmap[2] = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.block3);
		blockBitmap[3] = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.block4);
		blockBitmap[4] = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.block5);
		blockBitmap[5] = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.block6);
		blockBitmap[6] = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.block7);
		blockBitmap[7] = BitmapFactory.decodeStream(is);
		is = context.getResources().openRawResource(R.drawable.block0);
		blockBitmap[0] = BitmapFactory.decodeStream(is);
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/samplefont.ttf");
	}
	public int update(RefreshHandler handler) {
		updateBlocks();
	   handler.sleep(framerate);
	   
	   count++;
	   return 0;
	}
	
	public int updateDown(DownHandler handler)
	{
		delay1 = 100 + 1000 / getLevel();
		if (getState() == GameState.PLAY)
		{
			handler.sleep(delay1);
			if (downBlock())
			{
				createBlock();
			}
			count1++;
		}
		return 0;
	}
	

	public void hidePad()
	{
		this.bshowguide = false;
	}
	public void Start()
	{
		super.Start();
		this.mRedrawHandler.sleep(framerate);
		this.mDownHandler.sleep(delay1);
	}
	public void Stop()
	{
		super.Stop();
		this.mRedrawHandler.stop();
		this.mDownHandler.stop();
		//invalidate();
	}
	public void Resume()
	{
		super.Resume();
		this.mRedrawHandler.sleep(framerate);
		this.mDownHandler.sleep(delay1);
	}
	public void End()
	{
		super.End();
		this.mRedrawHandler.stop();
		this.mDownHandler.stop();
		invalidate();
	}
	public void drawNextBlock(Canvas canvas)
	{
		canvas.translate(210, 5);
		Rect rect = new Rect(0,0,100,100);
		canvas.clipRect(rect );
		
		mPaint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, 100, 100, mPaint);
		int xlength = br.block[Nextblock][0].length;
		int ylength = br.block[Nextblock][0][0].length;
		canvas.translate(100/2 - xlength * 20 / 2, 100/2 - ylength * 20 / 2);
		for (int i = 0 ; i < xlength ; i++)
		{
			for (int j = 0 ; j < ylength ; j++)
			{
				if (br.block[Nextblock][0][i][j] != 0)
				{
					Rect dst = new Rect( i * 20, j * 20 ,
							 i * 20 + 20, + j*20+20);
					canvas.drawBitmap(blockBitmap[br.block[Nextblock][0][i][j]], null, dst, mPaint);
				}
			}
		}
		
		canvas.restore();
	}
	/**
	 * @param g
	 */
	public void drawBlock(Canvas g)
	{
		if (this.select == -1)
			return;
		g.translate(5, 5);
		Rect rect = new Rect(0,0,20*MaxWidth , 20*MaxHeight);
		g.clipRect(rect );
		
		mPaint.setStrokeWidth(2);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(Color.WHITE);
		g.drawRect(0, 0, 20*10, 20*20, mPaint);
		Rect dst;
		for (int i =0 ; i < 10 ; i ++)
		{
			for (int j = 5 ; j < 25 ; j++)
			{
				dst = new Rect(i*20,(j-5)*20,i*20+ 19,(j-5)*20+19);
				
				if (nblock[i][j] != 0)
				{
					g.drawBitmap(blockBitmap[nblock[i][j]], null, dst, mPaint);
				}
			}
		}
		mPaint.setColor(Color.GRAY);
		int lengthX = br.block[select][shape].length;
		int lengthY;
		for (int i = 0 ; i < lengthX ; i++)
		{
			lengthY = br.block[select][shape][i].length;
			for (int j = 0 ; j < lengthY ; j++ )
			{
				if (br.block[select][shape][i][j] != 0)
				{
					dst = new Rect(pt.x*20 + i*20,pt.y*20 + (j-5)*20,pt.x*20 + i*20 + 20,pt.y*20 + (j-5)*20 + 20);
					g.drawBitmap(blockBitmap[br.block[select][shape][i][j]], null, dst, mPaint);
				}
			}
		}
		Paint xPaint = new Paint();
		xPaint.setAlpha(100);
		for (int i = 0 ; i < lengthX ; i++)
		{
			lengthY = br.block[select][shape][i].length;
			for (int j = 0 ; j < lengthY ; j++ )
			{
				if (br.block[select][shape][i][j] != 0)
				{
	
					dst = new Rect(fallPt.x*20 + i*20 , fallPt.y*20 + (j-5)*20 , fallPt.x*20 + i*20  + 20,  fallPt.y*20 + (j-5)*20 + 20);
					g.drawBitmap(blockBitmap[0],null,dst, xPaint);
		
				}
			}
		}
		
		if (vt.isEmpty() == false)
		{
			animate t = (animate) vt.firstElement();
			int temp[] = (int[]) t.getBlocks();
			int y = (int) t.getY();
			for (int i = 0 ; i < 10 ; i++)
			{
				dst = new Rect(i*20, y, i*20 + 20 , y+ 20);
				g.drawBitmap(blockBitmap[temp[i]],null,dst, mPaint);
			}
		}
		g.restore();
	}
	/* (non-Javadoc)
	 * @see com.netris.MainView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		mPaint.reset();
		super.onDraw(canvas);
		//canvas.restore();
		canvas.save();
		this.drawNextBlock(canvas);
		canvas.save();
		this.drawBlock(canvas);
	
		
		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Style.FILL_AND_STROKE);
		mPaint.setStyle(Style.FILL);
		mPaint.setTextSize(20);
	
		int width = this.getWidth();
	    int height = this.getHeight();
	    mPaint.setTypeface(mFace);
	    mPaint.setTextAlign(Align.CENTER);
	    canvas.drawText(GameState.toString(gameState) , 250, 190, mPaint);
	    mPaint.setTextAlign(Align.RIGHT);
	    canvas.drawText("SCORE"  , getWidth() - 10, 230, mPaint);
	    canvas.drawText(String.valueOf(scoreInt) , getWidth() - 10, 260, mPaint);
	    canvas.drawText("LEVEL"  , getWidth() - 10, 300, mPaint);
	    canvas.drawText(String.valueOf(this.levelInt)  ,  getWidth() - 10, 330, mPaint);
	    
	   // 
		//Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.WHITE);
		mPaint.setShadowLayer(10, 1, 1, Color.BLUE);
		mPaint.setAlpha(150);
		
		if (bshowguide)
		{
		
			
		//	Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setAlpha(50);
			canvas.drawBitmap(leftBitmap, 0 , height/ 2, mPaint);
	        canvas.drawBitmap(rightBitmap, width - rightBitmap.getWidth()  , height/ 2, mPaint);
	        canvas.drawBitmap(downBitmap , width / 2 - downBitmap.getWidth() / 2 , height * 3/4, mPaint);
	       
			
		}
		if (this.getState() == GameState.READY)
		{		
	
		
		//canvas.drawRect(0, 0, width, height, mPaint);
			int textSize = 40;
	// mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.RED);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(textSize);
			mPaint.setAlpha(150);
			
			
			
	//		int width = getWidth();
	//		int height = getHeight();
			//canvas.drawRect(0, 0, width, height, mPaint);
			
			String text = "Touch Screen";
			canvas.drawText(text, width / 2, height / 2 - textSize, mPaint);
			text = "Play Game";
			canvas.drawText(text, width / 2, height / 2 , mPaint);
			
			
			mPaint.setColor(Color.WHITE);
			mPaint.setTextSize(15);
			text = "Drag - Rotate Block";
			canvas.drawText(text, width / 2, height / 2 + textSize, mPaint);
		}
		if (this.getState() == GameState.STOP)
		{
			int textSize = 40;
	//		Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.RED);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(textSize);
			mPaint.setAlpha(150);
			
			
			
	//		int width = getWidth();
	//		int height = getHeight();
			//canvas.drawRect(0, 0, width, height, mPaint);
			
			String text = "Touch Screen";
			canvas.drawText(text, width / 2, height / 2 - textSize, mPaint);
			text = "Resume Game";
			canvas.drawText(text, width / 2, height / 2 , mPaint);
			
		}
		if (this.getState() == GameState.END)
		{
			int textSize = 40;
	//		Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.RED);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(textSize);
			mPaint.setAlpha(150);
			
			
			
	//		int width = getWidth();
	//		int height = getHeight();
			//canvas.drawRect(0, 0, width, height, mPaint);
			
			String text = "Touch Screen";
			canvas.drawText(text, width / 2, height / 2 - textSize, mPaint);
			text = "Restart Game";
			canvas.drawText(text, width / 2, height / 2 , mPaint);
			
			
			mPaint.setColor(Color.WHITE);
			mPaint.setTextSize(15);
			text = "Drag - Rotate Block";
			canvas.drawText(text, width / 2, height / 2 + textSize, mPaint);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.netris.MainView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if (getState() == GameState.READY || getState() == GameState.END)
			{
				this.Start();
				return true;
			}
			if (getState() == GameState.STOP)
			{
				this.Resume();
				return true;
			}
			return true;
		}
		float x = event.getX();
	    float y = event.getY();
	    
	    if (gameState == GameState.PLAY)
	    {
	    	int width = this.getWidth();
	        int height = this.getHeight();
			switch (event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					touchPastP.x = x;
					touchPastP.y = y;
	
					return true;
				case MotionEvent.ACTION_MOVE:
					
					float tempx = Math.abs(touchPastP.x - x);
					float tempy = Math.abs(touchPastP.y - y);
					if (tempx + tempy > 50)
					{
						touchDrag = true;
					}
					return true;
				case MotionEvent.ACTION_UP:
					if (touchDrag)
					{
						rotate();
					}
					else
					{
						if ( (height * 3/4) < y)
						{
							fallBlock();
							createBlock();
						}
						else
						{
							if (width / 2 > x)
								moveBlock(-1, 0);
							else
								moveBlock(+1, 0);
						}
					}
					touchDrag = false;
					
					return true;
				default:		
			}
	    }
	    return super.onTouchEvent(event);
	}
	public boolean onKeyDown(int keyCode, KeyEvent msg) {
	
	    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
	    	rotate();
	        return (true);
	    }
	
	    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
	    	downBlock();
	        return (true);
	    }
	
	    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
	    	moveBlock(-1, 0);
	        return (true);
	    }
	
	    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
	    	moveBlock(+1, 0);
	        return (true);
	    }
	
	    return super.onKeyDown(keyCode, msg);
	}
}