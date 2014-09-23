/**
 * 
 */
package com.psychoanalysis.iqtest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.psychoanalysis.comm.Utility;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author Neo
 *
 */

public class IQTestApplication extends Application implements EventListener{
    public static boolean lockAnswerArea = false;
	private static IQTestApplication app;
	private float screenScale;
	private Handler MainHandler;
	public static String downloadUrl;
	private static int newVersion = 0;
	private String TAG = "IQTestApplication";
	public Vector<AppItem> vApp;
	public static Activity mActivity;

    public void onCreate() 
    {
    	super.onCreate();
    	vApp = new Vector<AppItem>();
    	init();
    }
    
    public void onTerminate() 
    {
		
    }
    
    private void putAppItem(String pageName, int version, String pageLink, String pageInfo)
    {
    	AppItem item = new AppItem();
    	item.pageName = pageName;
    	item.version = version;
    	item.pageLink = pageLink;
    	item.pageInfo = pageInfo;
    	vApp.addElement(item);
    }
    
    public static IQTestApplication getApplication(Activity activity) 
    {
        // TODO should this be synchronized?
        mActivity = activity;
        if (app == null) 
        {
            initialize(activity);
        }   
        return app;
    }
    
    public static IQTestApplication getApplication() 
    {
    	if (app == null) 
    	{
    		app=new IQTestApplication();
    	}
        return app;
    }
    
    private static void initialize(Activity activity)
    {    	
    	app = new IQTestApplication();
        app.onCreate(); 
        app.screenScale = activity.getResources().getDisplayMetrics().density;  
    }
    
    public float getScreenScale()
    {
    	return screenScale;
    }
    
    public void setMainHandler(Handler h)
    {
    	MainHandler = h;
    }
    
    private void init()
    {
    	//Check version update.
    	new Thread()
    	{
    		public void run()
    		{
    			URL url;
    		  	  try {
    		  	    String avaluableApps = "http://www.techtheme.org/iqroot/version.xml";
    		  	    url = new URL(avaluableApps);
    		  	    Log.i(TAG, "11111111");     
    		  	    URLConnection connection;
    		  	    connection = url.openConnection();
    		  	    Log.i(TAG, "22222222");    
    		  	    HttpURLConnection httpConnection = (HttpURLConnection)connection; 
    		  	    int responseCode = httpConnection.getResponseCode(); 
    		  	    Log.i(TAG, "333333333"); 
    		  	    if (responseCode == HttpURLConnection.HTTP_OK) { 
    		  	      InputStream in = httpConnection.getInputStream(); 
    		  	          
    		  	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    		  	      DocumentBuilder db = dbf.newDocumentBuilder();
    		  	      Log.i(TAG, "444444444"); 
    		  	      // Parse the earthquake feed.
    		  	      Document dom = db.parse(in);      
    		  	      Element docEle = dom.getDocumentElement();
    		  	      Log.i(TAG, "5555555555555");     
    		  	      // Get a list of each earthquake entry.
    		  	      NodeList nl = docEle.getElementsByTagName("iq_test");
    		  	      if (nl != null && nl.getLength() > 0) 
    		  	      {
    		  	    	  Node version = nl.item(0);
    		  	    	  NamedNodeMap atts = version.getAttributes();
    		  	    	  int attrSize = atts.getLength();
    		  	    	  for(int i = 0; i < attrSize; i++)
    		  	    	  {
    		  	    		  Log.i(TAG, "6666666666666"); 
    		  	    		  Node Attrnode = atts.item(i);
    		  	    		  if("version".equals(Attrnode.getNodeName()))
    		  	    		  {
    		  	    		      String avaliableVersion = Attrnode.getNodeValue();
    		  	    		      if(avaliableVersion != null)
    		  	    		      {
    		  	    		    	newVersion = Integer.valueOf(avaliableVersion);
    		  	    		        NodeList nodes = version.getChildNodes(); 
    		  	    		        downloadUrl = nodes.item(0).getNodeValue(); 
    		  	    		      }
    		  	    		      Log.i(TAG, "77777777777777"); 
    		  	    		      app.eventAction(EventListener.EVENT_VERSION_UPDATE, null);
    		  	    		  }
    		  	    	  }
    		  	      }
    		  	    }
    		  	  } catch (MalformedURLException e) {
    		  	    e.printStackTrace();
    		  	  } catch (IOException e) {
    		  	    e.printStackTrace();
    		  	  } catch (ParserConfigurationException e) {
    		  	    e.printStackTrace();
    		  	  } catch (SAXException e) {
    		  	    e.printStackTrace();
    		      } catch (Exception e) {
    		            e.printStackTrace();
    		      }
    		  	  finally {
    		  	  }
    		}
    	}.start(); 	
    }
    
    public int getNewVersion()
    {
    	return newVersion;
    }
    
    private Handler IQTestHandler = new Handler() 
    {    	
        @Override 
        public void handleMessage(Message msg) 
        { 
             switch (msg.what) 
             {
	             case EventListener.EVENT_UPDATA_IQ_NUM:
	             case EventListener.EVENT_VERSION_UPDATE:
	            	 if(MainHandler != null)
	            	 {
	 					Message m = new Message();
						m.what = msg.what;
						m.obj = msg.obj;
						MainHandler.sendMessage(m);
	            	 }
	             	break;
	             default:
	            	 break;

            	 
             }
             super.handleMessage(msg);             
        } 
    };
	
	/* (non-Javadoc)
	 * @see com.psychoanalysis.iqtest.EventListener#eventAction(byte, java.lang.Object)
	 */
	public void eventAction(byte eventType, Object obj) {
		// TODO Auto-generated method stub
        Message msg = new Message(); 
        msg.what = eventType; 
        msg.obj = obj;
        app.IQTestHandler.sendMessage(msg);
	}
	
	public String toValidRs(String obj)
	{
		if (obj == null)
			return "@*@";
		else if(obj.indexOf("'")!=-1)
		{
			return obj.replace("'", "*@*");
		}
		else
			return obj;
	}
	
	public String getUnvalidFormRs(String obj)
	{

			if (obj.equals("@*@"))
				return null;
			else if(obj.indexOf("*@*")!=-1)
			{
				return obj.replace("*@*", "'");
			}
			else
				return obj;

	}
	
	public void getAvaliableApps()
	{
	    new Thread()
        {
            public void run()
            {
                Document dom = Utility.getTeleDoc("http://www.techtheme.org/app_list/app_list.xml");
                if(dom == null)
                {
                	return;
                }
                Element docEle = dom.getDocumentElement();
                
                NodeList androidAppList = docEle.getElementsByTagName("android");
                if (androidAppList != null) 
                {
                    int appCount = androidAppList.getLength();
                   
                    for(int i = 0; i < appCount; i++)
                    {
                        Element apps = (Element)androidAppList.item(i);
                        NodeList androidApps = apps.getChildNodes();
                        int androidAppCount = androidApps.getLength();
                        for(int j = 0; j < androidAppCount; j++)
                        {
                            Node androidApp = androidApps.item(j);
                            if(androidApp.getNodeType() == Node.ELEMENT_NODE)
                            {
                                String name = ((Element)androidApp).getAttribute("name");
                                String desc = ((Element)androidApp).getAttribute("desc");
                                String version = ((Element)androidApp).getAttribute("version");
                                String url = androidApp.getFirstChild().getNodeValue();
                                if(name != null && version != null && url != null)
                                {
                                    putAppItem(name, Integer.parseInt(version), url, desc);
                                }
                            }
                        }
                    }   
                }
            }
        }.start();  
	}
}
