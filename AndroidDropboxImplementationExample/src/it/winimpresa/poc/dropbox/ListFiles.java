package it.winimpresa.poc.dropbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ListFiles extends AsyncTask<Void, Void, ArrayList<String>> {

	private DropboxAPI<?> dropboxApi;
	private String path;
	private Handler handler;
    private String pathLocal;
    
	public ListFiles(DropboxAPI<?> dropboxApi, String pathLocal,String path, Handler handler) {
		this.dropboxApi = dropboxApi;
		this.path = path;
		this.pathLocal=pathLocal;
		this.handler = handler;
	}

	@Override
	protected ArrayList<String> doInBackground(Void... params) {
		ArrayList<String> files = new ArrayList<String>();
		try {
			Entry directory = dropboxApi.metadata(path, 1000, null, true, null);
			for (Entry entry : directory.contents) {
				files.add(entry.fileName());
				downloadDropboxFile(entry);
			}
		} catch (DropboxException e) {
			e.printStackTrace();
		}
		return files;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		Message message = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("data", result);
		message.setData(bundle);
		handler.sendMessage(message);
	}

	private boolean downloadDropboxFile(Entry fileSelected) {
		File dir = new File(Utils.getPath());
		if (!dir.exists())
			dir.mkdirs();
		try {
			
			File localFile = new File( pathLocal,"/" + fileSelected.fileName());

			if (!localFile.exists()) {
				localFile.createNewFile();
				copy(fileSelected, localFile);

			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	
	
	private void copy(final Entry fileSelected, final File localFile) {
		
				try {
					
					FileOutputStream outputStream = new FileOutputStream(localFile);
					DropboxFileInfo info = dropboxApi.getFile(fileSelected.path, null, outputStream, null);
					Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
					outputStream.flush();
					outputStream.close();

					InputStream fileInputStream = new FileInputStream(localFile);
					
					
					String line;
				    StringBuilder stringBuilder = new StringBuilder();
			        InputStreamReader inputreader = new InputStreamReader(fileInputStream);
			        BufferedReader bufferedreader = new BufferedReader(inputreader);
			        
			            while (( line = bufferedreader.readLine()) != null) 
			            {
			                stringBuilder.append(line);
			                stringBuilder.append('\n');
			            }
			        bufferedreader.close();
					localFile.delete();
				} catch (DropboxException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}

	

}