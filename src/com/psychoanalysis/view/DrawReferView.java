package com.psychoanalysis.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psychoanalysis.iqtest.R;
/**
 * @author Neo
 *
 */
public class DrawReferView extends LinearLayout{

	/**
	 * @param context
	 */
	private LinearLayout item;
	public ReferView v;
	public TextView iqNum;
	private Context context;
	public DrawReferView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		  LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);	        
		  item = (LinearLayout)inflate.inflate(R.layout.refer, null);
		  Button btn = (Button)item.findViewById(R.id.submitScore);
		  btn.setOnClickListener((OnClickListener)context);
		  v = (ReferView)item.findViewById(R.id.indicate);
		  iqNum = (TextView)item.findViewById(R.id.iq);
		  addView(item);
	}
}
