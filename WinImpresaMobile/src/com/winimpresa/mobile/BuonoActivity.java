package com.winimpresa.mobile;

import java.util.Arrays;
import java.util.Calendar;





import com.winimpresa.mobile.database.MonitoraggioTable;
import com.winimpresa.mobile.database.MonitoraggioVociTable;
import com.winimpresa.mobile.to.Monitoraggio;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.DialogGlobalBack;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class BuonoActivity extends ActivityBase {
 private static final String TAG_LOG = MainActivity.class.getName();	
 private ImageButton  buttonOraInizio;
 private ImageButton  buttonOraFine;
 private TextView oraInizio;
 private TextView oraFine;
 private TextView data;
 private TextView numeroCommessa;
 private TextView numeroMonitoraggio;
 private TextView tipo_moni;
 private EditText note_intervento;
 private EditText segnalazioni;
 private EditText automezzo;
 private EditText km;
 private EditText comunicazione_operatore;
 private Spinner andamento_lavori;

 private Button  dispositivi;
 private long current_id_buono = 0;
 private Monitoraggio monitoraggio;
 private MonitoraggioTable tableMoni;
 private MonitoraggioVociTable tableMoniVoci;
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_buono);
		
		Bundle bundle = getIntent().getExtras();
		
		if (bundle != null) {
       
    	
        	 Log.d(TAG_LOG,"ID_BUONO "+ bundle.getLong(GlobalConstants.IDBUONO));
        	 current_id_buono = bundle.getLong(GlobalConstants.IDBUONO);
        	
        }

		
		ActionBar ab = getActionBar();
	    ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		ab.setIcon( new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ab.setSubtitle(user.getFullName() + " / "+user.getIdUser());
		
		tableMoni = new MonitoraggioTable(context, db);
		tableMoniVoci = new  MonitoraggioVociTable(context,db);
		monitoraggio = tableMoni.getInfoMonitoraggio(current_id_buono);
		
		
		numeroCommessa   		=    (TextView) findViewById(R.id.numeroCommessa);
		numeroMonitoraggio 		=    (TextView) findViewById(R.id.monitoraggioNumero);
		note_intervento	  	    =    (EditText) findViewById(R.id.noteIntervento);	
		segnalazioni	   		=    (EditText) findViewById(R.id.segnalazioni);
		automezzo				=    (EditText) findViewById(R.id.automezzo);
		km						=    (EditText) findViewById(R.id.km);
		comunicazione_operatore = 	 (EditText) findViewById(R.id.comunicazioneOperatore);
		buttonOraInizio  		=    (ImageButton) findViewById(R.id.buttonOraInizio);
		buttonOraFine    		=    (ImageButton) findViewById(R.id.buttonOraFine);
		oraInizio		 		=    (TextView) findViewById(R.id.oraInizio);
		oraFine		            =    (TextView) findViewById(R.id.oraFine);
		data		            =    (TextView) findViewById(R.id.dataMon);
		tipo_moni				=    (TextView) findViewById(R.id.tipo);
		dispositivi		     	=    (Button)   findViewById(R.id.settori);
		Button magazzino		=    (Button)   findViewById(R.id.magazzino);
		andamento_lavori		=    (Spinner) findViewById(R.id.andamLavori);
		
		setInformation();
		automezzo.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
		
		buttonOraInizio.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					 int [] time= setTimePopup(oraInizio);
		                TimePickerDialog tpd = new TimePickerDialog(context,
		                        new TimePickerDialog.OnTimeSetListener() {
		     
		                            @Override
		                            public void onTimeSet(TimePicker view, int hourOfDay,
		                                    int minute) {
		                                // Display Selected time in textbox
		                               
		                            	if(GlobalConstants.differentTime(GlobalConstants.setIntTime(hourOfDay) + ":" + GlobalConstants.setIntTime(minute),oraFine.getText().toString())<0){
		                            		showToast("L'ora inizio è maggiore dell'ora fine");
			                            	oraFine.setText(GlobalConstants.setIntTime(hourOfDay) + ":" + GlobalConstants.setIntTime(minute) );

		                            		return;
		                            	}
		                            	
		                            	
		                            	oraInizio.setText(GlobalConstants.setIntTime(hourOfDay) + ":" + GlobalConstants.setIntTime(minute) );

		                            }
		                        }, time[0], time[1], false);
		                tpd.show();
				
				}
			});
		
		buttonOraFine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 int [] time= setTimePopup(oraFine);
	                TimePickerDialog tpd = new TimePickerDialog(context,
	                        new TimePickerDialog.OnTimeSetListener() {
	     
	                            @Override
	                            public void onTimeSet(TimePicker view, int hourOfDay,
	                                    int minute) {
	                                // Display Selected time in textbox
	                            	if(GlobalConstants.differentTime(oraInizio.getText().toString(),GlobalConstants.setIntTime(hourOfDay) + ":" + GlobalConstants.setIntTime(minute))<0){
	                            		showToast("L'ora fine è minore dell'ora inizio");
		                            	oraInizio.setText(GlobalConstants.setIntTime(hourOfDay) + ":" + GlobalConstants.setIntTime(minute) );

	                            		return;
	                            	}
	                            	
	                            	oraFine.setText(GlobalConstants.setIntTime(hourOfDay) + ":" + GlobalConstants.setIntTime(minute) );
	                              
	                            }
	                        }, time[0], time[1], false);
	                tpd.show();
			
			}
		});
		
		dispositivi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goDispositivi();
			
			}
		});
		
		magazzino.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogMagazzino();
			
			}
		});
		
	}
   
   
   public int[] setTimePopup(TextView time){
	   String get_time = time.getText().toString();
	   int [] totalTime = new int[2];
	   if(!get_time.equalsIgnoreCase("")){
		   
		   totalTime[0] = Integer.parseInt(get_time.split(":")[0]);
		   totalTime[1] = Integer.parseInt(get_time.split(":")[1]);
	   }
	   else{
		   
		   Calendar mcurrentTime = Calendar.getInstance();
		   totalTime[0] = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		   totalTime[1] = mcurrentTime.get(Calendar.MINUTE);
	   }
	   
	   return totalTime;
	   
   }
   
   
  private void setInformation(){
	  
 	 Log.d(TAG_LOG,"tipo scheda "+monitoraggio.getTipo_scheda() );

	  numeroCommessa.setText(" "+monitoraggio.getCommessa_n());
	  numeroMonitoraggio.setText(""+monitoraggio.getMonitoraggio_n());
	  data.setText(""+monitoraggio.getData());
	  tipo_moni.setText(monitoraggio.getTipo_monitoraggio());
	  note_intervento.setText(monitoraggio.getNoteIntervento());
	  oraFine.setText(monitoraggio.getOra_fine());
	  oraInizio.setText(monitoraggio.getOra_inizio());
	  segnalazioni.setText(monitoraggio.getSegnalazioni_anomalie());
	  automezzo.setText(monitoraggio.getAutomezzo());
	  km.setText(""+monitoraggio.getKm());
	  comunicazione_operatore.setText(monitoraggio.getComunicazione_operatore());
	  
	  andamento_lavori.setSelection(Arrays.asList(getResources().getStringArray(R.array.andam)).indexOf(monitoraggio.getAndamento()),true);
	  
	  
  }
  
  
  private void  getInformation(){
	  
	  monitoraggio.setNoteIntervento(note_intervento.getText().toString());
	  monitoraggio.setOra_inizio(oraInizio.getText().toString());
	  monitoraggio.setOra_fine(oraFine.getText().toString());
	  monitoraggio.setSegnalazioni_anomalie(segnalazioni.getText().toString());
	  monitoraggio.setAutomezzo(automezzo.getText().toString());
	  monitoraggio.setKm(Integer.parseInt(km.getText().toString()));
	  monitoraggio.setComunicazione_operatore(comunicazione_operatore.getText().toString());
	  monitoraggio.setAndamento(andamento_lavori.getSelectedItem().toString());
  }
   
   public void backList(){
	   finish();
		 
   }
   
   
   public void  goDispositivi(){
	     Intent page_dispositivi= new Intent(context, DispositiviListActivity.class);
	     page_dispositivi.putExtra(GlobalConstants.IDBUONO, current_id_buono);
	     page_dispositivi.putExtra(GlobalConstants.TYPESCHEDA, monitoraggio.getTipo_scheda());
	     startActivityForResult(page_dispositivi,0);
		 overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
 }
   
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            
            if (resultCode == RESULT_OK) {
            	//tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
            	//tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
            	
            	
            } else if (resultCode == RESULT_CANCELED) {
            	//tvStatus.setText("Press a button to start a scan.");
                //tvResult.setText("Scan cancelled.");
            }
        }
    }

   
	@Override
	public void backActivity() {
		// TODO Auto-generated method stub
		backList();
		
	}
    
	 @Override
	 public void onBackPressed() {
		finish();
	    
	 }
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buono, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		int id = item.getItemId();
		if (id == R.id.save) {
			seveInfotmation();
			return true;
		}
		else{
			dialogBox();
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	public void seveInfotmation(){
		
		 getInformation();
		if(tableMoni.updateBuono(monitoraggio)){
			showToast(context.getResources().getString(R.string.labelSuccessSave));
		}
		else{
			showToast(context.getResources().getString(R.string.labelErrorSave));
		}
		
	}
	
public void dialogBox() {
		
	    AlertDialog.Builder alertDialogBuilder = new        AlertDialog.Builder(this);
	    alertDialogBuilder.setMessage("Vuoi salvare le informazione prima di tornare alla lista?");
	    alertDialogBuilder.setPositiveButton("Salva",
	        new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface arg0, int arg1) {
	        	seveInfotmation();
	        	backList();
				
	        }
	    });
	    alertDialogBuilder.setNegativeButton("Torna alla lista",
	        new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface arg0, int arg1) {
	        	backList();
	        	
	        }
	    });

	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.show();
		
	}


public void dialogMagazzino(){
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Magazzino")
           .setItems(R.array.operazionimagazzino, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
               // The 'which' argument contains the index position
               // of the selected item
            	  
            	   
            	   Intent page_magazzino= new Intent(context, MagazzinoListActivity.class);
            	   page_magazzino.putExtra(GlobalConstants.IDBUONO, current_id_buono);
            	   if(which==0)
            	   page_magazzino.putExtra("operazione","carico_buono");
            	   else 
            	   page_magazzino.putExtra("operazione","scarico_buono");
            	   
            	   startActivity(page_magazzino);

           }
    });
    AlertDialog alertDialog = builder.create();
    alertDialog.show();
}
	
	public void showDialog(){
		
		DialogGlobalBack dialog = new DialogGlobalBack(this,getResources().getString(R.string.labelMessaggeControlSaveBuono));
        dialog.show(getFragmentManager(),"");
	}
	
	@Override
	public void onResume(){
	    super.onResume();
		dispositivi.setText(getResources().getText(R.string.labelDispositivi)+ " ("+tableMoniVoci.getNumDipositivi(current_id_buono)+")");


	}
	
}
