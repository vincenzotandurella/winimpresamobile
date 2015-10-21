package com.winimpresa.mobile.async;

import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.database.ManagementDB;
import com.winimpresa.mobile.interfaces.ActivityInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class SyncLocalDatabase extends AsyncTask<Integer, Void, Boolean> {
	

	private ProgressDialog 	progress;
    private ManagementDB    mdb;
	private ActivityInterface acty;
	
	public SyncLocalDatabase (SQLiteDatabase db, Context ctx,ProgressDialog 	progress,ActivityBase base){
		this.progress = progress;
		mdb = new ManagementDB(db,ctx,progress,base);
		this.progress.show();
		acty = base;
	}
	
	
    @Override
    protected Boolean doInBackground(Integer... params) {
     
    	
    	
        return mdb.syncData(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
    	
    	if(result){
    	this.progress.hide();   	
    	acty.showMessage("OK");
    	
    	
    	}
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

