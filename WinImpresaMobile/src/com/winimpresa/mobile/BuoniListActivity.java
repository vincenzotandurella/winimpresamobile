package com.winimpresa.mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import com.winimpresa.mobile.database.MonitoraggioTable;
import com.winimpresa.mobile.to.Buono;
import com.winimpresa.mobile.to.Monitoraggio;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;


public class BuoniListActivity extends ActivityBase {

private static final String TAG_LOG = MainActivity.class.getName();
private ListView listBuoni;
private ArrayList<HashMap<String, Object>> dataBuoni ;
private  SimpleAdapter adapter;
private ArrayList<Monitoraggio> monitoriaggioList = new ArrayList<Monitoraggio>();
private AlertDialog.Builder alertDialogBuilder;
private ImageButton buttonData;
private ImageButton reloadList;
private boolean logTouch=false;
private MonitoraggioTable mont;
private EditText textData;
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);
String[] from={"ev","data","descrizione","cliente","commessa","comune","area"};

int[] to={R.id.ev,R.id.data,R.id.descrizione,R.id.cliente,R.id.commessa,R.id.comune,R.id.area};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState, R.layout.activity_listbuoni);
		
		ActionBar ab = getActionBar();
		ab.setTitle(getResources().getString(R.string.listBuoni));
		ab.setSubtitle(user.getFullName() + " / "+user.getIdUser());
		getActionBar().setDisplayShowHomeEnabled(false);
		
		
		mont  = new MonitoraggioTable(context, db);
		monitoriaggioList = mont.selectAllMonitoraggio(monitoriaggioList);
		alertDialogBuilder 		= new AlertDialog.Builder(this);
	
	       
	       
	     
	     listBuoni =  (ListView) findViewById(R.id.buoniListView);
	     buttonData = (ImageButton) findViewById(R.id.buttonData);
	     reloadList = (ImageButton) findViewById(R.id.reloadList);
	     textData	= (EditText) findViewById(R.id.labelData);
	     setListAdapter();
	     
	     buttonData.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					
				        
					 DatePickerDialog dialogData = new DatePickerDialog(context,
							new DatePickerDialog.OnDateSetListener() {

								@Override
								public void onDateSet(DatePicker view,
										int yearFinsh, int monthOfYear,
										int dayOfMonth) {
									// TODO Auto-generated method stub
									textData.setText("Data: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+yearFinsh );
									showToast("Ricerca per data...");
									monitoriaggioList = new ArrayList<Monitoraggio>();
									monitoriaggioList = mont.selectAllMonitoraggioData(monitoriaggioList,GlobalConstants.dataPerFiltro(yearFinsh,(monthOfYear+1),dayOfMonth));
									setListAdapter();
									 year = yearFinsh;
									 month = monthOfYear;
									 day = dayOfMonth;
									  
									
								}
						 
					 }, year, month, day);
					 
					 dialogData.show();
					 	
				}
			});
	     
	     reloadList.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if(!textData.getText().toString().equals("")){
					textData.setText("");
					monitoriaggioList = new ArrayList<Monitoraggio>();
					monitoriaggioList = mont.selectAllMonitoraggio(monitoriaggioList);
					setListAdapter();
					}
				}
	     });
	     listBuoni.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if (logTouch == true) {
						logTouch = false;
						return;
					}
					
					 if(monitoriaggioList.get(position).isStatoEvaso()){
						 showToast("Il buono è stato evaso !");
						 return;
					 }
					
					 
					 Intent page_buono= new Intent(context, BuonoActivity.class);
					 page_buono.putExtra(GlobalConstants.IDBUONO, monitoriaggioList.get(position).getId_monitoraggio());
					
				 
					
					 startActivity(page_buono);
					 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}
	    	});
	     
	     listBuoni.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> arg0, View v,
						int index, long arg3) {

					logTouch = true;
					dialogBoxStatus(index);
					return false;
				}
			});
	
	     
	}
	
   
	private void setListAdapter(){
		
		 dataBuoni = new ArrayList<HashMap<String,Object>>();
			
		   
		   for(int i=0;i<monitoriaggioList.size();i++){
	           Monitoraggio mtg= monitoriaggioList.get(i);
	           
	           HashMap<String,Object> buoniMap=new HashMap<String, Object>();
	           
	           buoniMap.put("ev",mtg.getEvaso()); 
	           buoniMap.put("data", mtg.getData()); 
	           buoniMap.put("descrizione",mtg.getDescrizione());
	           buoniMap.put("cliente",mtg.getCliente());
	           buoniMap.put("commessa",mtg.getCommessa_n());
	           buoniMap.put("comune",mtg.getCitta());
	           buoniMap.put("area",mtg.getArea());
	           dataBuoni.add(buoniMap); 
		   		}
  
   
   adapter =new SimpleAdapter(
           getApplicationContext(),
           dataBuoni,
           R.layout.buono, 
           from,
           to);
   
   listBuoni.setAdapter(adapter);
		
	}
	
	
	public void addNewBuono(View view){
		showToast("Funzionalià ancora non disponibile");
		
		/* Buono newBuono = new Buono("Buono nuovo", 10, R.drawable.init);
	     HashMap<String,Object> buoniMap2=new HashMap<String, Object>();
	     buoniMap2.put("status",newBuono.getStatus()); 
         buoniMap2.put("titolo", newBuono.getTitolo()); 
         buoniMap2.put("tipologia",newBuono.getTipologia());
         monitoriaggioList.add(newBuono);
         dataBuoni.add(buoniMap2);
	     adapter.notifyDataSetChanged();
	     //listBuoni.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	     listBuoni.smoothScrollToPosition(dataBuoni.size());
	     showToast(getResources().getString(R.string.msgSuccessBuono));
	     */
	     
	
	}
public void dialogBoxStatus(final int pos) {

	 final Monitoraggio m = monitoriaggioList.get(pos);
	 String     text = "Evaso ";
	 
	 
	 if(m.isStatoEvaso())
		 text = "Non evaso";
		 
	
	
		alertDialogBuilder.setMessage("Vuoi modificare lo stato del buono?");
		alertDialogBuilder.setPositiveButton(text,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						 if(m.isStatoEvaso()){
							 
							 m.setStatoEvaso(false);
							 m.setEvaso(R.drawable.zero);
						 }else{
							 m.setStatoEvaso(true);
							 m.setEvaso(R.drawable.uno);
						 }
						
						if(mont.updateStatoMonitoraggio(m)){
						     monitoriaggioList.set(pos, m);
							dataBuoni.get(pos).put("ev", m.getEvaso());
							adapter.notifyDataSetChanged();
							 logTouch= false;
						}else{
							showToast("Errore durante la modifica !");
						}
						

					}
				});
		alertDialogBuilder.setNegativeButton("Annulla",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						 logTouch= false;
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

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
		if (id == R.id.action_rilascia) {
			goRilascia();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void goRilascia(){
		 Intent page_rilascia= new Intent(context, RilascioActivity.class);
			
		 startActivity(page_rilascia);
		 
		 finish();
	}
}
