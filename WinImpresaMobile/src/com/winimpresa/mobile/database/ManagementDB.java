package com.winimpresa.mobile.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

public class ManagementDB {

	public SQLiteDatabase ourDatabase;
	public Context ctx;
	public ProgressDialog progress;
	public ActivityBase base;
	private String userId;

	public ManagementDB(SQLiteDatabase db, Context ctx,
			ProgressDialog progress, ActivityBase base) {

		ourDatabase = db;
		this.ctx = ctx;

		this.base = base;

	}

	public Cursor showsTables() {

		String mySql = " SELECT name FROM sqlite_master "
				+ " WHERE type='table'  ";

		return ourDatabase.rawQuery(mySql, null);

	}

	public boolean syncData(String typeSync,String user) {
		boolean flag = false;
		userId = user;
		createTableLog();
		if(typeSync.equalsIgnoreCase("local")){
			flag = deleteAllTable();
		}
		
		if(typeSync.equalsIgnoreCase("drop")){
			flag = insertData();
		}
		
		if (flag == true) {
			base.setStorageFileName(GlobalConstants.getNameFileDrop(userId));
		}

		return flag;
		

		

	}
	
	
	
	public boolean relaseFile(String user) {
		boolean flag = false;
		
		File rootPath = new File(Environment.getExternalStorageDirectory(), GlobalConstants.pathFolderDropLocalReleaase);
		if(!rootPath.exists()) {
	           rootPath.mkdirs();
	           
	         }
		
		File file = new File(rootPath,GlobalConstants.getNameFileDropRelease(user)+"");
		 if(!file.exists()) {
    		 try {
				file.createNewFile();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
			
				e.printStackTrace();
			}
		 }
		  FileOutputStream is = null;
		try {
			is = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          OutputStreamWriter osw = new OutputStreamWriter(is);    
          Writer w = new BufferedWriter(osw);
          try {
        		 String query 	= "SELECT * FROM "+GlobalConstants.tabellaLog;
        			Cursor cursor = ourDatabase.rawQuery(query, null);
        		 while (cursor.moveToNext()) {
        				
        			 w.write(cursor.getString(cursor.getColumnIndex("testo"))+"\n");
        			
        			}

        			cursor.close();
        		 
			
			flag=true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          try {
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
		

		

	}

	private boolean deleteAllTable() {

		Cursor c = showsTables();

		ArrayList<String> todoItems = new ArrayList<String>();
		if (c.moveToFirst()) {
			do {
				todoItems.add(c.getString(0));

			} while (c.moveToNext());
		}
		if (todoItems.size() >= 0) {
			for (int i = 0; i < todoItems.size(); i++) {

				String table = todoItems.get(i);

				// deleteQuery(table);

			}

		}

		return true;// insertData();
	}
	
	private void createTableLog(){
		 String query 	= "DROP TABLE  IF EXISTS "+GlobalConstants.tabellaLog;
		 String query2 	= "CREATE TABLE "+GlobalConstants.tabellaLog+" (id INTEGER PRIMARY KEY AUTOINCREMENT, testo TEXT) ";
		 ourDatabase.execSQL(query); 
		 ourDatabase.execSQL(query2); 
	}

	private void deleteQuery(String table) {

		String query = "delete  from  '" + table + "';";

		ourDatabase.execSQL(query);

	}

	private boolean insertData() {

		String line;
		String incompleteLine = "";

		try {
			FileInputStream file = new FileInputStream(Environment
					.getExternalStorageDirectory().getAbsolutePath()
					+ File.separator
					+ GlobalConstants.pathFolderDropLocal
					+ "/"+GlobalConstants.getNameFileDrop(userId));
        
			InputStream in = file;
			if (in != null) {
				// prepare the file for reading
				InputStreamReader input = new InputStreamReader(in);
				BufferedReader buffreader = new BufferedReader(input);

				while ((line = buffreader.readLine()) != null) {

					if (line.indexOf("-") == 0 || line.indexOf("/") == 0
							|| line.indexOf("SET") == 0) {
						continue;
					}

					if (line.contains(";")) {
						incompleteLine = incompleteLine + line;
						
						ourDatabase.execSQL(incompleteLine);
						incompleteLine = "";

					} else {
						incompleteLine = incompleteLine + line;
					}

					in.close();
						System.out.println("Sto qui");
					return true;
				}
				Toast.makeText(ctx, "wee", 50000000).show();
			} else {
			}
		} catch (Exception e) {
			Toast.makeText(ctx, e.toString() + e.getMessage(), 50000000).show();
			return false;
		}
		return false;

	}

}
