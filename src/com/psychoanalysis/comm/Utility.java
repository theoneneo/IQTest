package com.psychoanalysis.comm;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Utility 
{

    public static void drawCell(Canvas c, Bitmap img, int destX, int destY, int cellIndex, int cellW, int cellH)
    {
    	int rowIndex = cellIndex % 4;
    	int colIndex = cellIndex / 4;
    	
        int offsetX = rowIndex * cellW;
        int offsetY = colIndex * cellH;
    	c.save();//���浱ǰ״̬   
    	c.clipRect(destX, destY, destX + cellW, destY + cellH);
    	c.drawBitmap(img, destX - offsetX, destY - offsetY, null);
    	c.restore();//�ͷŵ�ǰ״̬ 
    }
    
    public static void drawCell(Canvas c, Bitmap img, int rowCount, int destX, int destY, int cellIndex, int cellW, int cellH)
    {
    	int rowIndex = cellIndex % rowCount;
    	int colIndex = cellIndex / rowCount;
    	
        int offsetX = rowIndex * cellW;
        int offsetY = colIndex * cellH;
    	c.save();//���浱ǰ״̬   
    	c.clipRect(destX, destY, destX + cellW, destY + cellH);
    	c.drawBitmap(img, destX - offsetX, destY - offsetY, null);
    	c.restore();//�ͷŵ�ǰ״̬ 
    }
    
    public static void drawCellScale(Canvas c, Bitmap img, int rowCount, int colCount, int marginOuterPercentX, int marginInnerPercentX, int marginInnerPercentY, int[] cellIndexs)
    {
    	int cellW, cellH;
    	int canvasW = c.getWidth();
    	int canvasH = c.getHeight();
    	
    	//������Ƶ�Ԫ�Ŀ��
    	int marginOuterX = (marginOuterPercentX * canvasW) / 100;
    	int leftMarginOuterX = marginOuterX / 2;
    	cellW = ((100 - marginInnerPercentX - marginOuterPercentX) * canvasW) / (100 * (colCount - 1));
    	
    	//������Ƶ�Ԫ�ĸ߶�
     	int destW = cellW * colCount; 
     	
    	/* ����reSize���Bitmap���� */
        Matrix matrix = new Matrix();
        float scale = ((float)destW) / img.getWidth();
        matrix.postScale(scale, scale); 
        Bitmap resizedBmp = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        
        int stepX = (marginInnerPercentX * canvasW) / (100 * colCount);
		int stepY = (marginOuterPercentX * canvasH) / (100 * rowCount);
		
		cellW = resizedBmp.getWidth() / 4;
		cellH = (int)(cellW * (57.0 / 78)); 
		
        for(int i = 0; i < colCount; i++)
		{
			for(int j = 0; j < rowCount; j++)
			{
				if(i * colCount + j == (rowCount * colCount - 1)) 
				{
					return;
				}
				Utility.drawCell(c, resizedBmp, rowCount, leftMarginOuterX + (stepX + cellW) * i, (stepY + cellH) * j, cellIndexs[i * colCount + j], cellW, cellH);
			}
		}
    }
    
    public static Document getTeleDoc(String url)
    {
        URL docUrl;
        try {
          docUrl = new URL(url);
               
          URLConnection connection;
          connection = docUrl.openConnection();
             
          HttpURLConnection httpConnection = (HttpURLConnection)connection; 
          int responseCode = httpConnection.getResponseCode(); 

          if (responseCode == HttpURLConnection.HTTP_OK) { 
            InputStream in = httpConnection.getInputStream(); 
                
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document dom = db.parse(in);      
            return dom;
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
        return null;
    }
}
