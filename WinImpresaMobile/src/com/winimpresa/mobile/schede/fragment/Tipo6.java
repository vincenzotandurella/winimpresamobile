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

public class Tipo6 extends Fragment{
	public ActivityBase activityBase;
	
	private EditText plodia;
	private EditText ephestia;
	private EditText tinella;
	private EditText tineola;
	private EditText altro;
	
	
	
	private Spinner gradoInfest;
	private Spinner statoAdesivoSpinner;
	private Spinner feromoneSpinner;
	
	private MonitoriaggioVoci monitoraggio;
   
	String[] attivitaall = new String[]{}; 
    String[] statoAdesivo = new String[]{};
    String[] feromone = new String[]{};

	
    
   

	@Override
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    activityBase = (ActivityBase) a;
	    
	    attivitaall   =  activityBase.taAttivita(attivitaall);
	    statoAdesivo  = activityBase.taStatoEsca(statoAdesivo);
	    feromone      =  activityBase.taStatoEsca(feromone);
	    
	    monitoraggio  = activityBase.getMonitoriaggioVoci();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_scheda6,
				container, false);
		
		plodia  = (EditText) rootView.findViewById(R.id.plodia);
		ephestia  = (EditText) rootView.findViewById(R.id.ephestia);
		tinella  = (EditText) rootView.findViewById(R.id.tinella);
		tineola  = (EditText) rootView.findViewById(R.id.tineola);
	
		altro  = (EditText) rootView.findViewById(R.id.altro);
		
		gradoInfest = (Spinner) rootView.findViewById(R.id.gradoInfes);
		statoAdesivoSpinner = (Spinner) rootView.findViewById(R.id.statoAdesivo);
		feromoneSpinner = (Spinner) rootView.findViewById(R.id.feromone);
		
		
		
	    Button saveDisp     = (Button) rootView.findViewById(R.id.saveDisp);
	    Button tornaIndietro     = (Button) rootView.findViewById(R.id.tornaIndietro);
	    
	    
	    ArrayAdapter<String> adapterGrado = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, attivitaall);
	   
	    gradoInfest.setAdapter(adapterGrado);
	    
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, statoAdesivo);
	   
	    statoAdesivoSpinner.setAdapter(adapter);
	    
	    ArrayAdapter<String> adapterFeromone = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, feromone);
	   
	    feromoneSpinner.setAdapter(adapterFeromone);
	    
	    
	    plodia.setText(monitoraggio.getPlodia());
	    ephestia.setText(monitoraggio.getEphestia());
	    tinella.setText(monitoraggio.getTinella());
	    tineola.setText(monitoraggio.getTineola());
	   
	    altro.setText(monitoraggio.getAltro());
	    
	    statoAdesivoSpinner.setSelection(Arrays.asList(statoAdesivo).indexOf(monitoraggio.getStato_adesivo()),true);
	    gradoInfest.setSelection(Arrays.asList(attivitaall).indexOf(monitoraggio.getGrado_infe()),true);
	    feromoneSpinner.setSelection(Arrays.asList(feromone).indexOf(monitoraggio.getFeromone()),true);
	    
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
		
		monitoraggio.setStato_adesivo(statoAdesivoSpinner.getSelectedItem().toString());
		monitoraggio.setGrado_infe(gradoInfest.getSelectedItem().toString());
		monitoraggio.setFeromone(feromoneSpinner.getSelectedItem().toString());
		
		monitoraggio.setPlodia(plodia.getText().toString());
		monitoraggio.setEphestia(ephestia.getText().toString());
		monitoraggio.setTinella(tinella.getText().toString());
		monitoraggio.setTineola(tineola.getText().toString());
		
		
		
		monitoraggio.setAltro(altro.getText().toString());
		
		
		activityBase.updateMonitoraggioVoci(monitoraggio);
		
	}
	
}
