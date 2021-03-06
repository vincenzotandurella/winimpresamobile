package com.winimpresa.mobile.schede.fragment;

import java.util.Arrays;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.R;
import com.winimpresa.mobile.to.MonitoriaggioVoci;

public class Tipo1 extends Fragment  {
	
	public ActivityBase activityBase;
	private Spinner spinnerAttivita;
	private Spinner spinnerStatoEsca;
	private Spinner spinnerProdottoSost;
	private MonitoriaggioVoci monitoraggio;
    String[] attivitaall = new String[]{}; 
    String[] statoEscaall = new String[]{}; 
    String[] prodottoSostall = new String[]{}; 
	
    
   

	@Override
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    activityBase = (ActivityBase) a;
	    
	    attivitaall = activityBase.taAttivita(attivitaall);
	    statoEscaall = activityBase.taStatoEsca(statoEscaall);
	    prodottoSostall = activityBase.taTipoSostituzione(prodottoSostall);
	    monitoraggio  = activityBase.getMonitoriaggioVoci();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_scheda1,
				container, false);
		spinnerAttivita = (Spinner) rootView.findViewById(R.id.spinnerAttivita);
		spinnerStatoEsca = (Spinner) rootView.findViewById(R.id.spinnerStatoEsca);
		spinnerProdottoSost = (Spinner) rootView.findViewById(R.id.spinnerProdottoSostituito);
	    Button saveDisp     = (Button) rootView.findViewById(R.id.saveDisp);
	    Button tornaIndietro     = (Button) rootView.findViewById(R.id.tornaIndietro);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, attivitaall);
	   
	    spinnerAttivita.setAdapter(adapter);
	    
	    ArrayAdapter<String> adapterStatoesca = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, statoEscaall);
	    spinnerStatoEsca.setAdapter(adapterStatoesca);
	    
	    ArrayAdapter<String> adapterProdotti = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, prodottoSostall);
	    spinnerProdottoSost.setAdapter(adapterProdotti);
	     
	   
	    spinnerAttivita.setSelection(Arrays.asList(attivitaall).indexOf(monitoraggio.getAttivita()),true);
	    spinnerStatoEsca.setSelection( Arrays.asList(statoEscaall).indexOf(monitoraggio.getStatoEsca()),true);
	    spinnerProdottoSost.setSelection( Arrays.asList(prodottoSostall).indexOf(monitoraggio.getProdottoSost()),true);
	   
	    saveDisp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				upadateMonitoraggio();
			
			}
		});
	    
	    tornaIndietro.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
			
			}
		});
	    
		return rootView;
	}
	
	
	
	
	
	
	
	public void upadateMonitoraggio(){
		
		monitoraggio.setAttivita(spinnerAttivita.getSelectedItem().toString());
		monitoraggio.setStatoEsca(spinnerStatoEsca.getSelectedItem().toString());
		monitoraggio.setProdottoSost(spinnerProdottoSost.getSelectedItem().toString());
		
		activityBase.updateMonitoraggioVoci(monitoraggio);
		
	}
	
	
	
}


