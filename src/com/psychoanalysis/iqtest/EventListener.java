package com.psychoanalysis.iqtest;
/**
 * @author mobi.liubing@gmail.com
 * @creation date Dec 14, 2009 4:02:42 PM 
 */
public interface EventListener
{
	public static final byte EVENT_SAVE_CURRENT_SUBJECT = 0;
	public static final byte EVENT_LAOD_CURRENT_SUBJECT = EVENT_SAVE_CURRENT_SUBJECT + 1;
	public static final byte EVENT_GET_SELECT_SUBJECT = EVENT_LAOD_CURRENT_SUBJECT + 1;
	public static final byte EVENT_UPDATA_REFREASH_MAIN = EVENT_GET_SELECT_SUBJECT + 1;
	public static final byte EVENT_UPDATA_IQ_NUM = EVENT_UPDATA_REFREASH_MAIN + 1;
	public static final byte EVENT_VERSION_UPDATE = EVENT_UPDATA_IQ_NUM + 1;
    
	public void eventAction(byte eventType, Object obj);
}