package com.psychoanalysis.view;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.psychoanalysis.iqtest.IQTestApplication;
import com.psychoanalysis.iqtest.R;

/**
 * @author Neo
 * 
 */
public class IQNumberView extends RelativeLayout {

	/**
	 * @param context
	 */
	private IQTestApplication app;
	private Context context;

	public IQNumberView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		app = IQTestApplication.getApplication();
		this.context = context;
		init();
	}

	private void init() {
		LayoutInflater inflate = (LayoutInflater) getContext()
				.getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
		RelativeLayout item = (RelativeLayout) inflate.inflate(
				R.layout.iqnumber, null);
		addView(item);

		TextView ad1 = (TextView) item.findViewById(R.id.ad_1);
		ad1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				downloadApk("http://bcs.duapp.com/pgupdate/PG_default_2.0.apk", "PG_default_2.0.apk");
			}

		});

		TextView ad2 = (TextView) item.findViewById(R.id.ad_2);
		ad2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				downloadApk("http://gdown.baidu.com/data/wisegame/e187de444ea6b915/CrazySchoolGirl_3.apk", "CrazySchoolGirl_3.apk");
			}
		});
	}
	
	private void downloadApk(String url, String name){
		DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(getContext().DOWNLOAD_SERVICE);

		Uri uri = Uri.parse(url);
		Request request = new Request(uri);

		// 设置允许使用的网络类型，这里是移动网络和wifi都可以
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
				| DownloadManager.Request.NETWORK_WIFI);

		request.setVisibleInDownloadsUi(true);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setDestinationInExternalPublicDir("", name);
		downloadManager.enqueue(request);
	}
}
