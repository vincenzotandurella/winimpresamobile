package com.winimpresa.mobile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.winimpresa.mobile.database.MonitoraggioTable;
import com.winimpresa.mobile.database.MonitoraggioVociTable;
import com.winimpresa.mobile.to.Articolo;
import com.winimpresa.mobile.to.Magazzino;
import com.winimpresa.mobile.to.Monitoraggio;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.R.bool;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MagazzinoListActivity extends ActivityBase {


	private ArrayList<HashMap<String, Object>> dataMagazzino;
	private ArrayList<Magazzino> allMagazzino = new ArrayList<Magazzino>();
	private MonitoraggioTable   monitoraggioMagazzino ;
	private SimpleAdapter adapter;
	private ListView listMagazzino;
	private Bundle bundle;
	private String operazione;
	private long current_id_buono;
	private int position_current ;
	private View blackSfondo;
	private LinearLayout dettaglioMagazzino;
	private boolean viewdattagli = false;
	private boolean delete = false;
	private ArrayList<Articolo> listArticoli  = new ArrayList<Articolo>();
	private Monitoraggio currentMonitoraggio;
	private Button chiudidettaglio;
	private Button salvamagazzino;
	private Spinner spinnerCodice;
	private TextView unita;
	private TextView descrizioneArticolo;
	private EditText principio;
	private EditText percentualeConc;
	private EditText soluzioni;
	private EditText quantita;
	private EditText prezzo;
	private int tipoOp=0; //0 new 1 edit
	private AlertDialog.Builder alertDialogBuilder;
	
	
	
	
	
    String[] codici = new String[]{}; 
    String[] codiciDesc = new String[]{}; 
	String[] from = { "codiceMagazzino", "descrizione" };
	int[] to = { R.id.codiceMagazzino, R.id.descrizione };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_magazzino_list);
		
		ActionBar ab = getActionBar();
	    ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		ab.setIcon( new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ab.setTitle("Carcio magazzino");
		
		
		bundle = getIntent().getExtras();
		if (bundle != null) {

			operazione = bundle.getString("operazione");
			current_id_buono = bundle.getLong(GlobalConstants.IDBUONO);
			 alertDialogBuilder 		= new AlertDialog.Builder(this);
			monitoraggioMagazzino = new MonitoraggioTable(context, db);
			MonitoraggioVociTable voci = new MonitoraggioVociTable(context, db); 
			currentMonitoraggio = monitoraggioMagazzino.getInfoMonitoraggio(current_id_buono);
			listMagazzino    =  (ListView) findViewById(R.id.listViewMagazzino);
			allMagazzino     = monitoraggioMagazzino.getListMagazzino(allMagazzino,current_id_buono,operazione);
			dataMagazzino 		= new ArrayList<HashMap<String, Object>>();
			blackSfondo 			= (View) findViewById(R.id.black);
			dettaglioMagazzino 	= (LinearLayout) findViewById(R.id.dettaglioMagazzino);
			chiudidettaglio     = (Button) findViewById(R.id.chudischeda);
			salvamagazzino       = (Button) findViewById(R.id.salvamagazzino);
			spinnerCodice       = (Spinner) findViewById(R.id.spinnerCodice);
			descrizioneArticolo = (TextView) findViewById(R.id.descrizione);
			unita 				= (TextView) findViewById(R.id.unita);
			principio 				= (EditText) findViewById(R.id.principio);
			percentualeConc 		= (EditText) findViewById(R.id.percentualeConc);
			soluzioni 				= (EditText) findViewById(R.id.soluzioni);
			principio 				= (EditText) findViewById(R.id.principio);
			quantita 				= (EditText) findViewById(R.id.quantita);
			prezzo 					= (EditText) findViewById(R.id.prezzo);
			
			codiciDesc = voci.getArticoli(codiciDesc);
			codici     = voci.getCodice(codici);
			listArticoli = voci.getArticoliList(listArticoli);
			
			ArrayAdapter<String> adaptercodici = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_dropdown_item, codici);
		   
			spinnerCodice.setAdapter(adaptercodici);
			

			spinnerCodice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { 
			    
			    		descrizioneArticolo.setText( listArticoli.get(i).getDescrizione());
			    		unita.setText(listArticoli.get(i).getUnita());
			    	
			    }
			    public void onNothingSelected(AdapterView<?> adapterView) {
			        return;
			    } 
			}); 
			
			
			if (operazione.equalsIgnoreCase("scarico_buono"))
			  ab.setTitle("Scarico magazzino");
			
		}
		
		
		for (int i = 0; i < allMagazzino.size(); i++) {
			Magazzino mag = allMagazzino.get(i);

			HashMap<String, Object> magazzinoMap = new HashMap<String, Object>();

			magazzinoMap.put("codiceMagazzino", mag.getArticolo().getCodice());
			magazzinoMap.put("descrizione", mag.getArticolo().getDescrizione());
			

			dataMagazzino.add(magazzinoMap);
		}
		adapter = new SimpleAdapter(getApplicationContext(), dataMagazzino,
				R.layout.magazzino_general, from, to);
		
		listMagazzino.setAdapter(adapter);
		
		listMagazzino.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					if (delete == true) {
						delete = false;
						return;
					}

					
					position_current = position;
					tipoOp=1;
					setCampiEdit();
					settaViewDattagli(true);
					

				}
			});
		
		   
		listMagazzino.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0, View v,
							int index, long arg3) {

						delete = true;
						dialogBoxeDelete(index);
						return false;
					}
				});
		
		chiudidettaglio.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				settaViewDattagli(false);

			}
		});
		
		salvamagazzino.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				saveCarcioScarico();

			}
		});
		
	}
	
	
	public void backBuono() {

		if (viewdattagli == true) {
			settaViewDattagli(false);
			return;
		}

		finish();
	}

	public void settaViewDattagli(boolean view) {

		if (view == true) {
			blackSfondo.setVisibility(View.VISIBLE);
			blackSfondo.animate().alpha(0.5f);
			dettaglioMagazzino.setVisibility(View.VISIBLE);
			dettaglioMagazzino.animate().alpha(1.0f);
			listMagazzino.setEnabled(false);
			listMagazzino.animate().alpha(0.1f);

		} else if (view == false) {
			dettaglioMagazzino.animate().alpha(0.0f);
			dettaglioMagazzino.setVisibility(View.INVISIBLE);
			blackSfondo.setVisibility(View.INVISIBLE);
			blackSfondo.animate().alpha(0.0f);
			listMagazzino.setEnabled(true);
			listMagazzino.animate().alpha(1.0f);
			resetCampi();
			tipoOp=0;
			hideKeyboard(this);
		}
		viewdattagli = view;
	}
	
	public void saveCarcioScarico(){
		Magazzino maga = new Magazzino();
		maga.setArticolo(listArticoli.get( spinnerCodice.getSelectedItemPosition()));
		maga.setMonitoriaggio(currentMonitoraggio);
		maga.setPrincipio(principio.getText().toString());
		maga.setConcetrato(percentualeConc.getText().toString());
		maga.setSoluzione(soluzioni.getText().toString());
		maga.setQuantita(quantita.getText().toString());
		maga.setPrezzo(prezzo.getText().toString());
		
		if(tipoOp==0){
		
		if(monitoraggioMagazzino.aggiungiCaricoScarico(maga,operazione)){
			showToast("Il dato è stato inserito, con successo");
			

			HashMap<String, Object> magazzinoMap2 = new HashMap<String, Object>();

			magazzinoMap2.put("codiceMagazzino", maga.getArticolo().getCodice());
			magazzinoMap2.put("descrizione", maga.getArticolo().getDescrizione());
			

			dataMagazzino.add(magazzinoMap2);

		
			allMagazzino.add(maga);

			adapter.notifyDataSetChanged();
			
			listMagazzino.smoothScrollToPosition(dataMagazzino.size());
			
			settaViewDattagli(false);
		}
		else{
			showToast("Errore durante l'inseritmeto, riprova!");
		}
		}
		else{
			Magazzino m = maga;
			
			if(monitoraggioMagazzino.updateCaricoScarico(m, operazione)){
				
				showToast("Il dato è stato aggiornato, con successo");
				allMagazzino.set(position_current, m);
				dataMagazzino.get(position_current).put("codiceMagazzino", m.getArticolo().getCodice());
				dataMagazzino.get(position_current).put("descrizione", m.getArticolo().getDescrizione());
				adapter.notifyDataSetChanged();
				settaViewDattagli(false);
			}else{
				showToast("Errore durante l'aggiornamento, riprova!");
			}
			
			
		}
		
		
		
	}
	
	private void setCampiEdit(){
		Magazzino m = allMagazzino.get(position_current);
		
		spinnerCodice.setSelection(Arrays.asList(codici).indexOf(m.getArticolo().getCodice()));
		descrizioneArticolo.setText(m.getArticolo().getDescrizione());
		principio.setText(m.getPrincipio());
		 unita.setText(m.getArticolo().getUnita());
		 percentualeConc.setText(m.getConcetrato());
		   soluzioni.setText(m.getSoluzione());
		   quantita.setText(m.getQuantita());
		   prezzo.setText(m.getPrezzo());
		
		
	}
	
	private void resetCampi(){
	   spinnerCodice.setSelection(0);
	   descrizioneArticolo.setText("");
	   principio.setText("");
	   unita.setText("");
	   percentualeConc.setText("");
	   soluzioni.setText("");
	   quantita.setText("");
	   prezzo.setText("");
		
	}
	
public void dialogBoxeDelete(final int pos) {

		
		alertDialogBuilder.setMessage("Vuoi cancellare l'operazione ?");
		alertDialogBuilder.setPositiveButton("Cancella",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					boolean flag= 	monitoraggioMagazzino.deleteCaricoScarico(allMagazzino.get(pos), operazione);
						

					
						if (flag) {
							showToast("L'operazione è stata cancellata !");
							allMagazzino.remove(pos);
							dataMagazzino.remove(pos);
							adapter.notifyDataSetChanged();

						}

					}
				});
		alertDialogBuilder.setNegativeButton("Annulla",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.magazzino_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
	
		if (id == R.id.add) {
			settaViewDattagli(true);
			return true;
		}
		else {
			// showToast("current id "+current_id_buono);
			backBuono();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed() {
		backBuono();
	}

}
