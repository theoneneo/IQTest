package com.psychoanalysis.view;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psychoanalysis.iqtest.IQTestApplication;
import com.psychoanalysis.iqtest.R;
/**
 * @author Neo
 *
 */
public class IQNumberView extends RelativeLayout{

	/**
	 * @param context
	 */
	private IQTestApplication app;
	private LinearLayout linear;
	private Context context;
	public IQNumberView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		app = IQTestApplication.getApplication();
		this.context = context;
		init();
	}
	
	private void init(){
		LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);	        
		RelativeLayout item = (RelativeLayout)inflate.inflate(R.layout.iqnumber, null);
		addView(item);
		linear = (LinearLayout)item.findViewById(R.id.content);
	}
	
	public void setPageName()
	{
		linear.removeAllViews();
		for(int i = 0; i < app.vApp.size(); i++)
		{
	        TextView text1 = new TextView(context);
	        text1.setText(String.valueOf(app.vApp.get(i).pageInfo));
	        //text1.setTextColor(0xffffff);
	        text1.setTextSize(25);
	        linear.addView(text1);
			
			
	        TextView text2 = new TextView(context);
	        text2.setText(
	            Html.fromHtml(
	                "<a href="+String.valueOf(app.vApp.get(i).pageLink)+">"+String.valueOf(app.vApp.get(i).pageName)+"</a> "));
	        text2.setTextColor(0xffffff);
	        text2.setTextSize(25);
	        text2.setMovementMethod(LinkMovementMethod.getInstance());
	        linear.addView(text2);
		}
	}
}
