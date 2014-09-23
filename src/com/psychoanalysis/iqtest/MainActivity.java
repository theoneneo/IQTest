/**
 * 
 */
package com.psychoanalysis.iqtest;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import techtheme.metal.core.TestSet;
import techtheme.metal.db.BasedataElement;
import techtheme.metal.db.DatabaseListener;
import techtheme.metal.db.DatabaseType;

import com.psychoanalysis.view.AboutHelpView;
import com.psychoanalysis.view.BrowseSubjectView;
import com.psychoanalysis.view.DrawReferView;
import com.psychoanalysis.view.IQNumberView;
import com.psychoanalysis.view.MainView;
import com.psychoanalysis.view.MenuView;
import com.psychoanalysis.view.PanelSwitcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wiyun.game.DownloadBlob;
import com.wiyun.game.WiGame;
/**
 * @author Neo
 *
 */
public class MainActivity extends Activity implements OnClickListener, DatabaseListener{
    
	private static final int VIEW_ID_MAIN = 0;
	private static final int VIEW_ID_REFER = 1;
	private static final int VIEW_ID_SCAN = 2;
	private static final int VIEW_ID_IQ_NUMBER = 3;
	private static final int VIEW_ID_ABOUT_HELP = 4;
	private static final int VIEW_ID_MENU = 5;
	private MainView mainview;
	//private ReferView referview;
	private DrawReferView referview;
	private BrowseSubjectView browseview;
	private IQNumberView iqnumberview;
	private AboutHelpView aboutview;
	private MenuView menuview;
	private PanelSwitcher mPanelSwitcher;	
	private IQTestApplication app;
	private String iq_num = null;
	
	private int currentViewIdx = 0;

	private static final int MENU_SYSTEM = Menu.FIRST;
	private static final int MENU_HELP = MENU_SYSTEM + 1;
	private static final int MENU_ABOUT = MENU_HELP + 1;
	private static final int MENU_LOGOUT = MENU_ABOUT + 1;

	private PsyDatabaseAbility pda;//data base ability
	private TestSet ts;//Test lib logic set.
	private boolean isPause = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the platform database ability
        pda = new PsyDatabaseAbility(this);
        //Register platform db ability to TS.
        ts = TestSet.getInstance();
        ts.registerDBAbility(pda);
//        AdManager.setTestDevices(new String[]{AdManager.TEST_EMULATOR});
        app = IQTestApplication.getApplication(this);
        app.setMainHandler(MainHandler);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);       
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);  
        mPanelSwitcher = (PanelSwitcher)findViewById(R.id.panelswitcher);
        init();
    }
    
    private int checkVersion()
    {
    	int versionCode = 1;
    	try {
    		versionCode = getPackageManager().getPackageInfo("com.psychoanalysis.iqtest", 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionCode;
    }
    
    public void onResume()
    {
    	super.onResume();
    	if(!isPause)
    	{
    		app.getAvaliableApps();
    	}
    	
    }
    
    public void onPause()
    {
    	super.onPause();
    	isPause = true;
    }
    
    private void updateVersion()
    {
		if(app.getNewVersion() > checkVersion())
		{
	         new AlertDialog.Builder(MainActivity.this)
	         .setCancelable(false)
	         .setMessage(R.string.update_info)
	         .setIcon(android.R.drawable.ic_dialog_alert)
	         .setPositiveButton(R.string.wy_button_ok, new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int whichButton) 
	             {
					Uri uri = Uri.parse(app.downloadUrl);
					if(uri == null)
					{
					    return;
					}
					Intent returnIt = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(returnIt);
					Process.killProcess(Process.myPid());
	             }
	            
	         })
	         .setNegativeButton(R.string.wy_button_cancel, new DialogInterface.OnClickListener() {
	             public void onClick(DialogInterface dialog, int whichButton) {
	             }
	         })
	        .show();
		}
    }
    
    private void init()
    {
//    	mPanelSwitcher.removeAllViews();
//    	mPanelSwitcher.ResetPanelSwicher();
    	
        mainview = new MainView(this, this);//閿熸枻鎷烽敓鏂ゆ嫹绀洪敓鏂ゆ嫹閿熸枻鎷�
		mPanelSwitcher.addView(mainview);
        mPanelSwitcher.setViewInLayout(mainview, VIEW_ID_MAIN);
        
        referview = new DrawReferView(this);//閿熺粨浜ら敓鏂ゆ嫹閿熸枻鎷�
		mPanelSwitcher.addView(referview);
        mPanelSwitcher.setViewInLayout(referview, VIEW_ID_REFER);
        
        browseview = new BrowseSubjectView(this, this);//閿熸枻鎷烽敓鏂ゆ嫹涔滈敓锟�		mPanelSwitcher.addView(browseview);
        mPanelSwitcher.setViewInLayout(browseview, VIEW_ID_SCAN);
        
        iqnumberview = new IQNumberView(this);//閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鏂滈敓锟�		mPanelSwitcher.addView(iqnumberview);
        mPanelSwitcher.setViewInLayout(iqnumberview, VIEW_ID_IQ_NUMBER);
        
        aboutview = new AboutHelpView(this);//閿熸枻鎷烽敓鑺傚府鎷烽敓鏂ゆ嫹
		mPanelSwitcher.addView(aboutview);
        mPanelSwitcher.setViewInLayout(aboutview, VIEW_ID_ABOUT_HELP);
        
        menuview = new MenuView(this);//閿熷壙纰夋嫹閿熸枻鎷烽敓鏂ゆ嫹
		mPanelSwitcher.addView(menuview);
        mPanelSwitcher.setViewInLayout(menuview, VIEW_ID_MENU);
        
         
        //Start the timer.Then Main view will be notified the cost time, rest time and soon on.
        ts.addDbListener(DatabaseType.DB_TEST_PAGE_INDEX, mainview);
        ts.addDbListener(DatabaseType.DB_QUESTION_SELECTION, mainview);
        ts.addDbListener(DatabaseType.DB_ANSWER_SELECTION, mainview);
        ts.addDbListener(DatabaseType.DB_TIME_REST, mainview);
        ts.addDbListener(DatabaseType.DB_TEST_RESULT, referview.v);
        ts.addDbListener(DatabaseType.DB_TEST_RESULT, this);
        ts.restart();
        long preRestTime = ts.getRestTime();
		mainview.saveRestTime(preRestTime);
		Button btn_psy = (Button)findViewById(R.id.psy);
		btn_psy.setOnClickListener(this);
        WiGame.init(this, "ea777b3a5253bcde", "akvQxtkgNSjYmWFc9nJDaHddneWtzdfX"); 
    }
    
    public void onDestroy()
    {
    	super.onDestroy();
    	WiGame.destroy(this);
    }
    
    private void referSubject(int idx)
    {
        if(IQTestApplication.lockAnswerArea)
        {
            return;
        }
    	ts.confirm(idx);
    	if(mainview.isPause)
    	{
    		mainview.pauseOrContinueTest();
    	}
//    	currentSubject++;
//    	mainview.setSubject(currentSubject);
    }
    
    public void onClick(View v) {
    	if(v.getId() == R.id.btn1)
    	{
    		referSubject(0);
    	}
    	else if(v.getId() == R.id.btn2)
    	{
    		referSubject(1);
    		//
    	}
    	else if(v.getId() == R.id.btn3)
    	{
    		referSubject(2);
    	}
    	else if(v.getId() == R.id.btn4)
    	{
    		referSubject(3);
    	}
    	else if(v.getId() == R.id.btn5)
    	{
    		referSubject(4);
    	}
    	else if(v.getId() == R.id.btn6)
    	{
    		referSubject(5);
    	}
    	else if(v.getId() == R.id.btn7)
    	{
    		referSubject(6);
    	}
    	else if(v.getId() == R.id.btn8)
    	{
    		referSubject(7);
    	}
    	else if(v.getId() == R.id.submitScore)
    	{
    		WiGame.submitScore("16d3d2aaca8723b7", Integer.valueOf(iq_num), null, false);
    	}						
    	else if(v.getId() == R.id.btnSubmit)
    	{
        	mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
        	currentViewIdx = VIEW_ID_REFER;
        	switchToLeft(VIEW_ID_REFER);
    		ts.submit();
    	}
//    	else if(v.getId() == R.id.btnMenuScan)
//    	{
//    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
//        	currentViewIdx = VIEW_ID_SCAN;
//        	switchToLeft(VIEW_ID_SCAN);
//    	}
    	else if(v.getId() == R.id.btnScan)
    	{ 		
    		browseview.mAdapter.UpdateView();
        	currentViewIdx = VIEW_ID_SCAN;
        	mPanelSwitcher.enterFromUp(VIEW_ID_SCAN);
    	}
    	else if(v.getId() == R.id.btnRank)
    	{
    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
    		currentViewIdx = VIEW_ID_MAIN;
        	WiGame.openLeaderboard("16d3d2aaca8723b7");
    	}
    	else if(v.getId() == R.id.btnHelp)
    	{
    		aboutview.makeScrollview();
    		aboutview.data_title.setText(R.string.help_title);
    		aboutview.data_info.setText(R.string.help_info);
    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
        	currentViewIdx = VIEW_ID_ABOUT_HELP;
        	switchToLeft(VIEW_ID_ABOUT_HELP);
    	}
    	else if(v.getId() == R.id.btnAbout)
    	{
    		aboutview.makeScrollview();
    		aboutview.data_title.setText(R.string.about_title);
    		aboutview.data_info.setText(R.string.about_info);
    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
        	currentViewIdx = VIEW_ID_ABOUT_HELP;
        	switchToLeft(VIEW_ID_ABOUT_HELP);
    	}    	
    	else if(v.getId() == R.id.btnReleated)
    	{
    		iqnumberview.setPageName();
    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
        	currentViewIdx = VIEW_ID_IQ_NUMBER;
        	switchToLeft(VIEW_ID_IQ_NUMBER);
    	}
    	else if((v.getId() == R.id.btnExit) || (v.getId() == R.id.btnOut))
    	{
        	ts.savePageIndex();
            finish();
            Process.killProcess(Process.myPid());
    	}
    	else if(v.getId() == R.id.btnMenu)
    	{
    		currentViewIdx = VIEW_ID_MENU;
    		mPanelSwitcher.enterFromUp(VIEW_ID_MENU);
    	}
    	else if((v.getId() == R.id.btnDoubleStart))
    	{
    		mainview.pauseOrContinueTest();
    	}
    	else if(v.getId() == R.id.menu_layout)
    	{
    		return;
    	}
    	else if((v.getId() == R.id.over_btn)||(v.getId() == R.id.menu_btn))
    	{
    		mPanelSwitcher.outFromBottom(currentViewIdx);
    	}
    	else if(v.getId() == R.id.psy){
    		dialog();
//    		
    	}
//    	else if(v.getId() == R.id.btn)
//    	{
//    		ts.saveRestTime(mainview.restoreRestTime());
//    		ts.savePageIndex();
//    		
//    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
//    		currentViewIdx = VIEW_ID_MAIN;
//    	}
//    	else if(v.getId() == R.id.btnRead)
//    	{
//    		long preRestTime = ts.getRestTime();
//    		mainview.saveRestTime(preRestTime);  		
//    		int pageIndex =  ts.getPageIndex();
//    		ts.scan(pageIndex);
//    		
//    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
//    		currentViewIdx = VIEW_ID_MAIN;
//    	}
//    	else if(v.getId() == R.id.btnScan)
//    	{
//    		mPanelSwitcher.outFromBottom(VIEW_ID_MENU);
//        	currentViewIdx = VIEW_ID_SCAN;
//        	switchToLeft(VIEW_ID_SCAN);
//    	}    	
    }
    
    private void dialog(){
        Dialog unknowdialog = new AlertDialog.Builder(this).setTitle("推荐应用")
                .setMessage("心理云")
                .setCancelable(false)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	download();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
            unknowdialog.show();
    }
    
    private void download(){
    	String urlString = "http://bcs.duapp.com/psycloud/PsychologyCloud.apk?sign=MBO:710de0ba58eeb21ef0bbcd0d14fe5a40:%2F0Ff1jys5oc2x%2BNjkIRu6aERm6U%3D&response-content-disposition=attachment;filename*=utf8''PsychologyCloud.apk&response-cache-control=private";
    	Uri uri = Uri.parse(urlString); 
    	Intent intent =new Intent(Intent.ACTION_VIEW, uri);
    	startActivity(intent);
    }
    
    private void Logout()
    {
         new AlertDialog.Builder(MainActivity.this)
        .setCancelable(false)
        .setTitle(R.string.logout_title)
        .setMessage(R.string.logout_info)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setPositiveButton(R.string.wy_button_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) 
            {
            	ts.savePageIndex();
                finish();
                Process.killProcess(Process.myPid());
            }
           
        })
        .setNegativeButton(R.string.wy_button_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        })
       .show();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	if(keyCode == event.KEYCODE_MENU)
    	{
    		if(currentViewIdx == VIEW_ID_MAIN)
    		{
	    		currentViewIdx = VIEW_ID_MENU;
	    		mPanelSwitcher.enterFromUp(VIEW_ID_MENU);
	    		return true;
    		}
    	}   	
        return super.onKeyUp(keyCode, event);
    }
    	    
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) 
        {
        	if(currentViewIdx != VIEW_ID_MAIN)
        	{
        		if((currentViewIdx == VIEW_ID_MENU) || (currentViewIdx == VIEW_ID_SCAN))
        		{
        			mPanelSwitcher.outFromBottom(currentViewIdx);
        		}
        		else
        		{
        			switchToRight(currentViewIdx);
        			 IQTestApplication.lockAnswerArea = false;
        		}
            	currentViewIdx = VIEW_ID_MAIN;        	
        	}
            return true;
        }
        else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU)
        {
        	super.dispatchKeyEvent(event);
        	return true;
        }
		return super.dispatchKeyEvent(event);
    }
    
    public Handler getMainHandler()
    {
    	return MainHandler;
    }

    private Handler MainHandler = new Handler() 
    {    	
        @Override 
        public void handleMessage(Message msg) 
        { 
             switch (msg.what) 
             {
             	case EventListener.EVENT_GET_SELECT_SUBJECT:
             		super.handleMessage(msg);
             		mPanelSwitcher.outFromBottom(currentViewIdx);
             		//switchToRight(currentViewIdx);
             		currentViewIdx = VIEW_ID_MAIN; 
             		Integer index = (Integer)msg.obj;
             		ts.scan(index);
             		break;
             	case EventListener.EVENT_UPDATA_REFREASH_MAIN:
             	    super.handleMessage(msg);
             	    mainview.invalidate();
             	    break;
             	case EventListener.EVENT_UPDATA_IQ_NUM:
             		super.handleMessage(msg);
             		iq_num = String.valueOf(msg.obj);
             		referview.iqNum.setText(iq_num);
             		break;
             	case EventListener.EVENT_VERSION_UPDATE:
             		updateVersion();
             		break;
             	default:
             		break;
             }
             super.handleMessage(msg);             
        } 
    };    
    
    public void switchToLeft(int idxPanel) {
        //  <--
    	mPanelSwitcher.enterFromRightBorder(idxPanel);
    	mPanelSwitcher.outFromLeftBorder(VIEW_ID_MAIN);
    }
    
    public void switchToRight(int idxPanel) {
        //  -->
    	mPanelSwitcher.enterFromLeftBorder(VIEW_ID_MAIN);
    	mPanelSwitcher.outFromRightBorder(idxPanel);
    }

	@Override
	public void onUpdate(int dbType, int dbAct, BasedataElement dbe) {
		 switch (dbType)
	        {
	            case DatabaseType.DB_TEST_RESULT:
	                IQTestApplication.lockAnswerArea = true;
	            	currentViewIdx = VIEW_ID_REFER;
	            	switchToLeft(VIEW_ID_REFER);
	            	break;
	        }	
	}

	@Override
	public void onUpdate(int dbType, int dbAct, BasedataElement[] dbes) {
		
	}
}