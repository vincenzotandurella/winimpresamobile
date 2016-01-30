package com.winimpresa.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.winimpresa.mobile.database.MonitoraggioVociTable;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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

public class DispositiviListActivity extends ActivityBase {
	private static final String TAG_LOG = MainActivity.class.getName();
	private long current_id_buono = 0;
	private int position_current;
	private int type_scheda;
	private Bundle bundle;
	private ArrayList<HashMap<String, Object>> dataDispositivi;
	private ArrayList<MonitoriaggioVoci> allMonitoriaggioVoci = new ArrayList<MonitoriaggioVoci>();
	private SimpleAdapter adapter;
	private ListView lisDispositivi;
	private MonitoraggioVociTable tableVoci;
	private LinearLayout dettaglioDispositivo;
	private View blackSfondo;
	private boolean viewdattagli = false;
	private boolean delete = false;
	private Button qrcode;
	private Button goScheda;
	private Button salva;
	private Spinner settore;
	private Spinner ambiente;
	private Spinner tiposDisp;
	private Spinner codDisp;
	private EditText siglaDisp;
	private InitTask initTask;
	private String [] ambienteContainer;
	private String [] settoriContainer;
	private String [] tipoDispContainer;
	private String [] dispositiviContainer;
	
	
	String[] from = { "ambiente", "settore", "tipoDisp", "codDisp", "siglaDisp" };
	int[] to = { R.id.ambiente, R.id.settore, R.id.tipoDisp, R.id.codDisp,
			R.id.siglaDisp };
	
	private AlertDialog.Builder alertDialogBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_dispositivi_list);
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		ab.setIcon(new ColorDrawable(getResources().getColor(
				android.R.color.transparent)));
		ab.setSubtitle(user.getFullName() + " / " + user.getIdUser());
		bundle = getIntent().getExtras();
		if (bundle != null) {

			Log.d(TAG_LOG,
					"TYPE_SCHEDA " + bundle.getInt(GlobalConstants.TYPESCHEDA));
			current_id_buono = bundle.getLong(GlobalConstants.IDBUONO);
			type_scheda = bundle.getInt(GlobalConstants.TYPESCHEDA);
		}

        alertDialogBuilder 		= new AlertDialog.Builder(this);
		tableVoci 			 	= new MonitoraggioVociTable(context, db);
		ambienteContainer       = tableVoci.getTaAmbiente(ambienteContainer);
		settoriContainer        = tableVoci.getSettori(settoriContainer);
		tipoDispContainer        = tableVoci.getTipoDispositivo(tipoDispContainer);
		dispositiviContainer        = tableVoci.getDispositivi(dispositiviContainer);
		allMonitoriaggioVoci 	= tableVoci.getAllDispoisitivi(allMonitoriaggioVoci, current_id_buono);
		lisDispositivi 			= (ListView) findViewById(R.id.listViewDispositivi);
		blackSfondo 			= (View) findViewById(R.id.black);
		dettaglioDispositivo 	= (LinearLayout) findViewById(R.id.dettaglioDispositivo);
		ambiente 				= (Spinner) findViewById(R.id.ambiente);
		settore 				= (Spinner) findViewById(R.id.settore);
		tiposDisp 				= (Spinner) findViewById(R.id.tipoDisp);
		codDisp 				= (Spinner) findViewById(R.id.codDisp);
		siglaDisp 				= (EditText) findViewById(R.id.siglaDisp);
		qrcode 					= (Button) findViewById(R.id.qrcode);
		goScheda 			    = (Button) findViewById(R.id.goscheda);
		salva 					= (Button) findViewById(R.id.salva);
		dataDispositivi 		= new ArrayList<HashMap<String, Object>>();

		
		 ArrayAdapter<String> adapterAmbiente = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_dropdown_item, ambienteContainer);
		 ambiente.setAdapter(adapterAmbiente);
		 
		 ArrayAdapter<String> adapterSettori = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_dropdown_item, settoriContainer);
		 settore.setAdapter(adapterSettori);
		 
		 
		 ArrayAdapter<String> adapterTipoDisp = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_dropdown_item, tipoDispContainer);
		 tiposDisp.setAdapter(adapterTipoDisp);
		 
		 ArrayAdapter<String> adapterDispositivi = new ArrayAdapter<String>(this,
		            android.R.layout.simple_spinner_dropdown_item, dispositiviContainer);
		 codDisp.setAdapter(adapterDispositivi);
		
		
		
		for (int i = 0; i < allMonitoriaggioVoci.size(); i++) {
			MonitoriaggioVoci mtg = allMonitoriaggioVoci.get(i);

			HashMap<String, Object> dispositivoMap = new HashMap<String, Object>();

			dispositivoMap.put("ambiente", mtg.getAmbiente());
			dispositivoMap.put("settore", mtg.getSettore());
			dispositivoMap.put("tipoDisp", mtg.getErogatore());
			dispositivoMap.put("codDisp", mtg.getCod_dispositivo());
			dispositivoMap.put("siglaDisp", mtg.getSigla_dispositivo());

			dataDispositivi.add(dispositivoMap);
		}

		
		
		adapter = new SimpleAdapter(getApplicationContext(), dataDispositivi,
				R.layout.dispositivo_general, from, to);

		lisDispositivi.setAdapter(adapter);
        lisDispositivi.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (delete == true) {
					delete = false;
					return;
				}

				Log.d(TAG_LOG,
						"Element selected "
								+ allMonitoriaggioVoci.get(position)
										.getId_voce());
				position_current = position;

				settaViewDattagli(true);
				settaParamDettagli();

			}
		});

		
        
        lisDispositivi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0, View v,
							int index, long arg3) {

						delete = true;
						dialogBoxeDelete(index);
						return false;
					}
				});

		
		
		qrcode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getWindow()
						.setSoftInputMode(
								WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				scattaQR_CODE();
			}
		});
		
		goScheda.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goScheda();
			}
		});
		
		
	
		
		

		salva.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				saveInformationLocal();
				if (tableVoci.updateMonitoraggioVoci(allMonitoriaggioVoci
						.get(position_current))) {
					settaViewDattagli(false);
					closekeyboard();
				}

			}
		});
		
		
		
		if( GlobalConstants.controlloTipoScheda(type_scheda)==0){
			
			qrcode.setVisibility(View.GONE);
			goScheda.setVisibility(View.VISIBLE);
		}

	}

	
	
	
	private void goDispositivo(int pos) {

		lisDispositivi.performItemClick(lisDispositivi.getChildAt(pos), pos,
				lisDispositivi.getItemIdAtPosition(pos));

	}

	private int searchDipositivo(int idVoce) {
		int pos = 0;
		for (int i = 0; i < allMonitoriaggioVoci.size(); i++) {

			if (allMonitoriaggioVoci.get(i).getId_voce() == idVoce) {
				pos = i;
				break;
			}

		}
		return pos;

	}

	
	public void settaViewDattagli(boolean view) {

		if (view == true) {
			blackSfondo.setVisibility(View.VISIBLE);
			blackSfondo.animate().alpha(0.5f);
			dettaglioDispositivo.setVisibility(View.VISIBLE);
			dettaglioDispositivo.animate().alpha(1.0f);
			lisDispositivi.setEnabled(false);
			lisDispositivi.animate().alpha(0.1f);

		} else if (view == false) {
			dettaglioDispositivo.animate().alpha(0.0f);
			dettaglioDispositivo.setVisibility(View.INVISIBLE);
			blackSfondo.setVisibility(View.INVISIBLE);
			blackSfondo.animate().alpha(0.0f);
			lisDispositivi.setEnabled(true);
			lisDispositivi.animate().alpha(1.0f);
			hideKeyboard(this);
		}
		viewdattagli = view;
	}

	
	public void settaParamDettagli() {

		
		ambiente.setSelection( Arrays.asList(ambienteContainer).indexOf(allMonitoriaggioVoci.get(position_current)
				.getAmbiente()),true);
		settore.setSelection( Arrays.asList(settoriContainer).indexOf(allMonitoriaggioVoci.get(position_current)
				.getSettore()),true);
		tiposDisp.setSelection( Arrays.asList(tipoDispContainer).indexOf(allMonitoriaggioVoci.get(position_current)
				.getErogatore()),true);
		codDisp.setSelection( Arrays.asList(dispositiviContainer).indexOf(allMonitoriaggioVoci.get(position_current)
				.getCod_dispositivo()),true);
		siglaDisp.setText(allMonitoriaggioVoci.get(position_current)
				.getSigla_dispositivo());

	}

	
	public void saveInformationLocal() {

		dataDispositivi.get(position_current).put("ambiente",
				ambiente.getSelectedItem().toString());
		allMonitoriaggioVoci.get(position_current).setAmbiente(
				ambiente.getSelectedItem().toString());
		dataDispositivi.get(position_current).put("settore",
				settore.getSelectedItem().toString());
		allMonitoriaggioVoci.get(position_current).setSettore(
				settore.getSelectedItem().toString());
		dataDispositivi.get(position_current).put("tipoDisp",
				tiposDisp.getSelectedItem().toString());
		allMonitoriaggioVoci.get(position_current).setErogatore(
				tiposDisp.getSelectedItem().toString());
		dataDispositivi.get(position_current).put("codDisp",
				codDisp.getSelectedItem().toString());
		allMonitoriaggioVoci.get(position_current).setCod_dispositivo(
				codDisp.getSelectedItem().toString());
		dataDispositivi.get(position_current).put("siglaDisp",
				siglaDisp.getText().toString());
		allMonitoriaggioVoci.get(position_current).setSigla_dispositivo(
				siglaDisp.getText().toString());

		adapter.notifyDataSetChanged();
		showToast("Dispositivo modificato !");
	}

	
	
	public void scattaQR_CODE() {
		initTask = new InitTask();
		initTask.execute();

	}

	
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			initTask.cancel(true);
			if (resultCode == RESULT_OK) {
			
				controllerQrcode(intent.getStringExtra("SCAN_RESULT"));
				
			} else if (resultCode == RESULT_CANCELED) {
				initTask.cancel(true);

			}
		}
	}

	
	
	
	

	private void controllerQrcode(String qrcode) {

		MonitoriaggioVoci mon = allMonitoriaggioVoci.get(position_current);
		int tempIdVoce = tableVoci.controllerQrcode(mon.getId_voce(), qrcode);
		if (tempIdVoce != 0) {

			int pos = searchDipositivo(tempIdVoce);
			dialogBoxGoDispositivo(pos);
			return;
		}

		// CASO CHE NON ESISTE IL QRCODE NELLA TABELLA

		if (mon.getQrcode() == null) {
			mon.setQrcode(qrcode);

			Boolean flag = tableVoci.updateDispositivoQrcode(mon);
			if (flag) {
				showToast("QrCode inserito correttamente ");
				goScheda();

			}
		} else if (!mon.getQrcode().equals(qrcode)) {
			dialogBox(qrcode);
		} else if (mon.getQrcode().equals(qrcode)) {
			// showMessage("Qrcode uguali ok");
			goScheda();
		}

	}

	public void goScheda() {
		Intent page_scheda = new Intent(context, SchedaActivity.class);
		page_scheda.putExtra(GlobalConstants.TYPESCHEDA, type_scheda);
		page_scheda.putExtra(GlobalConstants.IDBUONO, current_id_buono);
		page_scheda.putExtra(GlobalConstants.IDVOCE,
				allMonitoriaggioVoci.get(position_current).getId_voce());


		startActivity(page_scheda);
	}

	public void backBuono() {

		if (viewdattagli == true) {
			settaViewDattagli(false);
			return;
		}

		finish();
	}

	
	
	public void dialogBox(final String qrcode) {

		
		alertDialogBuilder
				.setMessage("Il qrcode non coincide con il dispositivo, vuoi sostituire?");
		alertDialogBuilder.setPositiveButton("Sostituisci",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						MonitoriaggioVoci mon = allMonitoriaggioVoci
								.get(position_current);
						mon.setQrcode(qrcode);

						Boolean flag = tableVoci.updateDispositivoQrcode(mon);
						if (flag) {
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
	
	

	public void dialogBoxGoDispositivo(final int pos) {

		
		alertDialogBuilder
				.setMessage("Il qrcode è presente su un altro dispositivo !");
		alertDialogBuilder.setPositiveButton("Vai al dispositivo",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						settaViewDattagli(false);
						goDispositivo(pos);

					}
				});
		alertDialogBuilder.setNegativeButton("Annulla operazione",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	public void dialogBoxQrcodeNoReader() {

	
		alertDialogBuilder.setMessage("Il qrcode non è leggibile !");
		alertDialogBuilder.setPositiveButton("Vai avanti senza qrcode",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						MonitoriaggioVoci mon = allMonitoriaggioVoci
								.get(position_current);
						mon.setQrcode(GlobalConstants.QRCODECORROTTO);

						Boolean flag = tableVoci.updateDispositivoQrcode(mon);
						if (flag) {
							showToast("QrCode inserito correttamente ");
							goScheda();

						}

					}
				});
		alertDialogBuilder.setNegativeButton("Chiudi",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	public void dialogBoxeDelete(final int pos) {

		
		alertDialogBuilder.setMessage("Vuoi cancellare il dispositivo?");
		alertDialogBuilder.setPositiveButton("Cancella",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						MonitoriaggioVoci mon = allMonitoriaggioVoci.get(pos);
						mon.setQrcode(GlobalConstants.DISPOSTIVODELETE);

						Boolean flag = tableVoci.updateDispositivoQrcode(mon);
						if (flag) {
							showToast("Il dispositivo è stato cancellato");
							dataDispositivi.remove(pos);
							allMonitoriaggioVoci.remove(pos);
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
	
	public void addMonitoraggio() {

		MonitoriaggioVoci nuovo = new MonitoriaggioVoci();
		nuovo = tableVoci.insertMonitoraggioVoci(nuovo, current_id_buono);
		if (nuovo != null) {
			showToast("Dispositivo inserito con successo!");

			HashMap<String, Object> dispositivoMap2 = new HashMap<String, Object>();

			dispositivoMap2.put("ambiente", nuovo.getAmbiente());
			dispositivoMap2.put("settore", nuovo.getSettore());
			dispositivoMap2.put("tipoDisp", nuovo.getErogatore());
			dispositivoMap2.put("codDisp", nuovo.getCod_dispositivo());
			dispositivoMap2.put("siglaDisp", nuovo.getSigla_dispositivo());

			dataDispositivi.add(dispositivoMap2);
			allMonitoriaggioVoci.add(nuovo);

			adapter.notifyDataSetChanged();
			// listBuoni.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
			lisDispositivi.smoothScrollToPosition(dataDispositivi.size());

		} else {
			showToast("Errore inseirimento");
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dispositivi_list, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add) {

			addMonitoraggio();
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

	
	
	
	
	private class InitTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			String packageString = "com.winimpresa.mobile";
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.setPackage(packageString);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		}

		protected Void doInBackground(Void... unused) {

			try {
				Thread.sleep(GlobalConstants.timeoutQrCode * 1000);
			} catch (Exception e) {
				System.out.println(e);
			}
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			finishActivity(0);
			dialogBoxQrcodeNoReader();

		}

	}
}
