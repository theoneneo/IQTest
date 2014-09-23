package com.psychoanalysis.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.psychoanalysis.iqtest.R;
/**
 * @author Neo
 *
 */
public class AboutHelpView extends RelativeLayout{

	/**
	 * @param context
	 */
	private ScrollView scorllview;
	public TextView data_title, data_info;
	public AboutHelpView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		  LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);	        
		  RelativeLayout item = (RelativeLayout)inflate.inflate(R.layout.about_help, null);
		  addView(item);
		  data_title = (TextView)item.findViewById(R.id.data_title);
		  data_info = (TextView)item.findViewById(R.id.data_info);
		  scorllview = (ScrollView)item.findViewById(R.id.scrollview);
	}
	
	public void makeScrollview()
	{
		scorllview.fullScroll(ScrollView.FOCUS_UP);
	}
}
