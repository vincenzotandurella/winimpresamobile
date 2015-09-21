package com.winimpresa.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.winimpresa.mobile.utility.Buono;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class BuoniListActivity extends ActivityBase {

private static final String TAG_LOG = MainActivity.class.getName();
private ListView listBuoni;
private ArrayList<HashMap<String, Object>> dataBuoni ;
private  SimpleAdapter adapter;
private ArrayList<Buono> buoniList = new ArrayList<Buono>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState, R.layout.activity_listbuoni);
		
		ActionBar ab = getActionBar();
		ab.setTitle(getResources().getString(R.string.listBuoni));
		ab.setSubtitle(user.getFullName() + " / "+user.getIdUser());
		getActionBar().setDisplayShowHomeEnabled(false);
		
		
		
		
        
		Buono [] buoni = {
							new Buono (" Buono con rosso"  ,1,R.drawable.init),
							new Buono (" Buono con verde"  ,1,R.drawable.finish),
							new Buono (" Buono con arancio",1,R.drawable.inter)
						  };
		
	
		
		Random r=new Random();
	        for(int i=0;i<30;i++){
	        	buoniList.add(buoni[r.nextInt(buoni.length)]);
	        }
	       
	        dataBuoni = new ArrayList<HashMap<String,Object>>();
		
	   
			   for(int i=0;i<buoniList.size();i++){
		           Buono b=buoniList.get(i);
		           
		           HashMap<String,Object> buoniMap=new HashMap<String, Object>();
		           
		           buoniMap.put("status",b.getStatus()); 
		           buoniMap.put("titolo", b.getTitolo()); 
		           buoniMap.put("tipologia",b.getTipologia());
		           dataBuoni.add(buoniMap); 
			   		}
	     String[] from={"status","titolo","tipologia"};
	     
	     int[] to={R.id.status,R.id.titolo,R.id.tipologia};
	     
	     adapter =new SimpleAdapter(
                 getApplicationContext(),
                 dataBuoni,
                 R.layout.buono, 
                 from,
                 to);
	     
	     listBuoni =  (ListView) findViewById(R.id.buoniListView);
	     listBuoni.setAdapter(adapter);
	     
	     
	     listBuoni.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					/*HashMap<String,Object> buono = dataBuoni.get(0);
					
					buono.put("titolo", "Modificato");
					dataBuoni.set(0, buono);
					*/
					Log.d(TAG_LOG,"Element selected "+ buoniList.get(position).getTitolo());
					 Intent page_buono= new Intent(context, BuonoActivity.class);
				     /* page.putExtra("TIPO", mList.get(position).tipo_attivita);
				      page.putExtra("ID_ATTIVITA", mList.get(position).id);
				      page.putExtra("POSITION_ARRAY",position);
				      startActivityForResult(page,20);
					*/
					 startActivity(page_buono);
					 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}
	    	});
	     
	}
	
   
	
	public void addNewBuono(View view){
		 Buono newBuono = new Buono("Buono nuovo", 10, R.drawable.init);
	     HashMap<String,Object> buoniMap2=new HashMap<String, Object>();
	     buoniMap2.put("status",newBuono.getStatus()); 
         buoniMap2.put("titolo", newBuono.getTitolo()); 
         buoniMap2.put("tipologia",newBuono.getTipologia());
         buoniList.add(newBuono);
         dataBuoni.add(buoniMap2);
	     adapter.notifyDataSetChanged();
	     //listBuoni.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	     listBuoni.smoothScrollToPosition(dataBuoni.size());
	     showToast(getResources().getString(R.string.msgSuccessBuono));
	     
	     
	
	}
	
	
	public void showToast(String msg) {
		Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		t.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buoni, menu);
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