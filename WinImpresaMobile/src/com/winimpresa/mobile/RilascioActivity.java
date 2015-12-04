package com.winimpresa.mobile;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.dropbox.client2.DropboxAPI.Entry;
import com.winimpresa.mobile.async.ReleaseLocalDatabase;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RilascioActivity extends ActivityBase {

	private Button rilascio;
	public ProgressDialog progress;
	public int startRilascio=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_rilascio);
		
		rilascio = (Button) findViewById(R.id.rilascio);
		  gestionDropBox();
		  
		  progress = new ProgressDialog(context);
		    progress.setMessage("Copia operazione in corso...");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rilascio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	   public void syncWithDrop(View view){
	    	
	    ReleaseLocalDatabase rel = new ReleaseLocalDatabase(db, context, progress, this);
	    String[] param = {user.getIdUser()};
        rel.execute(param);
	 
	    }
	   
	   public void tornaAiBuoni(View view){
	    	
		   Intent new_page = new Intent(context, BuoniListActivity.class);
			startActivity(new_page);
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			finish();
		   	
		 
		
	   }
	   
	   
		@Override
	   public void realseWithDrop(){
			startRilascio=1;
		   mDBApi.getSession().startOAuth2Authentication(RilascioActivity.this);

	   }
	
	
	
	public String createFileDump(){
		try {
			File rootPath = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderDropLocalReleaase);
	    	 
	    	if(!rootPath.exists()) {
	           rootPath.mkdirs();
	           
	         }
	    	
	    	 
	    	File file = new File(rootPath,GlobalConstants.getNameFileDropRelease(user.getIdUser())+"");
	    	 if(!file.exists()) {
	    		 try {
					file.createNewFile();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
				
					e.printStackTrace();
				}
	    	 }
//	    	  FileOutputStream is = new FileOutputStream(file);
//	            OutputStreamWriter osw = new OutputStreamWriter(is);    
//	            Writer w = new BufferedWriter(osw);
//	            w.write("WINWER IS SABATINO!!!");
//	            w.close();
			
			FileInputStream inputStream = new FileInputStream(file);
		
			//display file saved message
			Entry response = mDBApi.putFile("/"+GlobalConstants.getNameFileDropRelease(user.getIdUser()), inputStream,
					file.length(), null, null);
				Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
			    
				return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		//return "success";
	}
	
	
	
	   @Override
	    protected void onResume() {
	        super.onResume();

	        if (mDBApi.getSession().authenticationSuccessful()) {
	            try {
	                // Required to complete auth, sets the access token on the session
	                mDBApi.getSession().finishAuthentication();
	           
	                if(startRilascio==1)
	                {
	                	String accessToken = mDBApi.getSession().getOAuth2AccessToken();
	                	LongOperationDropBox a = new LongOperationDropBox();
	                	a.execute();
	                }
	            } catch (IllegalStateException e) {
	                Log.i("DbAuthLog", "Error authenticating", e);
	            }
	        }
	    }
	   
	   
	   private class LongOperationDropBox extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... params) {
	          
	        	return createFileDump();
	            
	        }

	        @Override
	        protected void onPostExecute(String result) {
	        	
	        	if(result.equals("success")){
	        		logout();
	        		goLogin();
	        		
	        	}
	        }
	        

	        @Override
	        protected void onPreExecute() {
	        
	        }

	        @Override
	        protected void onProgressUpdate(Void... values) {
	        }
	    }
	   
	   
	   public void goLogin(){
		   Intent new_page = new Intent(context, MainActivity.class);
					startActivity(new_page);
					overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
					finish();
	   }
	   
	   @Override
		public void onBackPressed() {
		   Intent new_page = new Intent(context, BuoniListActivity.class);
			startActivity(new_page);
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			finish();
		   	
		}
}


