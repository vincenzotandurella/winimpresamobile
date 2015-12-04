package com.winimpresa.mobile;

import com.winimpresa.mobile.database.MonitoraggioVociTable;
import com.winimpresa.mobile.schede.fragment.Tipo1;
import com.winimpresa.mobile.schede.fragment.Tipo2;
import com.winimpresa.mobile.schede.fragment.Tipo3;
import com.winimpresa.mobile.schede.fragment.Tipo4;
import com.winimpresa.mobile.schede.fragment.Tipo5;
import com.winimpresa.mobile.schede.fragment.Tipo6;
import com.winimpresa.mobile.schede.fragment.Tipo7;
import com.winimpresa.mobile.schede.fragment.TipoAltro;
import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class SchedaActivity extends ActivityBase {
	private static final String TAG_LOG = MainActivity.class.getName();
	private Bundle bundle;
	private long current_id_buono;
	private int current_id_voce;
	private int type_scheda;
	private MonitoraggioVociTable tableVoci;
	public MonitoriaggioVoci monintoraggio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_scheda);
		ActionBar ab = getActionBar();
	   
		ab.setDisplayUseLogoEnabled(false);
		
		ab.setIcon( new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ab.setSubtitle(user.getFullName() + " / "+user.getIdUser());
		bundle  = getIntent().getExtras();
		
		if (bundle != null) {
		       
	    	
       	
       	   current_id_buono = bundle.getLong(GlobalConstants.IDBUONO);
       	   type_scheda 		= bundle.getInt(GlobalConstants.TYPESCHEDA);
       	   current_id_voce 	= bundle.getInt(GlobalConstants.IDVOCE);
		}
		
		
		
		
		if (savedInstanceState == null) {
			switch (type_scheda) {
			case 1:
				ab.setTitle("Buono roditore erogatore");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new Tipo1()).commit();
				break;
				
			case 2:
				ab.setTitle("Buono roditore trappola");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new Tipo2()).commit();
				break;
				
			case 3:
				ab.setTitle("Buono insetti striscianti");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new Tipo3()).commit();
				break;
			case 4:
				ab.setTitle("Buono insetti striscianti delle derrate alimentari");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new Tipo4()).commit();
				break;
				
			case 5:
				ab.setTitle("Buono insetti volanti");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new Tipo5()).commit();
				break;

			case 6:
				ab.setTitle("Buono insetti volanti delle derrate alimentari");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new Tipo6()).commit();
				break;
			case 19:
				ab.setTitle("Buono insetti volanti delle derrate alimentari");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new Tipo7()).commit();
				break;
				
				
			default:
				
				ab.setTitle("Altri buoni");
				getFragmentManager().beginTransaction()
				.add(R.id.container, new TipoAltro()).commit();
				break;
				
			}
			
			
		}
		
		
		tableVoci 	  = new MonitoraggioVociTable(context, db);
		monintoraggio = new MonitoriaggioVoci();
		monintoraggio = tableVoci.getDispoisitivo(monintoraggio, current_id_voce);
		
	}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scheda, menu);
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
	

	@Override
	public String[] taAttivita(String[] attivita) {
		// TODO Auto-generated method stub
	
			return tableVoci.getTaAttivita(attivita);
	}
	
	@Override
	public String[] taStatoEsca(String[] statoEsca) {
		// TODO Auto-generated method stub
	
			return tableVoci.getTaStatoEsca(statoEsca);
	}
	
	@Override
	public String[] taTipoSostituzione(String[] prodottoSostituito) {
		// TODO Auto-generated method stub
		return tableVoci.getTaTipoSostituzione(prodottoSostituito);
	}
	
	
	@Override
	public String[] taArticoli(String[] articoli) {
		// TODO Auto-generated method stub
		return tableVoci.getArticoli(articoli);
	}
	
	
	@Override
	public String[] taTipoDispositivo(String[] tipoDisp) {
		// TODO Auto-generated method stub
		return tableVoci.getTipoDispositivo(tipoDisp);
	}
	
	@Override
	public MonitoriaggioVoci getMonitoriaggioVoci() {
		// TODO Auto-generated method stub
		return this.monintoraggio;
	}
	
	@Override
	public boolean updateMonitoraggioVoci(MonitoriaggioVoci mon) {
		// TODO Auto-generated method stub
		
	    if(tableVoci.updateMonitoraggio(mon, type_scheda)){
	    	monintoraggio=mon;
	    	showToast("Il dispositivo è stato salvato correttamente");
	    	finish();
	    }
	    else{
	    	
	    	showToast("Errore durante il salvataggio, riprova!");
	    }
		return false;
	}


	
}
