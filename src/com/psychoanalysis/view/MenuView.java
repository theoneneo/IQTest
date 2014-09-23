/**
 * 
 */
package com.psychoanalysis.view;

import com.psychoanalysis.iqtest.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
	private LinearLayout view;
	public MenuView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		  LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);	        
		  view = (LinearLayout)inflate.inflate(R.layout.menu, null);

		  addView(view);
		  loadResourceId();	  
	}

	private void loadResourceId()
	{
		LinearLayout btnRefer = (LinearLayout)view.findViewById(R.id.btnSubmit);
    	btnRefer.setOnClickListener((OnClickListener)context);
//    	ImageButton btnSave = (ImageButton)view.findViewById(R.id.btnSave);
//    	btnSave.setOnClickListener((OnClickListener)context);
//    	ImageButton btnRead = (ImageButton)view.findViewById(R.id.btnRead);
//    	btnRead.setOnClickListener((OnClickListener)context);
//    	ImageButton btnSubject = (ImageButton)view.findViewById(R.id.btnMenuScan);
//    	btnSubject.setOnClickListener((OnClickListener)context);
    	LinearLayout btnIQ = (LinearLayout)view.findViewById(R.id.btnRank);
    	btnIQ.setOnClickListener((OnClickListener)context);
    	LinearLayout btnOtherApp = (LinearLayout)view.findViewById(R.id.btnReleated);
    	btnOtherApp.setOnClickListener((OnClickListener)context);    	
    	LinearLayout btnHelp = (LinearLayout)view.findViewById(R.id.btnHelp);
    	btnHelp.setOnClickListener((OnClickListener)context);
    	LinearLayout btnAbout = (LinearLayout)view.findViewById(R.id.btnAbout);
    	btnAbout.setOnClickListener((OnClickListener)context);
    	LinearLayout btnLogout = (LinearLayout)view.findViewById(R.id.btnExit);
    	btnLogout.setOnClickListener((OnClickListener)context);
        ImageButton btn = (ImageButton)view.findViewById(R.id.menu_btn);
        btn.setOnClickListener((OnClickListener) context);
	}
}
