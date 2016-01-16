package com.winimpresa.mobile.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class GlobalConstants {

	
	public static final String  nameInfoUser = "info_user.xml";
	public static final String  filedatabase = "db_scambioqrcode.db";
	public static final int     typeSyncFile = 2;    // 2 (visualizza tasto usb e internet) (1 solo internet ) (0 solo usb)
	public static final String  folderLocalFile = "winImpresa"; // folder create localmente con il file all'interno;s
	public static int   timeoutQrCode = 10; // secondi di tempo per recuperare il qrcode
	public static String tabellaLog = "tabellaLog";
	
	public static String nomeappdropbox      = "/winimpresaapp";
	public static String pathFolderDropLocal = "winimpresaapp/dumpattivita";  // path dove rilascio il file localmente
	public static String pathFolderDropLocalReleaase = "winimpresaapp/dropBox/release";
	public static String token_dropbox = "4-9Z_gq3yzAAAAAAAAAACAPYhXZ2Vz38zZJH850ZtqHY9Xp8PbfhSkCLmWMYT9mc";
	
	public static String readPathDropBox = "/buoni_utenti/";
	
	
	
	public static String QRCODECORROTTO = "1111";
	public static String DISPOSTIVODELETE = "0000";
	public static int PRIVATE_MODE = 0;
	public static final String PREFER_NAME						  = "PrefWinImpresaMobile";
	public static final String LOGIN_STATUS 		  			  = "LOGIN_STATUS";
	public static final String NAME_FILE_SYNCHROINZED 			  = "NAME_FILE_SYNCHROINZED";
	
	public static final String IDBUONO							  	  = "ID_BUONO";
	public static final String TYPESCHEDA							  = "TYPESCHEDA";
	public static final String IDVOCE							 	  = "IDVOCE";
	public static final String APP_KEY_DROP = "gc4jcaw21iuxp0z";
	public static final String APP_SECRET_DROP = "hoy7n6z2g3bztxb";
	
	
	
	public static final int[] schedeDaControllare =new int[] { 1, 2, 3, 4, 5, 6, 19 };
	
	public static String getNameFileDrop(String username){
		 Date today = new Date();
		 SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");
		 String date = DATE_FORMAT.format(today);
		
		
		return username+"_"+date+".txt";
	}
	
	public static String getNameFileDropRelease(String username){
		 Date today = new Date();
		 SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");
		 String date = DATE_FORMAT.format(today);
		
		
		return username+"_"+date+"_release.txt";
	}
	
	
	public static String setIntTime(int num){
		String numero = "";
		if(num<=9){
			numero="0"+num;
		}
		else{
			numero +=num;
		}
		return numero;
	}
	
	
	public static int controlloTipoScheda(int tipo){
		
		int flag = 0;
		
		for(int i=0; i<schedeDaControllare.length;i++){
			
			if(schedeDaControllare[i]==tipo){
				flag=1;
			}
		}
		
		return flag;
		
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
	
	
	public static String getQueryLog(String query){
	
		return "INSERT INTO "+GlobalConstants.tabellaLog+" (testo) VALUES ('"+query.replaceAll("'", "''")+"')";
	}
	
	
	public static long differentTime(String timeU,String timeD ){
		String time1 = timeU+":00";
		String time2 = timeD+":00";

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = format.parse(time1);
		   date2 = format.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		long difference = date2.getTime() - date1.getTime(); 
		return difference;
	}
	
}
