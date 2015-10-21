package com.winimpresa.mobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ConnectionDb {
	public SQLiteDatabase ourDatabase;
	public Context ctx;
	
	
	public ConnectionDb(Context ctx , SQLiteDatabase db){
		this.ourDatabase = db;
		this.ctx = ctx;
	}
}
