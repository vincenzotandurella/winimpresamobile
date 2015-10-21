package com.winimpresa.mobile;



import com.winimpresa.mobile.async.SyncLocalDatabase;
import com.winimpresa.mobile.database.ManagementDB;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActivityBase {
	public ProgressDialog progress;
	public ManagementDB  mdb;
	public Button start;
	public LinearLayout viewTwobutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState, R.layout.activity_main);
        
        // SE ESISTE LA SESSIONE PASSA ALLA SCHERAMA DEI BUONI
        if(this.isUserLoggedIn()){
        	this.createIntent();
        }
        
         viewTwobutton 			= (LinearLayout) findViewById(R.id.twobuttonSync);
    	Button       buttonInternet = (Button) findViewById(R.id.syncInternet);
    	Button       buttonLocal = (Button) findViewById(R.id.syncLocal);
    	start = (Button) findViewById(R.id.start);
        
        
    	
    	//CONTROLLO SE HO FATTO L'IMPORT DEI DATA
        
        if(controllFileRead()==false){
        	
        	
        	progress = new ProgressDialog(context);
		    progress.setMessage("Sincronizzazione in corso...");
		   
        	mdb = new ManagementDB(db,context,progress,this);
        	
        	
        	viewTwobutton.setVisibility(View.VISIBLE);
        	
        	switch (GlobalConstants.typeSyncFile) {
			case 1:
				buttonLocal.setVisibility(View.GONE);
				break; 
				
			case 0:
				buttonInternet.setVisibility(View.GONE);
				
			default:
				break;
			}
        	
        	
        	
        }
        //CONTROLLO SE HO FATTO L'IMPORT E NON HA MAI CLICCATO SUL TASTO INIZIA LAVORO
        else{
    	
        	setInitStrat();
    	}
        
        
        
        
        TextView text = (TextView) findViewById(R.id.infoUser);
        text.setText( Html.fromHtml(   "<br>    "+user.getFullName()
			    					+  "<br><i> "+user.getIdUser()  +"</i>"
        							)
        			);
      
        
      }

    
    
    public void goBuoniActivity(View view){
    	this.createSession();
    	createIntent();
    }
    
    
    public void syncWithLocal(View view){
  
    	SyncLocalDatabase sync  = new SyncLocalDatabase(db,context,progress,this);
    	sync.execute(0);
 
    }
    
    @Override
	public void showMessage(String msg) {
		// TODO Auto-generated method stub
    	if(msg.equalsIgnoreCase("OK")){
    		showToast("Database syncronizzato con successo ...");
    		showToast("Questa è una demo");
    		setInitStrat();
    	}
	}
    
    public void setInitStrat(){
    	
    	start.setVisibility(View.VISIBLE);
		viewTwobutton.setVisibility(View.GONE);
    	
    }
    
    public void createIntent (){
    	
    	Intent new_page = new Intent(context, BuoniListActivity.class);
		startActivity(new_page);
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		finish();
    	
    }
    
    

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
