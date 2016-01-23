package com.winimpresa.mobile.async;

import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.database.ManagementDB;
import com.winimpresa.mobile.interfaces.ActivityInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class ReleaseLocalDatabase extends AsyncTask<String, Void, Boolean> {
	

	private ProgressDialog 	progress;
    private ManagementDB    mdb;
	private ActivityInterface acty;
	
	public ReleaseLocalDatabase (SQLiteDatabase db, Context ctx,ProgressDialog 	progress,ActivityBase base){
		this.progress = progress;
		mdb = new ManagementDB(db,ctx,progress,base);
		this.progress.show();
		acty = base;
	}
	
	
    @Override
    protected Boolean doInBackground(String... params) {
     
    	
    	
        return mdb.relaseFile(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
    	
    	if(result){
    	//this.progress.hide();   	
    	acty.showMessage("File creato localmente ");
    	acty.realseWithDrop();
    	
    	
    	}
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

