package com.winimpresa.mobile;

import java.io.IOException;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.winimpresa.mobile.database.AssetDatabaseOpenHelper;
import com.winimpresa.mobile.interfaces.ActivityInterface;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.GlobalConstants;
import com.winimpresa.mobile.utility.ReadXmlUser;
import com.winimpresa.mobile.utility.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class ActivityBase extends Activity implements ActivityInterface{
	protected  User user;
	public     Context context;
	public SQLiteDatabase db ;
	public SharedPreferences pref;
	public Editor editor;
	public DropboxAPI<AndroidAuthSession> mDBApi;
	
	
	protected void onCreate(Bundle savedInstanceState, int layoutId) {
	        super.onCreate(savedInstanceState);
	        setContentView(layoutId);
	        this.readUser();
	        context = this;
	        this.openDatabase();
	        pref 	= context.getSharedPreferences(GlobalConstants.PREFER_NAME, GlobalConstants.PRIVATE_MODE);
	        editor  = pref.edit();
	    }
	    
	    
	    
	    
	   //Leggo il file per le informazione dell'untente in sessione 
	   private void readUser(){
		   
		    try {
				ReadXmlUser xmlUser = new	ReadXmlUser(getAssets().open(GlobalConstants.nameInfoUser));
				user = xmlUser.readInfoUser();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   } 
	   
	   
	   public void openDatabase(){
		   AssetDatabaseOpenHelper dbOpen = new AssetDatabaseOpenHelper(context);
		   db = dbOpen.openDatabase();
		   
	   }
	   
	   
	    public void createSession(){
	    	editor.putBoolean(GlobalConstants.LOGIN_STATUS, true);
	    	editor.commit();
	    }
	    
	    public boolean isUserLoggedIn(){
	        return pref.getBoolean(GlobalConstants.LOGIN_STATUS, false);
	        
	    }
	    
	    
	    
	    public void logout(){
	       	editor.putBoolean(GlobalConstants.LOGIN_STATUS, false);
	       	setStorageFileName(null);
	    	editor.commit();
	    }
	    
	    
	    public void setStorageFileName(String file){
	    	editor.putString(GlobalConstants.NAME_FILE_SYNCHROINZED, file);
	    	editor.commit();
	    }
	    
	    public  String getStorageFileName(){
	    	return pref.getString(GlobalConstants.NAME_FILE_SYNCHROINZED, null);
	    }
	   
	    
	    public void showToast(String msg) {
			Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			t.show();
		}
	    
	    public boolean controllFileRead(){
	    	
	    	boolean flag = false;
	    	
	    	if(this.getStorageFileName()!=null){
	    		flag = true;
	    	}
	    	
	    	return flag;
	    }
	    
	    public void closekeyboard(){
	    	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	    }
	   
	   @Override
		public void finish() {
			super.finish();
			
		}

		@Override
		protected void onStop() {
			super.onStop();
			
		}

		@Override
		protected void onDestroy() {
			super.onDestroy();
		
		}




		@Override
		public void backActivity() {
			// TODO Auto-generated method stub
			
		}




		@Override
		public void showMessage(String msg) {
			// TODO Auto-generated method stub
			showToast(msg);
		}




		@Override
		public String[] taAttivita(String[] attivita) {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public String[] taStatoEsca(String[] statoEsca) {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public String[] taTipoSostituzione(String[] prodottoSostituito) {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public MonitoriaggioVoci getMonitoriaggioVoci() {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public boolean updateMonitoraggioVoci(MonitoriaggioVoci mon) {
			// TODO Auto-generated method stub
			return false;
		}




		@Override
		public String[] taArticoli(String[] articoli) {
			// TODO Auto-generated method stub
			return null;
		}




		@Override
		public String[] taTipoDispositivo(String[] tipoDisp) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void realseWithDrop(){
			
		}

		
	    public void gestionDropBox(){
	    	   AppKeyPair appKeys = new AppKeyPair(GlobalConstants.APP_KEY_DROP, GlobalConstants.APP_SECRET_DROP);
	           AndroidAuthSession session = new AndroidAuthSession(appKeys);
	          	mDBApi = new DropboxAPI<AndroidAuthSession>(session);
	           
	    }
	    
	    
	    
	    



	

}
