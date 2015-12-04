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
import android.widget.EditText;
import android.widget.Spinner;

import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.R;
import com.winimpresa.mobile.to.MonitoriaggioVoci;

public class Tipo2 extends Fragment{
	public ActivityBase activityBase;
	private Spinner spinnerStatoAdesivo;
	private Spinner numeroAttivita;
	
	private MonitoriaggioVoci monitoraggio;
   
	String[] attivitaall = new String[]{}; 
    String[] statoAdesivo = new String[]{}; 

	
    
   

	@Override
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    activityBase = (ActivityBase) a;
	    
	    attivitaall   =  activityBase.taAttivita(attivitaall);
	    statoAdesivo  = activityBase.taStatoEsca(statoAdesivo);
	    monitoraggio  = activityBase.getMonitoriaggioVoci();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_scheda2,
				container, false);
		
		spinnerStatoAdesivo = (Spinner) rootView.findViewById(R.id.spinnerCondizioneAdesivo);
		numeroAttivita = (Spinner) rootView.findViewById(R.id.numeroAttivita);
		
	    Button saveDisp     = (Button) rootView.findViewById(R.id.saveDisp);
	    Button tornaIndietro     = (Button) rootView.findViewById(R.id.tornaIndietro);
	    
	    
	    ArrayAdapter<String> adapterNum = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, attivitaall);
	   
	    numeroAttivita.setAdapter(adapterNum);
	    
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, statoAdesivo);
	   
	    spinnerStatoAdesivo.setAdapter(adapter);
	    
	   
	   spinnerStatoAdesivo.setSelection(Arrays.asList(statoAdesivo).indexOf(monitoraggio.getStato_adesivo()),true);
	    numeroAttivita.setSelection(Arrays.asList(attivitaall).indexOf(monitoraggio.getAttivita()),true);
	    
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
		
		monitoraggio.setStato_adesivo(spinnerStatoAdesivo.getSelectedItem().toString());
		monitoraggio.setAttivita(numeroAttivita.getSelectedItem().toString());
		
		activityBase.updateMonitoraggioVoci(monitoraggio);
		
	}
	
}
