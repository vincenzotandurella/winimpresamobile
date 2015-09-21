package it.winimpresa.poc.dropbox;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout container;
	private DropboxAPI<AndroidAuthSession> dropboxApi;
	private boolean isUserLoggedIn;
	private Button loginBtn;
	private Button uploadFileBtn;
	private Button downloadFileBtn;
	private Button listFilesBtn;
	
	private final static String DROPBOX_FILE_DIR = "/AndroidDropboxImplementationExample/";
	private final static String DROPBOX_NAME = "dropbox_prefs";
	private final static String ACCESS_KEY = "buo0x8gngt201lx";
	private final static String ACCESS_SECRET = "plze5jbfqhkfa6a";
	private final static AccessType ACCESS_TYPE = AccessType.DROPBOX;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		uploadFileBtn = (Button) findViewById(R.id.uploadFileBtn);
		downloadFileBtn =(Button) findViewById(R.id.downloadFileBtn);
		uploadFileBtn.setOnClickListener(this);
		downloadFileBtn.setOnClickListener(this);
		listFilesBtn = (Button) findViewById(R.id.listFilesBtn);
		listFilesBtn.setOnClickListener(this);
		container = (LinearLayout) findViewById(R.id.container_files);
		
		loggedIn(false);

		AppKeyPair appKeyPair = new AppKeyPair(ACCESS_KEY, ACCESS_SECRET);
		AndroidAuthSession session;
		
		SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
		String key = prefs.getString(ACCESS_KEY, null);
		String secret = prefs.getString(ACCESS_SECRET, null);

		if (key != null && secret != null) {
			AccessTokenPair token = new AccessTokenPair(key, secret);
			session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, token);
		} else {
			session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
		}

		dropboxApi = new DropboxAPI<AndroidAuthSession>(session);
	}

	@Override
	protected void onResume() {
		super.onResume();

		AndroidAuthSession session = dropboxApi.getSession();
		if (session.authenticationSuccessful()) {
			try {
				session.finishAuthentication();

				TokenPair tokens = session.getAccessTokenPair();
				SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
				Editor editor = prefs.edit();
				editor.putString(ACCESS_KEY, tokens.key);
				editor.putString(ACCESS_SECRET, tokens.secret);
				editor.commit();

				loggedIn(true);
			} catch (IllegalStateException e) {
				Toast.makeText(this, "Error during Dropbox authentication",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private final Handler handler = new Handler() {
		public void handleMessage(Message message) {
			ArrayList<String> result = message.getData().getStringArrayList("data");
			
			for (String fileName : result) {
				TextView textView = new TextView(MainActivity.this);
				textView.setText(fileName);
				container.addView(textView);
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			if (isUserLoggedIn) {
				dropboxApi.getSession().unlink();
				loggedIn(false);
			} else {
				dropboxApi.getSession().startAuthentication(MainActivity.this);
			}
			break;
		case R.id.uploadFileBtn:
			UploadFile uploadFile = new UploadFile(this, dropboxApi,
					DROPBOX_FILE_DIR);
			uploadFile.execute();
			break;
		case R.id.listFilesBtn:
			String pathLocal="/storage/emulated/0";//MainActivity.this.getFilesDir().getPath().toString();
			ListFiles listFiles = new ListFiles(dropboxApi,pathLocal, DROPBOX_FILE_DIR,
					handler);
			listFiles.execute();
			break;
		case R.id.downloadFileBtn:
			
			break;
		default:
			break;
		}
	}
	private void download(){
		
	}
	

	// Read text from file
	public void ReadBtn() {
		//reading text from file
		try {
			FileInputStream fileIn=openFileInput("myFile");
			InputStreamReader InputRead= new InputStreamReader(fileIn);
			
			char[] inputBuffer= new char[100];
			String s="";
			int charRead;
			
			while ((charRead=InputRead.read(inputBuffer))>0) {
				// char to string conversion
				String readstring=String.copyValueOf(inputBuffer,0,charRead);
				s +=readstring;					
			}
			InputRead.close();
			Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 
	
	private void loggedIn(boolean userLoggedIn) {
		isUserLoggedIn = userLoggedIn;
		uploadFileBtn.setEnabled(userLoggedIn);
		uploadFileBtn.setBackgroundColor(userLoggedIn ? Color.BLUE : Color.GRAY);
		listFilesBtn.setEnabled(userLoggedIn);
		listFilesBtn.setBackgroundColor(userLoggedIn ? Color.BLUE : Color.GRAY);
		loginBtn.setText(userLoggedIn ? "Logout" : "Log in");
	}
}