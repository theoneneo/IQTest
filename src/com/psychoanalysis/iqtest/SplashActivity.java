package com.psychoanalysis.iqtest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;

public class SplashActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.splash);
        
		Message msg = new Message();
		msg.what = 1;
		splashHandler.sendMessageDelayed(msg, 1000);
    }
    
	private Handler splashHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:{
					super.handleMessage(msg);
					Login();
					break;
				}
			}
		}
	};
	
	private void Login() 
	{
		splashHandler.removeMessages(1);
		Intent intent;
		intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		Login();
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		int action = event.getAction();
		switch(action) 
		{
			case MotionEvent.ACTION_DOWN:
				Login();
				break;
		}
		return(true);
	}
}