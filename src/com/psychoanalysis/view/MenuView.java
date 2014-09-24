/**
 * 
 */
package com.psychoanalysis.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.psychoanalysis.iqtest.R;

/**
 * @author Neo
 *
 */
public class MenuView extends LinearLayout{

	/**
	 * @param context
	 * @param attrs
	 */
	private Context context;
	private RelativeLayout view;
	public MenuView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		  LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);	        
		  view = (RelativeLayout)inflate.inflate(R.layout.menu, null);

		  addView(view);
		  loadResourceId();	  
	}

	private void loadResourceId()
	{

    	LinearLayout btnOtherApp = (LinearLayout)view.findViewById(R.id.btnReleated);
    	btnOtherApp.setOnClickListener((OnClickListener)context);    	
    	LinearLayout btnHelp = (LinearLayout)view.findViewById(R.id.btnHelp);
    	btnHelp.setOnClickListener((OnClickListener)context);
    	LinearLayout btnAbout = (LinearLayout)view.findViewById(R.id.btnAbout);
    	btnAbout.setOnClickListener((OnClickListener)context);
        ImageButton btn = (ImageButton)view.findViewById(R.id.menu_btn);
        btn.setOnClickListener((OnClickListener) context);
	}
}
