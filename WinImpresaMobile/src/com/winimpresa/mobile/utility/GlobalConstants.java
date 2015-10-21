package com.winimpresa.mobile.utility;

public class GlobalConstants {

	
	public static final String  nameInfoUser = "info_user.xml";
	public static final String  filedatabase = "db_scambioqrcode.db";
	public static final int     typeSyncFile = 2;    // 2 (visualizza tasto usb e internet) (1 solo internet ) (0 solo usb)
	public static final String  folderLocalFile = "winImpresa"; // folder create localmente con il file all'interno;s
	
	public static int PRIVATE_MODE = 0;
	public static final String PREFER_NAME						  = "PrefWinImpresaMobile";
	public static final String LOGIN_STATUS 		  			  = "LOGIN_STATUS";
	public static final String NAME_FILE_SYNCHROINZED 			  = "NAME_FILE_SYNCHROINZED";
	
	public static final String IDBUONO							  	  = "ID_BUONO";
	public static final String TYPESCHEDA							  = "TYPESCHEDA";
	public static final String IDVOCE							 	  = "IDVOCE";
	
	
	public static String getNameFileQuery(String username){
		
		return username+"";
	}
	
	
	public static String spiltDate(String date){
		
		String[] parts = date.split(" ");
		String[] dates = parts[0].split("-");
		
		return dates[2]+"/"+dates[1]+"/"+dates[0];
		
	}
	
	public static String spiltTime(String date){
		
		String[] parts = date.split(" ");
		String[] dates = parts[1].split(":");
		
		return dates[0]+":"+dates[1];
		
	}
	
}
