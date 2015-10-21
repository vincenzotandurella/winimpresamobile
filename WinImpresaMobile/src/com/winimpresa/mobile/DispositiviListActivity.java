package com.winimpresa.mobile;

import java.util.ArrayList;
import java.util.HashMap;

import com.winimpresa.mobile.database.MonitoraggioVociTable;
import com.winimpresa.mobile.to.Monitoraggio;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DispositiviListActivity extends ActivityBase {
	private static final String TAG_LOG = MainActivity.class.getName();
	private long current_id_buono=0;
	private int position_current;
	private int type_scheda ;
	private Bundle bundle;
	private ArrayList<HashMap<String, Object>> dataDispositivi;
	private ArrayList<MonitoriaggioVoci> allMonitoriaggioVoci = new ArrayList<MonitoriaggioVoci>();
	private  SimpleAdapter adapter;
	private ListView lisDispositivi;
	private MonitoraggioVociTable tableVoci;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_dispositivi_list);
		ActionBar ab = getActionBar();
	    ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		ab.setIcon( new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ab.setSubtitle(user.getFullName() + " / "+user.getIdUser());
		bundle  = getIntent().getExtras();
		if (bundle != null) {
		       
	    	
       	  Log.d(TAG_LOG,"TYPE_SCHEDA "+ bundle.getInt(GlobalConstants.TYPESCHEDA));
       	   current_id_buono = bundle.getLong(GlobalConstants.IDBUONO);
       	  type_scheda = bundle.getInt(GlobalConstants.TYPESCHEDA);
		}
		
		
       	 tableVoci = new MonitoraggioVociTable(context,db);
       	allMonitoriaggioVoci = tableVoci.getAllDispoisitivi(allMonitoriaggioVoci, current_id_buono);
       	lisDispositivi = (ListView) findViewById(R.id.listViewDispositivi);
       	
       	dataDispositivi = new ArrayList<HashMap<String,Object>>();
       	
       	for(int i=0;i<allMonitoriaggioVoci.size();i++){
	           MonitoriaggioVoci mtg= allMonitoriaggioVoci.get(i);
	           
	           HashMap<String,Object> dispositivoMap=new HashMap<String, Object>();
	           
	           dispositivoMap.put("ambiente",mtg.getAmbiente()); 
	           dispositivoMap.put("settore",mtg.getSettore());
	           dispositivoMap.put("tipoDisp",mtg.getErogatore());
	           dispositivoMap.put("codDisp",mtg.getCod_dispositivo());
	           dispositivoMap.put("siglaDisp",mtg.getSigla_dispositivo());
	           
	           dataDispositivi.add(dispositivoMap); 
		   		}
       	
            String[] from={"ambiente","settore","tipoDisp","codDisp","siglaDisp"};
 	     
	        int[] to={R.id.ambiente,R.id.settore,R.id.tipoDisp,R.id.codDisp,R.id.siglaDisp};
       	
        adapter =new SimpleAdapter(
                getApplicationContext(),
                dataDispositivi,
                R.layout.dispositivo_general, 
                from,
                to);
       	 
       
		
		lisDispositivi.setAdapter(adapter);
		
		
		lisDispositivi.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
			
					 Log.d(TAG_LOG,"Element selected "+ allMonitoriaggioVoci.get(position).getId_voce());
					 position_current = position;
					 scattaQR_CODE();
				    //  startActivityForResult(page_buono,20);
					
					 
				}
	    	});
		
		
	}
	
	
	public void scattaQR_CODE(){
		  String packageString = "com.winimpresa.mobile";
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.setPackage(packageString);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			 startActivityForResult(intent, 0);	
	  }
			
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		        if (requestCode == 0) {
		            
		            if (resultCode == RESULT_OK) {
		            	//tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
		            	//tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
		            	//Toast.makeText(this, "Selected position: " + intent.getStringExtra("SCAN_RESULT"), Toast.LENGTH_SHORT).show();
		            	controllerQrcode(intent.getStringExtra("SCAN_RESULT"));
		            	//setCodQRcode(intent.getStringExtra("SCAN_RESULT"));
		            } else if (resultCode == RESULT_CANCELED) {
		            	//tvStatus.setText("Press a button to start a scan.");
		                //tvResult.setText("Scan cancelled.");
		            	
		            }
		        }
		    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dispositivi_list, menu);
		
		return true;
	}
	
	
	private void controllerQrcode(String qrcoce){
		
		MonitoriaggioVoci mon = allMonitoriaggioVoci.get(position_current);
		
		// CASO CHE NON ESISTE IL QRCODE NELLA TABELLA
		if(mon.getQrcode() == null){
			mon.setQrcode(qrcoce);
        	

			Boolean flag=  tableVoci.updateDispositivoQrcode(mon);
			if(flag){
			 showToast("QrCode inserito correttamente ");
			 goScheda();
			 
			}
		}
		else if(!mon.getQrcode().equals(qrcoce)){
			dialogBox(qrcoce);
		}
		else if(mon.getQrcode().equals(qrcoce)){
			//showMessage("Qrcode uguali ok");
			goScheda();
		}
		
		
		
	}
	
	public void goScheda(){
		Intent page_scheda= new Intent(context, SchedaActivity.class);
		page_scheda.putExtra(GlobalConstants.TYPESCHEDA, type_scheda );
		page_scheda.putExtra(GlobalConstants.IDBUONO, current_id_buono );
		page_scheda.putExtra(GlobalConstants.IDVOCE, allMonitoriaggioVoci.get(position_current).getId_voce() );

	    //  startActivityForResult(page_buono,20);
		
		 startActivity(page_scheda);
	}
	
	public void backBuono(){
		
		
		   /*   NavUtils.navigateUpFromSameTask(this);
			  
			 overridePendingTransition( R.anim.push_down_in, R.anim.push_down_out);
			 Intent result = new Intent();
		     result.putExtra(GlobalConstants.IDBUONO, current_id_buono);
		     setResult(Activity.RESULT_OK, result);
			*/ 
			 finish();
	   }
	
	public void dialogBox(final String qrcode) {
		
	    AlertDialog.Builder alertDialogBuilder = new        AlertDialog.Builder(this);
	    alertDialogBuilder.setMessage("Il qrcode non coincide con il dispositivo, vuoi sostituire?");
	    alertDialogBuilder.setPositiveButton("Sostituisci",
	        new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface arg0, int arg1) {
	        	MonitoriaggioVoci mon = allMonitoriaggioVoci.get(position_current);
	        	mon.setQrcode(qrcode);
	        	

				Boolean flag=  tableVoci.updateDispositivoQrcode(mon);
				if(flag){
				 showToast("QrCode sostituito correttamente ");
				 goScheda();
				 
				}
	        }
	    });
	    alertDialogBuilder.setNegativeButton("Esci",
	        new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface arg0, int arg1) {
	        		
	        	
	        }
	    });

	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.show();
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else{
			//showToast("current id "+current_id_buono);
			backBuono();
		}
		return super.onOptionsItemSelected(item);
	}
}
