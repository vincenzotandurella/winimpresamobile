package com.winimpresa.mobile.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.winimpresa.mobile.ActivityBase;
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

	public boolean syncData(int typeSync) {
		boolean flag = false;
		switch (typeSync) {
		case 0:// local file
			flag = deleteAllTable();
			break;

		default:
			break;
		}

		if (flag == true) {
			base.setStorageFileName("a");
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
					+ GlobalConstants.folderLocalFile
					+ "/insert_db.sql");

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
						System.out.println(incompleteLine);
						ourDatabase.execSQL(incompleteLine);
						incompleteLine = "";

					} else {
						incompleteLine = incompleteLine + line;
					}

					in.close();

					return true;
				}
				// Toast.makeText(this, res, Toast.LENGTH_LONG).show();
			} else {
			}
		} catch (Exception e) {
			Toast.makeText(ctx, e.toString() + e.getMessage(), 50000000).show();
			return false;
		}
		return false;

	}

}
