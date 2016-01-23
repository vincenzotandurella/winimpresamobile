package com.winimpresa.mobile.async;

import java.io.File;

import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.database.ManagementDB;
import com.winimpresa.mobile.interfaces.ActivityInterface;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;

public class SyncLocalDatabase extends AsyncTask<String, Void, Boolean> {
	

	private ProgressDialog 	progress;
    private ManagementDB    mdb;
	private ActivityInterface acty;
	private String type;
	
	public SyncLocalDatabase (SQLiteDatabase db, Context ctx,ProgressDialog 	progress,ActivityBase base){
		this.progress = progress;
		mdb = new ManagementDB(db,ctx,progress,base);
		this.progress.show();
		
		acty = base;
	}
	
	
    @Override
    protected Boolean doInBackground(String... params) {
     
    	
    	type = params[0];
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
 
}

