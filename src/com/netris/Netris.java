package com.netris;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class Netris extends Activity {
    /** Called when the activity is first created. */
	
	 private NetrisView mView;

	public static final String vibrateBundleKey = "NETIS-VIBRATE";

	public final VibHandler vibhandler = new VibHandler();

	class VibHandler extends Handler{
		public void handleMessage(Message msg) {
			if (msg.what == 1)
				System.out.println("msg");
        	Bundle b = msg.getData();
        	long length = b.getLong(vibrateBundleKey);
        	//length = msg.arg1;
        	Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        	vibrator.vibrate(length);
        	System.out.println(length);
        }
		public void PutMassage()
		{
			Bundle b = new Bundle();
			Message msg1 = new Message();
        	b.putLong(vibrateBundleKey, 300);
        	msg1.setData(b);
			sendMessage(msg1);
		}	
	}
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        mView = new NetrisView(this);
        //setContentView(R.layout.main);
        
        //mView = (NetrisView) findViewById(R.id.netris);
        setContentView(mView);
        
        
    }
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mView.End();
		super.onDestroy();
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	private static final int START_MENU_ID = Menu.FIRST;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	    //menu.add(0, START_MENU_ID, 0, "START");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
		return super.onCreateOptionsMenu(menu);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case START_MENU_ID:
		case R.id.start:
			this.mView.Start();
			return true;
		case R.id.hidepad:
			mView.hidePad();
			return true;
		case R.id.stop:
			mView.Stop();
			mView.invalidate();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mView.Stop();
		super.onPause();
	}
	public Netris() {
		super();
		// TODO Auto-generated constructor stub
	}
}