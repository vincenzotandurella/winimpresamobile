package com.winimpresa.mobile;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxWriteMode;
import com.winimpresa.mobile.async.ReleaseLocalDatabase;
import com.winimpresa.mobile.database.MonitoraggioTable;
import com.winimpresa.mobile.utility.Connectivity;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RilascioActivity extends ActivityBase {

	private Button rilascio;
	public ProgressDialog progress;
	public int startRilascio=0;
	private MonitoraggioTable mon;
	private AlertDialog.Builder alertDialogBuilder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_rilascio);
		 alertDialogBuilder 		= new AlertDialog.Builder(this);
		rilascio = (Button) findViewById(R.id.rilascio);
		  gestionDropBox();
		  mon = new MonitoraggioTable(context, db);
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
		   
		   if(mon.selectAllMonitoraggioStatus() >0){
			   dialogBoxStop();
		   }
		   else{
			   startSync(); 
		   }
		
        
	 
	    }
	   
	   
	   public void startSync(){
		   progress.show();
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
			LongOperationDropBox a = new LongOperationDropBox();
        	a.execute();
		  // mDBApi.getSession().startOAuth2Authentication(RilascioActivity.this);

	   }
	
		public void dialogBoxStop() {

			
			alertDialogBuilder.setMessage("Nelle attività ci sono dei buoni non evasi vuoi cimunque continuare? ");
			alertDialogBuilder.setPositiveButton("Continua",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {

							 startSync();

						}
					});
			alertDialogBuilder.setNegativeButton("Annulla",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {

						}
					});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		}
	
	public String createFileDump(){
		gestionDropBox();
		
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

			
			FileInputStream inputStream = new FileInputStream(file);
			
			if(Connectivity.isConnected(context)){
			 DbxEntry.File uploadedFile = client.uploadFile("/fine_attivita/"+user.getIdUser()+"/"+GlobalConstants.getNameFileDropRelease(user.getIdUser()),
		           DbxWriteMode.add(), file.length(), inputStream);
			 return "success";
			}
			else{
				return "notConnect";
			}
	
			    
				
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		//return "success";
	}
	
	
	
	   @Override
	    protected void onResume() {
	        super.onResume();

	      /*  if (mDBApi.getSession().authenticationSuccessful()) {
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
	        }*/
	    }
	   
	   
	   private class LongOperationDropBox extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... params) {
	          
	        	return createFileDump();
	            
	        }

	        @Override
	        protected void onPostExecute(String result) {
	        	
	        	if(result.equals("success")){
	        		progress.hide();
	        		showToast("Invio dati con successo !");
	        		File rootPath = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderDropLocal);
	        		GlobalConstants.deleteDir(rootPath);
	        		
	        		logout();
	        		goLogin();
	        		
	        	}
	        	if(result.equals("notConnect")){
	        		showToast("La connessione internet non è presente !");
	        	}
	        	progress.hide();
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


