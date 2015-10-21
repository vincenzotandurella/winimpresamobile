package  com.winimpresa.mobile.database;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import com.winimpresa.mobile.utility.GlobalConstants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

public class AssetDatabaseOpenHelper {

   

    private Context context;

    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(GlobalConstants.filedatabase);

        if (!dbFile.exists()) {
            try {
                SQLiteDatabase checkDB = context.openOrCreateDatabase(GlobalConstants.filedatabase, context.MODE_PRIVATE, null);
                if(checkDB != null){
                			
                    checkDB.close();

            }
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
       
        
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE );
    }

    
    
    
    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(GlobalConstants.filedatabase);
        
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[10000000];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }
    
    
    
    
    public void bk(){
    	
    	try {
    	   // File sd = new File(Environment.getExternalStorageDirectory(),"PIPPOs");
    		File sd = new File(System.getenv("SECONDARY_STORAGE"), "SABATINO_IMMA");
    	    sd.mkdirs();
    	    File data = Environment.getDataDirectory();

    	    if (sd.canWrite()) {
    	        String currentDBPath = "//data//"+  context.getPackageName() +"//databases//"+GlobalConstants.filedatabase;
    	        String backupDBPath = GlobalConstants.filedatabase;
    	        
    	        File currentDB = new File(data, currentDBPath);
    	        File backupDB = new File(sd, backupDBPath);

    	        FileChannel src = new FileInputStream(currentDB).getChannel();
    	        FileChannel dst = new FileOutputStream(backupDB).getChannel();
    	        dst.transferFrom(src, 0, src.size());
    	        src.close();
    	        dst.close();
    	        Toast.makeText(context, sd.getPath(), Toast.LENGTH_LONG).show();
    	    }
    	} catch (Exception e) {
    	    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
    	}
    	
    } 

}
