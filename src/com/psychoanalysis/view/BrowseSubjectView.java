package com.psychoanalysis.view;

import techtheme.metal.core.TestSet;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.psychoanalysis.iqtest.EventListener;
import com.psychoanalysis.iqtest.MainActivity;
import com.psychoanalysis.iqtest.R;
/**
 * @author Neo
 *
 */
public class BrowseSubjectView extends LinearLayout{

	/**
	 * @param context
	 */

	private Context context;
	private MainActivity activity;
	private GridView mGrid;
	public ImageAdapter mAdapter;
	private LinearLayout view;
	public BrowseSubjectView(Context context, MainActivity activity) {
		super(context);
		this.context = context;
		this.activity = activity;
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		  LayoutInflater inflate = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);	        
		  view = (LinearLayout)inflate.inflate(R.layout.browsesubject, null);
		  addView(view);

		  loadResourceId();
	}
	
	private void loadResourceId()
	{
        mGrid = (GridView)view.findViewById(R.id.subject);
        mAdapter = new ImageAdapter(context);
        mGrid.setAdapter(mAdapter); 
        ImageButton btn = (ImageButton)view.findViewById(R.id.over_btn);
        btn.setOnClickListener((OnClickListener) context);
//        mGrid.setOnItemClickListener(new OnItemClickListener() 
//		{
//			
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) 
//			{
//				Integer i = (Integer) mGrid.getItemAtPosition(arg2);
//				Message msg = new Message();
//				msg.what = EventListener.EVENT_GET_SELECT_SUBJECT;
//				msg.obj = i;
//				activity.getMainHandler().sendMessage(msg);
//			}
//		});
	}
	
    public class ImageAdapter extends BaseAdapter {

		private Context context;
		LayoutInflater mInflater;
		ImageAdapter(Context context) {
			this.context = context;
			mInflater = LayoutInflater.from(context);
		}
		
    	public void UpdateView()
    	{
    		mAdapter.notifyDataSetChanged();
    	}
		
        public View getView(final int position, View convertView, ViewGroup parent) {
        	LinearLayout linear = (LinearLayout)mInflater.inflate(R.layout.number, null);
        	
        	if(TestSet.getInstance().isFilled(position))
        	{
        		linear.setBackgroundResource(R.drawable.scan_answer_bg);
        	}
        	else
        	{
        		linear.setBackgroundResource(R.drawable.scan_unanswer_bg);
        	}
        	linear.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.what = EventListener.EVENT_GET_SELECT_SUBJECT;
					msg.obj = position;
					activity.getMainHandler().sendMessage(msg);
				}     		
        	});
        	
        	ImageView img1 = (ImageView)linear.findViewById(R.id.img1);
        	ImageView img2 = (ImageView)linear.findViewById(R.id.img2);

        	int decade = position/10;
        	int remainder = position%10;
        	if(decade == 0)
        	{
        		if(remainder == 9)
        		{
        			img1.setBackgroundResource(R.drawable.number_1);
        			img2.setBackgroundResource(R.drawable.number_0);
        		}
        		else
        		{
        			img1.setBackgroundColor(0x00ffffff);
        		}
        	}
        	else if(decade == 1)
        	{
        		if(remainder == 9)
        		{
        			img1.setBackgroundResource(R.drawable.number_2);
        			img2.setBackgroundResource(R.drawable.number_0);
        		}
        		else
        		{
        			img1.setBackgroundResource(R.drawable.number_1);
        			img2.setBackgroundResource(R.drawable.number_0);
        		}	
        	}
        	else if(decade == 2)
        	{
        		if(remainder == 9)
        		{
        			img1.setBackgroundResource(R.drawable.number_3);
        			img2.setBackgroundResource(R.drawable.number_0);
        		}
        		else
        		{
        			img1.setBackgroundResource(R.drawable.number_2);
        			img2.setBackgroundResource(R.drawable.number_0);
        		}	
        	}
        	else if(decade == 3)
        	{
        		if(remainder == 9)
        		{
        			img1.setBackgroundResource(R.drawable.number_4);
        			img2.setBackgroundResource(R.drawable.number_0);
        		}
        		else
        		{
        			img1.setBackgroundResource(R.drawable.number_3);
        			img2.setBackgroundResource(R.drawable.number_0);
        		}	
        	}
        	
        	if(remainder == 0)
        	{
        		img2.setBackgroundResource(R.drawable.number_1);
        	}
        	else if(remainder == 1)
        	{
        		img2.setBackgroundResource(R.drawable.number_2);
        	}
        	else if(remainder == 2)
        	{
        		img2.setBackgroundResource(R.drawable.number_3);
        	}
        	else if(remainder == 3)
        	{
        		img2.setBackgroundResource(R.drawable.number_4);
        	}
        	else if(remainder == 4)
        	{
        		img2.setBackgroundResource(R.drawable.number_5);
        	}
        	else if(remainder == 5)
        	{
        		img2.setBackgroundResource(R.drawable.number_6);
        	}
        	else if(remainder == 6)
        	{
        		img2.setBackgroundResource(R.drawable.number_7);
        	}
        	else if(remainder == 7)
        	{
        		img2.setBackgroundResource(R.drawable.number_8);
        	}
        	else if(remainder == 8)
        	{
        		img2.setBackgroundResource(R.drawable.number_9);
        	}


            linear.setLayoutParams(new GridView.LayoutParams(60, 60));
            return linear;
        }

        public final int getCount() {
            return 39;
        }

        public final Object getItem(int position) {
            return position;
        }

        public final long getItemId(int position) {
            return position;
        }
    }
}
