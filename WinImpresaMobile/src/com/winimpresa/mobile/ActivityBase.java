package com.winimpresa.mobile;

import java.io.IOException;
import java.util.Locale;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.winimpresa.mobile.database.AssetDatabaseOpenHelper;
import com.winimpresa.mobile.interfaces.ActivityInterface;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.Connectivity;
import com.winimpresa.mobile.utility.GlobalConstants;
import com.winimpresa.mobile.utility.ReadXmlUser;
import com.winimpresa.mobile.utility.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class ActivityBase extends Activity implements ActivityInterface{
	protected  User user;
	public     Context context;
	public SQLiteDatabase db ;
	public SharedPreferences pref;
	public Editor editor;
	public   DbxClient client;
	
	
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
	    	if(Connectivity.isConnected(context)){
	    	 DbxRequestConfig config = new DbxRequestConfig(GlobalConstants.nomeappdropbox, Locale.getDefault().toString());
	        client = new DbxClient(config, GlobalConstants.token_dropbox);
	    	}
	        
	    }
	    
	    public static void hideKeyboard(Activity activity) {
		    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		    //Find the currently focused view, so we can grab the correct window token from it.
		    View view = activity.getCurrentFocus();
		    //If no view currently has focus, create a new one, just so we can grab a window token from it
		    if (view == null) {
		        view = new View(activity);
		    }
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	    
	    



	

}
