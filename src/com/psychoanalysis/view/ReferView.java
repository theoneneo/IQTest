package com.psychoanalysis.view;

import java.util.Timer;
import java.util.TimerTask;

import techtheme.metal.db.BasedataElement;
import techtheme.metal.db.DatabaseListener;
import techtheme.metal.db.DatabaseType;
import techtheme.metal.db.concret.AnswerSelection;
import techtheme.metal.db.concret.QuestionSelection;
import techtheme.metal.db.concret.TestPageIndex;
import techtheme.metal.db.concret.TestResult;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psychoanalysis.iqtest.EventListener;
import com.psychoanalysis.iqtest.IQTestApplication;
import com.psychoanalysis.iqtest.R;
/**
 * @author Neo
 *
 */
public class ReferView extends View implements DatabaseListener{
	Context context;
	LinearLayout view;
	TextView score;
	private IQTestApplication app;
	ImageView indicator;
    private BitmapDrawable iqIndicator;
    private BitmapDrawable iqCover;
    private int leftX, destX;
	int iq;
	Paint paint;
	Bitmap bmp;
	Bitmap resizedBmp = null;
	Bitmap coverBmp = null;
	int canvasW ;
	int canvasH ;
	int percent;
	/**
	 * @param context
	 */
	public ReferView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		app = IQTestApplication.getApplication();
		init();
	}
	
	public void onMeasure(int width, int height)
	{
		super.onMeasure(width, height);
		
	}
	
	private void init()
	{ 
//		LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
		
//		view = (LinearLayout)inflate.inflate(R.layout.refer, null);
//		addView(view); 
		paint = new Paint(); 
		paint.setColor(Color.BLACK); 
		paint.setTextSize(30);	
		
		Typeface font = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
		paint.setTypeface(font); 
		iqIndicator = (BitmapDrawable)getResources().getDrawable(R.drawable.tianchong);
		bmp = iqIndicator.getBitmap(); 
//		int bmpWidth=bmp.getWidth();
//	    int bmpHeight=bmp.getHeight();
//	    float scaleX = 320.0f / bmpWidth;
//	    float scaleY = 480.0f / bmpHeight;
//	    /* ����reSize���Bitmap���� */
//	    Matrix matrix = new Matrix();  
//	    matrix.postScale(scaleX, scaleY); 
//	    resizedBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
		iqCover = (BitmapDrawable)getResources().getDrawable(R.drawable.jieguo);
		leftX = -bmp.getWidth();
	}
	
	@Override
	public void onDraw(Canvas c) 
	{
//		super.onDraw(c);
		canvasW = c.getWidth();
		canvasH = c.getHeight();
		
		if(resizedBmp == null && bmp != null)
		{
			int bmpWidth=bmp.getWidth();
		    int bmpHeight=bmp.getHeight();
		    float scaleX = (float)(1.0 * canvasW) / bmpWidth;
		    float scaleY = (float)(1.0 * canvasH) / bmpHeight;
		    Matrix matrix = new Matrix();  
		    matrix.postScale(scaleX, scaleY); 
		    resizedBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
		    
		    Bitmap cover = iqCover.getBitmap();
		    int coverBmpWidth = cover.getWidth();
		    int coverBmpHeight = cover.getHeight();
		    float coverScaleX = (float)(1.0 * canvasW) / coverBmpWidth;
		    float coverScaleY = (float)(1.0 * canvasH) / coverBmpHeight;
		    Matrix coverMatrix = new Matrix();  
		    matrix.postScale(coverScaleX, coverScaleY); 
		    coverBmp = Bitmap.createBitmap(iqCover.getBitmap(), 0, 0, coverBmpWidth, coverBmpHeight, coverMatrix, true);
		}
		
		if(bmp != null && coverBmp != null)
		{
			c.drawBitmap(resizedBmp, leftX, 0, null);
			//c.drawBitmap(coverBmp, 0, 0, paint);
		}

//		float x = (float)(canvasW / 320.0) * 210;
//		float y = (float)(canvasH / 480.0) * 55;
//		c.drawText("" + iq, x, y, paint);
//		System.out.println("text x is :" + x + " y is :" + y);
	}

	@Override
	public void onUpdate(int dbType, int dbAct, BasedataElement dbe) {
		 switch (dbType)
	        {
	            case DatabaseType.DB_TEST_RESULT:
	            	leftX = -480;//-canvasW;
	                percent = ((TestResult)dbe).getDispPercent();
	                iq = ((TestResult)dbe).getIq();
	                
	                app.eventAction(EventListener.EVENT_UPDATA_IQ_NUM, iq);
	                
	                System.out.println("iq is: " + iq);
	                System.out.println("left x is :" + leftX);
	                System.out.println("dest x is :" + destX);
	                new Timer().schedule(new TimerTask() 
	                {
	        			public void run() 
	        			{
	        				System.out.println("in timer left x is :" + leftX);
	        				leftX += 20;
	        				postInvalidate();
	        				destX = -((100 - percent) * canvasW) / 100;
	        				if(leftX > destX)
	        				{
	        					cancel();
	        				}
	        			}
	        		}, 0, 200);
	                
	                break;
	        }
	        invalidate();
	}

	@Override
	public void onUpdate(int dbType, int dbAct, BasedataElement[] dbes) {
		
	}
	
	   
}
