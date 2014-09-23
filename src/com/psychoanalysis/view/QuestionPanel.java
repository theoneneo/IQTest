package com.psychoanalysis.view;

import com.psychoanalysis.comm.Utility;
import com.psychoanalysis.iqtest.IQTestApplication;
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
import android.widget.ImageView;

public class QuestionPanel extends ImageView
{
	public static  float scale = -1;
	private static final int QUESTION_PANEL_WIDTH = 320;
	private static final int QUESTION_PANEL_HEIGHT = 200;
	private static final int QUESTION_PANEL_COL_COUNT = 4;
	private static final int QUESTION_PANEL_ROW_COUNT = 4;
	private static final int QUEESTION_PANEL_SELETION_COUNT = 8;
	private static final int MARGIN_INNER_TOP = 10;
	private static final int MARGIN_INNER_BOTTOM = 10;
	private static final int MARGIN_INNER_LEFT = 20;
	private static final int MARGIN_INNER_RIGHT = 20;
	private static final int SELECTION_WIDTH = 78;
	private static final int SELECTION_HEIGHT = 57;
	private static IQTestApplication app;
	private Bitmap quesBmp;
    Bitmap resizedBmp = null;
	private int[] ques;
	
	public QuestionPanel(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		app = IQTestApplication.getApplication();
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		if(quesBmp != null && ques != null)
		{
			int rowCount = QUESTION_PANEL_ROW_COUNT - 1; int colCount = QUESTION_PANEL_COL_COUNT - 1;
			int marginOuterPercentX = 10; int marginInnerPercentX = 15; int marginOuterPercentY = 10;
			int[] cellIndexs = ques;
			int cellW, cellH;
	        int canvasW = canvas.getWidth();
	        int canvasH = canvas.getHeight();
	        int marginOuterX = (marginOuterPercentX * canvasW) / 100;
	        int leftMarginOuterX = marginOuterX / 2;
	        int offsetX;
	        offsetX = cellW = ((100 - marginInnerPercentX - marginOuterPercentX) * canvasW) / (100 * 3);
	        int destW = cellW * QUESTION_PANEL_COL_COUNT; 
	        if(resizedBmp == null)
	        {
	            Matrix matrix = new Matrix();
	            scale = ((float)destW) / quesBmp.getWidth();
	            scale *= 0.9;
	            System.out.println("scalse is :" + scale);
	            matrix.postScale(scale, scale); 
	            
	            resizedBmp = Bitmap.createBitmap(quesBmp, 0, 0, quesBmp.getWidth(), quesBmp.getHeight(), matrix, true);
	        }       
	        
	        int stepX = (marginInnerPercentX * canvasW) / (100 * colCount);
	        int stepY = (marginOuterPercentY * canvasH) / (200 * rowCount);
	        
	        cellW = resizedBmp.getWidth() / 4;
	        cellH = (int)(cellW * (57.0 / 78)); 
//	        System.out.println("will draw -------------------");
	        for(int i = 0; i < QUESTION_PANEL_ROW_COUNT - 1; i++)
	        {
	            for(int j = 0; j < colCount; j++)
	            {
	                if(i * rowCount + j == (rowCount * colCount - 1)) 
	                {
	                    return;
	                }
	                Utility.drawCell(canvas, resizedBmp, QUESTION_PANEL_ROW_COUNT, leftMarginOuterX + (stepX + offsetX) * j, (stepY + cellH) * i, cellIndexs[i * rowCount + j], cellW, cellH);
	            }
	        }
		}
	}

	public void onUpdate(int dbType, int dbAct, BasedataElement dbe) {
		
    	int pageIndex;
        switch (dbType)
        {
            case DatabaseType.DB_QUESTION_SELECTION:
            	ques = ((QuestionSelection)dbe).getSelection();
            	pageIndex = ((QuestionSelection)dbe).getPageIndex();
            	quesBmp =  BitmapFactory.decodeResource(getResources(), IQUtil.getTestPagePicResouceId(pageIndex));
            	resizedBmp = null;
//            	int bmpWidth = resizedBmp.getWidth();
//                int bmpHeight = resizedBmp.getHeight();
//                Matrix matrix=new Matrix();
//                float w = (float)bmpWidth / 3;
//                float h = (float)bmpHeight / 3;
//                matrix.postScale(w, h);
//                quesBmp = Bitmap.createBitmap(resizedBmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
            	invalidate();
                break;
        }
	}
}
