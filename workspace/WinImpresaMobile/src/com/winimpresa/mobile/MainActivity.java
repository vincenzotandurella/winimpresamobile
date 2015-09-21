package com.winimpresa.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActivityBase {
    

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState, R.layout.activity_main);
        
        TextView text = (TextView) findViewById(R.id.infoUser);
        text.setText( Html.fromHtml(   "<br>    "+user.getFullName()
			    					+  "<br><i> "+user.getIdUser()  +"</i>"
        							)
        			);
        		
        			
        
       
        
    }

    public void goBuoniActivity(View view){
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