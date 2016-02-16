package com.winimpresa.mobile;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.winimpresa.mobile.async.SyncLocalDatabase;
import com.winimpresa.mobile.database.ManagementDB;
import com.winimpresa.mobile.utility.Connectivity;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActivityBase {
	public ProgressDialog progress;
	public ManagementDB  mdb;
	public Button start;
	public LinearLayout viewTwobutton;

	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState, R.layout.activity_main);
    	
        gestionDropBox();
       
    	
    	
    	// SE ESISTE LA SESSIONE PASSA ALLA SCHERAMA DEI BUONI
        if(this.isUserLoggedIn()){
        	this.createIntent();
        }
        
         viewTwobutton 			= (LinearLayout) findViewById(R.id.twobuttonSync);
    	Button       buttonInternet = (Button) findViewById(R.id.syncInternet);
    	Button       buttonLocal = (Button) findViewById(R.id.syncLocal);
    	start = (Button) findViewById(R.id.start);
        
        
    	
    	//CONTROLLO SE HO FATTO L'IMPORT DEI DATA
        
        if(controllFileRead()==false){
        	
        	
        	progress = new ProgressDialog(context);
		    progress.setMessage("Sincronizzazione in corso...");
		   
        	mdb = new ManagementDB(db,context,progress,this);
        	
        	
        	viewTwobutton.setVisibility(View.VISIBLE);
        	
        	switch (GlobalConstants.typeSyncFile) {
			case 1:
				buttonLocal.setVisibility(View.GONE);
				break; 
				
			case 0:
				buttonInternet.setVisibility(View.GONE);
				
			default:
				break;
			}
        	
        	
        	
        }
        //CONTROLLO SE HO FATTO L'IMPORT E NON HA MAI CLICCATO SUL TASTO INIZIA LAVORO
        else{
    	
        	setInitStrat();
    	}
        
        
        
        
        TextView text = (TextView) findViewById(R.id.infoUser);
        text.setText( Html.fromHtml(   "<br>    "+user.getFullName()
			    					+  "<br><i> "+user.getIdUser()  +"</i>"
        							)
        			);
        
        TextView textversion = (TextView) findViewById(R.id.versionApp);
        try {
			textversion.setText("Versione " +context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
						);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      
        
      }

    
    

    
    public void goBuoniActivity(View view){
    	this.createSession();
    	createIntent();
    }
    
    
    public void syncWithLocal(View view){
  
    	SyncLocalDatabase sync  = new SyncLocalDatabase(db,context,progress,this,client);
    	String[] param = {"local",user.getIdUser()};
    	sync.execute(param);
 
    }

    
    
    
    
    
    
  private class LongOperationDropBox extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
          
        	return readFileDrop();
            
        }

        @Override
        protected void onPostExecute(String result) {
        	
        
        	
        	
            if(result.equals("notFileDrop")){
        		
        		 showToast("Il file non è presente sul sistema !");
        		 progress.hide();
        	}
        	if(result.equals("oldFile")){
        		
        		showMessage("oldFile");
        		 progress.hide();
        	}
        	if(result.equals("success")){
        		
        		syncFile();
        	}
        	if(result.equals("error")){
        		File rootPath = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderDropLocal);
        		GlobalConstants.deleteDir(rootPath);
        		File rootPathScaricati = new File(Environment.getExternalStorageDirectory(), GlobalConstants.folderStorageFile);
        		GlobalConstants.deleteDir(rootPathScaricati);
        		 showToast("Per oggi non hai più attività da fare ");
          		 progress.hide();
        		
        	}
        	if(result.equals("errorDrop")){
        		File rootPath = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderDropLocal);
        		GlobalConstants.deleteDir(rootPath);
        		File rootPathScaricati = new File(Environment.getExternalStorageDirectory(), GlobalConstants.folderStorageFile);
        		GlobalConstants.deleteDir(rootPathScaricati);
       		 showToast("Il file delle tue attività non è presenete contatta l'amministratore");
         		 progress.hide();
       		
       	}
        
        
        }
        

        @Override
        protected void onPreExecute() {
        
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    
   
    
    private void syncFile(){
    	SyncLocalDatabase sync  = new SyncLocalDatabase(db,context,progress,this,client);
    	String[] param = {"drop",user.getIdUser()};
    	
    	sync.execute(param);
    	
    }
    
    
    
    public String readFileDrop() {
    
    	
    	File rootPath = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderDropLocal);
    	
    	if(!rootPath.exists()) {
            rootPath.mkdirs();
            
          }
      File rootPathLOG = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderLocal+"/LOG");
    	
    	if(!rootPathLOG.exists()) {
    		rootPathLOG.mkdirs();
    		
    		 File fileLOG = new File(rootPathLOG,"logs.txt");
    		 if(!fileLOG.exists()) {
    			 try {
					fileLOG.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   		}
            
          }
    	
    	File rootPathScaricati = new File(Environment.getExternalStorageDirectory(), GlobalConstants.folderStorageFile);
      	 
    	if(!rootPathScaricati.exists()) {
    		rootPathScaricati.mkdirs();
           
         }
    	 String pathDrop = "/buoni_utenti/"+user.getIdUser();
    	 DbxEntry.WithChildren listing = null;
    	 DbxEntry child  = null;
    	
    	 

			try {
				       listing = client.getMetadataWithChildren(pathDrop,false);  // recupero la lista dei file in drop
				     
				       if(listing!=null){
				    	   if(listing.children.size()==0){
				    		   return "notFileDrop";
				    	   }
				    	  
				    	   
				    	   child = listing.children.get(listing.children.size()-1);
				    	   File fileStorage = new File(rootPathScaricati,child.name );
				    	   File file = new File(rootPath, GlobalConstants.getNameFileDrop(user.getIdUser()));
				    	
				    	   if(!fileStorage.exists()) {
							   try {
								   		fileStorage.createNewFile();
								   		if(!file.exists()) {
								   			file.createNewFile();
								   		}
								   		
								   		FileOutputStream outputStream = null;
										try {
											 outputStream = new FileOutputStream(file);
										} catch (FileNotFoundException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											return "errorDrop";
										}
										try {
											DbxEntry.File downloadedFile= client.getFile(pathDrop+"/"+child.name, null, outputStream);
											
											return "success";
							    		} catch (DbxException e) {
							    			
							    			return "errorDrop";
											// TODO Auto-generated catch block
											
										} catch (IOException e) {
											// TODO Auto-generated catch block
											return "errorDrop";
											
										}
								   		
							   
							   
							   
							   } catch (IOException e) {
								// TODO Auto-generated catch block
								  
								   return "errorDrop";
							   }
							   
				    	   }
				    	   else{
				    		   // se il file è gia stato sviluppato
				    		   return "oldFile";
				    		   
				    	   }
				    	   
				    	   
				    	   
				    	   
				       }else{
				    	   
				    	   
				    	   return "notFileDrop";
				    	   
				       }
				
				       } catch (DbxException e) {
							// TODO Auto-generated catch block
				    	   
							e.printStackTrace();
							return "errorDrop";
							
						}
			
				
					
					
 		
			
    }
    		
    
    
    public void syncWithDrop(View view){
    	
    	//mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
    	if(Connectivity.isConnected(context)){
    		progress.show();
    	LongOperationDropBox a = new LongOperationDropBox();
         a.execute();
    	}else{
    		showToast("La connessione internet non è presente !");
    	}
 
    }
    
    

    
    @Override
	public void showMessage(String msg) {
		// TODO Auto-generated method stub
    	if(msg.equalsIgnoreCase("OK")){
    		progress.hide();
    		showToast("Database syncronizzato con successo ...");
    	
    		setInitStrat();
    	}
    	
    	 	if(msg.equalsIgnoreCase("OldFile")){
    		
    		showToast("Stai ripendendo la vecchia attivita !");
    		setInitStrat();
    	}
    	 	
    	 	else 	if(msg.equalsIgnoreCase("ERROR")){
        		
        		showToast("Errore durate la lettura del file , riprova !");
        		progress.hide();
        	}
	}
    
    public void setInitStrat(){
    	
    	start.setVisibility(View.VISIBLE);
		viewTwobutton.setVisibility(View.GONE);
    	
    }
    
    public void createIntent (){
    	
    	Intent new_page = new Intent(context, BuoniListActivity.class);
		startActivity(new_page);
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		finish();
    	
    }
    
    

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
