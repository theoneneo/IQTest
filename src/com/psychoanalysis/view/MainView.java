package com.psychoanalysis.view;

import java.util.Timer;
import java.util.TimerTask;

import techtheme.metal.ability.DatabaseAbility;
import techtheme.metal.com.Common;
import techtheme.metal.core.TestSet;
import techtheme.metal.db.BasedataElement;
import techtheme.metal.db.DatabaseListener;
import techtheme.metal.db.DatabaseType;
import techtheme.metal.db.concret.AnswerSelection;
import techtheme.metal.db.concret.QuestionSelection;
import techtheme.metal.db.concret.TestPageIndex;
import techtheme.metal.db.concret.TimeCost;
import techtheme.metal.db.concret.TimeRest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psychoanalysis.comm.Utility;
import com.psychoanalysis.iqtest.EventListener;
import com.psychoanalysis.iqtest.IQTestApplication;
import com.psychoanalysis.iqtest.IQUtil;
import com.psychoanalysis.iqtest.MainActivity;
import com.psychoanalysis.iqtest.R;
/**
 * @author Neo
 *
 */
public class MainView extends LinearLayout implements DatabaseListener{

	/**
	 * @param context
	 */
    IQTestApplication app;
	Context context;
	RelativeLayout view;	
	TextView timer;
	String sTimer;
	String sTimerSplit;
	AnswerPanel btn1;
	AnswerPanel btn2;
	AnswerPanel btn3;
	AnswerPanel btn4;
	AnswerPanel btn5;
	AnswerPanel btn6;
	AnswerPanel btn7;
	AnswerPanel btn8;
	RelativeLayout btnMenu;
	//ImageView subject;
	
	private MainActivity activity;
	private long ticker;
    private long costTime;
    private long restTime;
    private Timer timeTicker;
    private TestSet ts;
    public QuestionPanel qp;
    public boolean isPause = false;
    
	public MainView(Context context, MainActivity activity) {
		super(context);
		this.context = context;
		this.activity = activity;
		app = IQTestApplication.getApplication();
		// TODO Auto-generated constructor stub
		init();
		ts = TestSet.getInstance();
	}
	
	private void init(){
        LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        view = (RelativeLayout) inflate.inflate(R.layout.mainview, null);
        addView(view);
        loadResourceId();
        timeTicker = new Timer();
        timeTicker.schedule(new TimerTask()
        {
            public void run()
            {
                refresh();
            }
        }, 0, Common.FLASH_INTEVERVAL);
        timer = (TextView)findViewById(R.id.timer);
        
//		TestSet.getInstance().addDbListener(DatabaseType.DB_QUESTION_SELECTION, qp);        
//        qp = new QuestionPanel(getContext());
	}
	
	private void loadResourceId()
	{
    	btn1 = (AnswerPanel)view.findViewById(R.id.btn1);
    	btn1.setOnClickListener((OnClickListener)context);
    	btn2 = (AnswerPanel)view.findViewById(R.id.btn2);
    	btn2.setOnClickListener((OnClickListener)context);
    	btn3 = (AnswerPanel)view.findViewById(R.id.btn3);
    	btn3.setOnClickListener((OnClickListener)context);
    	btn4 = (AnswerPanel)view.findViewById(R.id.btn4);
    	btn4.setOnClickListener((OnClickListener)context);
    	btn5 = (AnswerPanel)view.findViewById(R.id.btn5);
    	btn5.setOnClickListener((OnClickListener)context);
    	btn6 = (AnswerPanel)view.findViewById(R.id.btn6);
    	btn6.setOnClickListener((OnClickListener)context);
    	btn7 = (AnswerPanel)view.findViewById(R.id.btn7);
    	btn7.setOnClickListener((OnClickListener)context);
    	btn8 = (AnswerPanel)view.findViewById(R.id.btn8);
    	btn8.setOnClickListener((OnClickListener)context);	
    	
//    	ImageButton btnStart = (ImageButton)view.findViewById(R.id.btnStart);
//    	btnStart.setOnClickListener((OnClickListener)context);
    	ImageButton btnSub = (ImageButton)view.findViewById(R.id.btnScan);
    	btnSub.setOnClickListener((OnClickListener)context);
    	ImageButton btnmenu = (ImageButton)view.findViewById(R.id.btnMenu);
    	btnmenu.setOnClickListener((OnClickListener)context);
    	ImageButton btnOut = (ImageButton)view.findViewById(R.id.btnOut);
    	btnOut.setOnClickListener((OnClickListener)context);
    	
    	btnMenu = (RelativeLayout)view.findViewById(R.id.Menu);
    	
    	ImageButton btnDoubleStart = (ImageButton)view.findViewById(R.id.btnDoubleStart);
    	btnDoubleStart.setOnClickListener((OnClickListener)context);
//    	if(app.getScreenScale() == 0.75)
//    	{
//    		btnMenu.setVisibility(View.GONE);
//    	}
//    	else
//    	{
//    		btnDoubleStart.setVisibility(View.GONE);
//    	}
    	qp = (QuestionPanel)view.findViewById(R.id.iqsubject); 
    	//subject = (ImageView)view.findViewById(R.id.iqsubject);
	}

    public void onUpdate(int dbType, int dbAct, BasedataElement dbe)
    {
    	int pageIndex;
        switch (dbType)
        {
            case DatabaseType.DB_TEST_PAGE_INDEX:
            	int quesIndex = ((TestPageIndex)dbe).getTestPageIndex();
//            	System.out.println("@@@@Got question page index :" + quesIndex);
            	//setSubject(quesIndex);
            	break;
            case DatabaseType.DB_TIME_REST:
            	long restTime = ((TimeRest)dbe).getValue();
//            	System.out.println("@@@@restTime :" + restTime);
            	saveRestTime(restTime);
            	break;
            case DatabaseType.DB_QUESTION_SELECTION:
            	int[] ques = ((QuestionSelection)dbe).getSelection();
            	pageIndex = ((QuestionSelection)dbe).getPageIndex();
            	fillQuestion(ques, pageIndex);
            	qp.onUpdate(dbType, dbAct, dbe);
                break;
            case DatabaseType.DB_ANSWER_SELECTION:
            	int[] answers = ((AnswerSelection)dbe).getSelection();
            	pageIndex = ((AnswerSelection)dbe).getPageIndex();
            	fillAnswer(answers, pageIndex);
            	break;
            case DatabaseType.DB_ANSWER_SHEET:
            	break;
            case DatabaseType.DB_TEST_RESULT:
            	break;
        }
        invalidate();
    }

	public void onUpdate(int arg0, int arg1, BasedataElement[] arg2) {
		//Don't need fill in it now.
	}
	
	private void fillQuestion(int[] quesPicIndex, int pageIndex)
	{
		for(int i = 0; i < quesPicIndex.length; i++)
		{
//			System.out.println("----Got question pic index :" + quesPicIndex[i]);
		}
	}
	
	private void fillAnswer(int[] answerPicIndex, int pageIndex)
	{
		Bitmap bmp =  BitmapFactory.decodeResource(getResources(), IQUtil.getTestPagePicResouceId(pageIndex));
		for(int i = 0; i < answerPicIndex.length; i++)
		{
//			System.out.println("####Got answer pic index :" + answerPicIndex[i]);
		}
		btn1.setResource(bmp, answerPicIndex);
		btn2.setResource(bmp, answerPicIndex);
		btn3.setResource(bmp, answerPicIndex);
		btn4.setResource(bmp, answerPicIndex);
		btn5.setResource(bmp, answerPicIndex);
		btn6.setResource(bmp, answerPicIndex);
		btn7.setResource(bmp, answerPicIndex);
		btn8.setResource(bmp, answerPicIndex);
	}
	
	 /**
     * ��ʱˢ���߼������˴���ʱ�䣬�Զ����ύ������
     */

    private void refresh()
    {
        activity.runOnUiThread(new Runnable() { 
             public void run() { 
                 // timeTip = minutes + ":" + seconds;// "(" + (_testIndex + 1) + "/" + Common.TEST_COUNT + ")" + "    " + minutes + ":" + seconds;
                 ticker++;
                 costTime = ticker;
                 restTime = Common.TIME_LIMIT - costTime;
                 if(restTime <= 0)
                 {
                     timeTicker.cancel();
                     TestSet.getInstance().submit();
                 }
                 else
                 {
                   long minute = restTime / 60;
                   long second = restTime % 60;
                   if(second < 10)
                   {
                       sTimerSplit = ":0";
                   }
                   else
                   {
                       sTimerSplit = ":";
                   }
                   sTimer = minute + sTimerSplit + second;
                   timer.setText(sTimer);
                   ts.saveRestTime(restTime);
                 }
             } 
         });
    }
    
    @Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		qp.onDraw(canvas);
	}
    
    public long restoreRestTime()
    {
    	return Common.TIME_LIMIT - ticker;
    }
    
    public void saveRestTime(long restTime)
    {
    	ticker = Common.TIME_LIMIT - restTime;
    }
    
    public void pauseOrContinueTest()
    {	
    	isPause = !isPause;
		if(isPause)
		{
			timeTicker.cancel();
			//���ɰ棬�ڱ���Ŀ
		}
		else 
		{
			continueTest();
		}
    }
    
    private void continueTest()
    {
    	timeTicker = new Timer();
        timeTicker.schedule(new TimerTask()
        {
            public void run()
            {
                refresh();
            }
        }, 0, Common.FLASH_INTEVERVAL);
    }
}
