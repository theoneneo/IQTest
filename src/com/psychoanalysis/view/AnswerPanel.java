package com.psychoanalysis.view;

import com.psychoanalysis.comm.Utility;
import com.psychoanalysis.iqtest.IQUtil;
import com.psychoanalysis.iqtest.R;

import techtheme.metal.core.TestSet;
import techtheme.metal.db.BasedataElement;
import techtheme.metal.db.DatabaseListener;
import techtheme.metal.db.DatabaseType;
import techtheme.metal.db.concret.AnswerSelection;
import techtheme.metal.db.concret.QuestionSelection;
import techtheme.metal.db.concret.TestPageIndex;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AnswerPanel extends ImageButton
{
	private static final int QUESTION_PANEL_COL_COUNT = 4;
	private Bitmap answerBmp;
	private static Bitmap resizedBmp = null;
	private int[] answers;
	private static int cellW, cellH;
	public AnswerPanel(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
//		quesBmp =  BitmapFactory.decodeResource(getResources(), IQUtil.getTestPagePicResouceId(pageIndex));
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);

		if(resizedBmp == null)
		{
			int colCount = QUESTION_PANEL_COL_COUNT - 1;
			int marginOuterPercentX = 10; int marginInnerPercentX = 15; 
			
	        int canvasW = canvas.getWidth();
	        cellW = ((100 - marginInnerPercentX - marginOuterPercentX) * canvasW) / (100 * 3);
            int destW = cellW * QUESTION_PANEL_COL_COUNT; 
	        Matrix matrix = new Matrix();
            float scale = ((float)destW) / answerBmp.getWidth();
            scale *= 0.9;
            System.out.println("scalse is :" + scale);
            matrix.postScale(scale, scale); 
            
            resizedBmp = Bitmap.createBitmap(answerBmp, 0, 0, answerBmp.getWidth(), answerBmp.getHeight(), matrix, false);      
	        
	        cellW = resizedBmp.getWidth() / 4;
	        cellH = (int)(cellW * (57.0 / 78)); 
		}
		
		String str = String.valueOf(this.getId());
		if(str.equals(String.valueOf(R.id.btn1)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[0], cellW, cellH);
		}
		else if(str.equals(String.valueOf(R.id.btn2)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[1], cellW, cellH);
		}
		else if(str.equals(String.valueOf(R.id.btn3)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[2], cellW, cellH);
		}
		else if(str.equals(String.valueOf(R.id.btn4)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[3], cellW, cellH);
		}
		else if(str.equals(String.valueOf(R.id.btn5)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[4], cellW, cellH);
		}
		else if(str.equals(String.valueOf(R.id.btn6)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[5], cellW, cellH);
		}
		else if(str.equals(String.valueOf(R.id.btn7)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[6], cellW, cellH);
		}
		else if(str.equals(String.valueOf(R.id.btn8)))
		{
			Utility.drawCell(canvas, resizedBmp, -2, 2, answers[7], cellW, cellH);
		}
	}
	
	public void setResource(Bitmap bmp, int[] answers)
	{
		answerBmp = bmp;
		resizedBmp = null;
		this.answers = answers;
	}
}
