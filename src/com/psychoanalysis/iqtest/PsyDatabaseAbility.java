package com.psychoanalysis.iqtest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import techtheme.metal.ability.DatabaseAbility;
import techtheme.metal.com.Common;
import techtheme.metal.com.Utility;
import techtheme.metal.db.BasedataElement;
import techtheme.metal.db.DatabaseType;
import techtheme.metal.db.concret.AnswerSheet;
import techtheme.metal.db.concret.TestPageIndex;
import techtheme.metal.db.concret.QuestionSelection;
import techtheme.metal.db.concret.AnswerSelection;
import techtheme.metal.db.concret.TestResult;
import techtheme.metal.db.concret.TimeCost;
import techtheme.metal.db.concret.TimeRest;

public class PsyDatabaseAbility implements DatabaseAbility
{
	public static final String TEST_PREFS = "TestStore";
	private Activity _mainActivity;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	public PsyDatabaseAbility(Activity mainActivity)
	{
		_mainActivity = mainActivity;
		mySharedPreferences = _mainActivity.getSharedPreferences(TEST_PREFS, Activity.MODE_PRIVATE);
		editor = mySharedPreferences.edit();
	}
	
    public boolean add(int dbType, BasedataElement newElement)
    {
    	try
    	{
    		switch(dbType)
        	{
        		case DatabaseType.DB_TIME_REST:
        			long restTime = ((TimeRest)newElement).getValue();
        			editor.putLong(newElement.getId(), restTime);
        			break;
    			case DatabaseType.DB_TIME_COST:
    				long costTime = ((TimeCost)newElement).getValue();
        			editor.putLong(newElement.getId(), costTime);
        			break;
        		case DatabaseType.DB_TEST_PAGE_INDEX:
        			int pageIndex = ((TestPageIndex)newElement).getTestPageIndex();
        			editor.putInt(newElement.getId(), pageIndex);
        			break;
        		case DatabaseType.DB_QUESTION_SELECTION:
        			int[] quesIndex = ((QuestionSelection)newElement).getSelection();
        			String quesSele = Utility.combinIntArrayToString(quesIndex, "_");
        			editor.putString(newElement.getId(), quesSele);
        			break;
        		case DatabaseType.DB_ANSWER_SELECTION:
        			int[] answs = ((AnswerSelection)newElement).getSelection();
        			String answerSele = Utility.combinIntArrayToString(answs, "_");
        			editor.putString(newElement.getId(), answerSele);
        			break;
        		case DatabaseType.DB_ANSWER_SHEET:
        			int[] answerSheet = ((AnswerSheet)newElement).getAnswerNodes();
        			String answerSheetString = Utility.combinIntArrayToString(answerSheet, "_");
        			editor.putString(newElement.getId(), answerSheetString);
        			break;
        		case DatabaseType.DB_TEST_RESULT:
        			int[] ts = new int[2];
        			ts[0] = ((TestResult)newElement).getIq();
        			ts[1] = ((TestResult)newElement).getDispPercent();
        			String testResultString = Utility.combinIntArrayToString(ts, "_");
        			editor.putString(newElement.getId(), testResultString);
        			break;
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    	editor.commit();
//        System.out.println("---- add DataType:" + dbType + " data id:" + newElement.getId());
        return true;
    }

    public boolean del(int dbType, String id)
    {
    	try
    	{
    		if(id != null)
        	{
        		editor.remove(id);
        		editor.commit();
        	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return false;
    	}
//        System.out.println("---- del DataType:" + dbType + " data id:" + id);
        return true;
    }

    public BasedataElement get(int dbType, String id)
    {
    	switch(dbType)
    	{
    		case DatabaseType.DB_TIME_REST:
    			long restTime = mySharedPreferences.getLong(id, Common.TIME_LIMIT);
    			TimeRest restTimeObj = new TimeRest();
    			restTimeObj.setValue(restTime);
				return restTimeObj;
    		case DatabaseType.DB_TIME_COST:
    			long costTime = mySharedPreferences.getLong(id, 0);
    			TimeCost costTimeObj = new TimeCost();
    			costTimeObj.setValue(costTime);
    			return costTimeObj;
    		case DatabaseType.DB_TEST_PAGE_INDEX:  			
    			int tpi = mySharedPreferences.getInt(id, 0);	
    			TestPageIndex tpiObj = new TestPageIndex(tpi);
    			return tpiObj;
    		case DatabaseType.DB_QUESTION_SELECTION:
    			String qs = mySharedPreferences.getString(id, "");	
    			int[] qsArray = Utility.splitStringToIntArray(qs, "_");
    			QuestionSelection qsObj = new QuestionSelection(qsArray);
    			return qsObj;
    		case DatabaseType.DB_ANSWER_SELECTION:
    			String as =  mySharedPreferences.getString(id, "");	
    			int[] answs = Utility.splitStringToIntArray(as, "_");
    			AnswerSelection asObj = new AnswerSelection(answs);
    			return asObj;
    		case DatabaseType.DB_ANSWER_SHEET:
    			String das =  mySharedPreferences.getString(id, "");
    			int[] answerSheet;
    			if(das == null || das.length() == 0)
    			{
    			    answerSheet = new int[Common.TEST_COUNT];
    			    for (int i = 0; i < answerSheet.length; i++)
    		        {
    		            answerSheet[i] = -1;
    		        }
    			}
    			else
    			{
    			    answerSheet = Utility.splitStringToIntArray(das, "_");
    			}
    			
    			AnswerSheet answerSheetObj = new AnswerSheet(answerSheet);
    			return answerSheetObj;
    		case DatabaseType.DB_TEST_RESULT:
    			String iqs = mySharedPreferences.getString(id, "");	
    			int[] iqAndDispPercent = Utility.splitStringToIntArray(iqs, "_");
    			TestResult iqObj = new TestResult(iqAndDispPercent[0], iqAndDispPercent[1]);
    			return iqObj;
    	}
//        System.out.println("---- get DataType:" + dbType + " data id:" + id);
        return null;
    }

    public BasedataElement[] getElements(int dbType)
    {
        return null;
    }

    public boolean update(int dbType, BasedataElement newElement)
    {
    	if(!del(dbType, newElement.getId()))
    	{
    		return false;
    	}
    	if(!add(dbType, newElement))
    	{
    		return false;
    	}
//        System.out.println("---- update DataType:" + dbType + " data id:" + newElement.getId());
        return true;
    }

}
