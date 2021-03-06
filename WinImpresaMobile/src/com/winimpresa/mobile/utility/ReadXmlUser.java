package com.winimpresa.mobile.utility;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ReadXmlUser {
	
	private InputStream fileXML;
	private User us;
	private String text;
	
	
	public ReadXmlUser(InputStream fileXML){
		this.fileXML = fileXML;
	}
	
	
	public User readInfoUser(){
		XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
 
            parser.setInput(fileXML, null);
 
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("info")) {
                        
                    	us = new User();
                    }
                    break;
 
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
 
                case XmlPullParser.END_TAG:
                    if (tagname.equalsIgnoreCase("idUser")) {
                        us.setIdUser(text);
                    } else if (tagname.equalsIgnoreCase("fullName")) {
                    	 us.setFullName(text);
                    }
                    break;
 
                default:
                    break;
                }
                eventType = parser.next();
            }
 
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return us;
    }
	



}


