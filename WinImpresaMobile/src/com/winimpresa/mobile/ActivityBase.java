package com.winimpresa.mobile;

import java.io.IOException;

import com.winimpresa.interfaces.ActivityInterface;
import com.winimpresa.mobile.utility.GlobalConstants;
import com.winimpresa.mobile.utility.ReadXmlUser;
import com.winimpresa.mobile.utility.User;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


public class ActivityBase extends Activity implements ActivityInterface{
	protected  User user;
	public     Context context;
	    protected void onCreate(Bundle savedInstanceState, int layoutId) {
	        super.onCreate(savedInstanceState);
	        setContentView(layoutId);
	        this.readUser();
	        context = this;
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
	    

}
