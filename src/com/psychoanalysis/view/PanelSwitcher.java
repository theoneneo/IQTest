/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.psychoanalysis.view;

import java.util.ArrayList;
import java.util.List;

import android.view.animation.TranslateAnimation;
import android.view.View;
import android.widget.FrameLayout;
import android.content.Context;
import android.util.AttributeSet;

public class PanelSwitcher extends FrameLayout {
    private static final int ANIM_DURATION = 600;
    private int idxPanel = 0;
    private ArrayList<View> children;
    private int mWidth, mHeight;
    private TranslateAnimation inLeft;
    private TranslateAnimation outLeft;
    private TranslateAnimation inRight;
    private TranslateAnimation outRight;
    private TranslateAnimation inUp;
    private TranslateAnimation outDown;    
    private int mCurrentMaxViewNum = 0;
    
    public PanelSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        children = new ArrayList<View>();
    } 
    
    public void ResetPanelSwicher()
    {
    	children.clear();
    	mCurrentMaxViewNum=0;
    }
    
    public void setViewInLayout(View view, int index)
    {
    	children.add(index, view);
    	view.setVisibility(View.GONE);
    	if(index == 0)
    	{
    		view.setVisibility(View.VISIBLE);
    	}
    	mCurrentMaxViewNum++;
    }

    @Override 
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        mWidth = w;
        mHeight = h;
        
        inLeft   = new TranslateAnimation(mWidth, 0, 0, 0);
        outLeft  = new TranslateAnimation(0, -mWidth, 0, 0);        
        inRight  = new TranslateAnimation(-mWidth, 0, 0, 0);
        outRight = new TranslateAnimation(0, mWidth, 0, 0);

        inLeft.setDuration(ANIM_DURATION);
        outLeft.setDuration(ANIM_DURATION);
        inRight.setDuration(ANIM_DURATION);
        outRight.setDuration(ANIM_DURATION);
        
        inUp = new TranslateAnimation(0, 0, mHeight, 0);
        outDown = new TranslateAnimation(0, 0, 0, mHeight);
        
        inUp.setDuration(ANIM_DURATION);
        outDown.setDuration(ANIM_DURATION);
    }
    
    public void enterFromRightBorder(int idxPanel)
    {
    	if(children.get(idxPanel) == null) 
    	{
    		return;
    	}
    	
    	this.idxPanel = idxPanel;
    	children.get(idxPanel).setVisibility(View.VISIBLE);
    	children.get(idxPanel).startAnimation(inLeft);
    }
    
    public void enterFromLeftBorder(int idxPanel)
    {
    	if(children.get(idxPanel) == null) 
    	{
    		return;
    	}
    	
    	this.idxPanel = idxPanel;
    	children.get(idxPanel).setVisibility(View.VISIBLE);
    	children.get(idxPanel).startAnimation(inRight);
    }
    
    public void enterFromUp(int idxPanel)
    {
    	if(children.get(idxPanel) == null) 
    	{
    		return;
    	}
    	
    	this.idxPanel = idxPanel;
    	children.get(idxPanel).setVisibility(View.VISIBLE);
    	children.get(idxPanel).startAnimation(inUp);
    }
    
    public void outFromLeftBorder(int idxPanel)
    {
    	if(children.get(idxPanel) == null) 
    	{
    		return;
    	}
    	
    	this.idxPanel = idxPanel;
    	children.get(idxPanel).setVisibility(View.GONE);
    	children.get(idxPanel).startAnimation(outLeft);
    }
    
    public void outFromRightBorder(int idxPanel)
    {
    	if(children.get(idxPanel) == null) 
    	{
    		return;
    	}
    	
    	this.idxPanel = idxPanel;
    	children.get(idxPanel).setVisibility(View.GONE);
    	children.get(idxPanel).startAnimation(outRight);
    }
    
    public void outFromBottom(int idxPanel)
    {
    	if(children.get(idxPanel) == null) 
    	{
    		return;
    	}
    	
    	this.idxPanel = idxPanel;
    	children.get(idxPanel).startAnimation(outDown);
    	children.get(idxPanel).setVisibility(View.GONE);
    }
}
