package com.psychoanalysis.view;

import techtheme.metal.db.BasedataElement;
import techtheme.metal.db.DatabaseListener;
import techtheme.metal.db.DatabaseType;
import techtheme.metal.db.concret.TestResult;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.psychoanalysis.iqtest.R;
/**
 * @author Neo
 *
 */
public class DrawRefer extends View implements DatabaseListener{
    ImageView indicator;
    private BitmapDrawable iqIndicator;
    private BitmapDrawable iqCover;
    private int leftX;
	Context context;
	LinearLayout view;
	int iq;
	Paint paint;
	/**
	 * @param context
	 */
	public DrawRefer(Context context, AttributeSet attrs) {
		super(context);
		this.context = context;
		init();
	}
	
	private void init()
	{ 
		paint = new Paint(); 
		paint.setColor(Color.BLACK); 
		paint.setTextSize(30);	
		
		Typeface font = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
		paint.setTypeface(font); 
		iqIndicator = (BitmapDrawable)getResources().getDrawable(R.drawable.tianchong);
		iqCover = (BitmapDrawable)getResources().getDrawable(R.drawable.jieguo);
	}
	
	public void onDraw(Canvas c) 
	{
		c.drawBitmap(((BitmapDrawable)iqIndicator).getBitmap(), leftX, 0, null);
		c.drawBitmap(((BitmapDrawable)iqCover).getBitmap(), 0, 0, null);
		c.drawText("" + iq, 200, 55, paint);
	}

    public void onUpdate(int dbType, int dbAct, BasedataElement dbe)
    {
        switch (dbType)
        {
            case DatabaseType.DB_TEST_RESULT:
                int percent = ((TestResult)dbe).getDispPercent();
                iq = ((TestResult)dbe).getIq();
                leftX = -((100 - percent) * 320) / 100;
                System.out.println("iq is: " + iq);
                System.out.println("left x is :" + leftX);
                invalidate();
                break;
        }
    }

	public void onUpdate(int arg0, int arg1, BasedataElement[] arg2) {
		// TODO Auto-generated method stub
		
	}
}
