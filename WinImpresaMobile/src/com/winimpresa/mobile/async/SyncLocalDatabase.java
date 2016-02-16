package com.winimpresa.mobile.async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.database.ManagementDB;
import com.winimpresa.mobile.interfaces.ActivityInterface;
import com.winimpresa.mobile.utility.Connectivity;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;

public class SyncLocalDatabase extends AsyncTask<String, Void, Boolean> {
	
    private Context ctx;
	private ProgressDialog 	progress;
    private ManagementDB    mdb;
	private ActivityInterface acty;
	private String type;
	private DbxClient client;
	private String user;
	
	public SyncLocalDatabase (SQLiteDatabase db, Context ctx,ProgressDialog 	progress,ActivityBase base,DbxClient client){
		this.progress = progress;
		mdb = new ManagementDB(db,ctx,progress,base);
		this.progress.show();
		this.ctx= ctx;
		this.client=client;
		acty = base;
	
	}
	
	
    @Override
    protected Boolean doInBackground(String... params) {
     
    	
    	type = params[0];
    	user= params[1];
        return mdb.syncData(params[0],params[1]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
    	
    	if(result){
    	this.progress.hide();   	
    	acty.showMessage("OK");
    	
    	
    	}
    	else{
    		if(type.equals("drop")){
    		File rootPath = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderDropLocal);
    		GlobalConstants.deleteDir(rootPath);
    		File rootPathScaricati = new File(Environment.getExternalStorageDirectory(), GlobalConstants.folderStorageFile);
    		GlobalConstants.deleteDir(rootPathScaricati);
    		LongOperationDropBox log= new LongOperationDropBox();
    		log.execute();
    		}
    		acty.showMessage("ERROR");
    	}
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
    
    private class LongOperationDropBox extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
          
        	 File rootPathLOG = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderLocal+"/LOG");
   		  File fileLOG = new File(rootPathLOG,"logs.txt");
   		  try {
				FileInputStream inputStream = new FileInputStream(fileLOG);
				if(Connectivity.isConnected(ctx)){
					 try {
						DbxEntry.File uploadedFile = client.uploadFile("/LOG/logs_"+user+".txt",
						       DbxWriteMode.add(), fileLOG.length(), inputStream);
					} catch (DbxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		  return "";
            
        }

        @Override
        protected void onPostExecute(String result) {
        	
        	
        }
        

        @Override
        protected void onPreExecute() {
        
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
 
}



