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

public class Tipo5 extends Fragment{
	public ActivityBase activityBase;
	
	private EditText farfalle;
	private EditText mosche;
	private EditText zanzare;
	private EditText moscerini;
	private EditText imenoptera;
	private EditText altro;
	
	
	
	private Spinner gradoInfest;
	private Spinner statoAdesivoSpinner;

	
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
		View rootView = inflater.inflate(R.layout.fragment_scheda5,
				container, false);
		
		farfalle  = (EditText) rootView.findViewById(R.id.farfalle);
		mosche  = (EditText) rootView.findViewById(R.id.mosche);
		moscerini = (EditText) rootView.findViewById(R.id.moscerini);
		zanzare  = (EditText) rootView.findViewById(R.id.zanzare);
		imenoptera  = (EditText) rootView.findViewById(R.id.imenoptera);
		
	
		altro  = (EditText) rootView.findViewById(R.id.altro);
		
		gradoInfest = (Spinner) rootView.findViewById(R.id.gradoInfes);
		statoAdesivoSpinner = (Spinner) rootView.findViewById(R.id.statoAdesivo);
		
		
		
		
	    Button saveDisp     = (Button) rootView.findViewById(R.id.saveDisp);
	    Button tornaIndietro     = (Button) rootView.findViewById(R.id.tornaIndietro);
	    
	    
	    ArrayAdapter<String> adapterGrado = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, attivitaall);
	   
	    gradoInfest.setAdapter(adapterGrado);
	    
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, statoAdesivo);
	   
	    statoAdesivoSpinner.setAdapter(adapter);
	   
	    
	    
	    
	    farfalle.setText(monitoraggio.getFarfalle());
	    mosche.setText(monitoraggio.getMosche());
	    moscerini.setText(monitoraggio.getMoscerini());
	    zanzare.setText(monitoraggio.getZanzare());
	    imenoptera.setText(monitoraggio.getImenoptera());
	    altro.setText(monitoraggio.getAltro());
	    
	    statoAdesivoSpinner.setSelection(Arrays.asList(statoAdesivo).indexOf(monitoraggio.getStato_adesivo()),true);
	    gradoInfest.setSelection(Arrays.asList(attivitaall).indexOf(monitoraggio.getGrado_infe()),true);
	  
	    
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
		
		
		monitoraggio.setFarfalle(farfalle.getText().toString());
		monitoraggio.setMosche(mosche.getText().toString());
		monitoraggio.setMoscerini(moscerini.getText().toString());
		monitoraggio.setZanzare(zanzare.getText().toString());
		monitoraggio.setImenoptera(imenoptera.getText().toString());
		
		
		monitoraggio.setAltro(altro.getText().toString());
		
		
		activityBase.updateMonitoraggioVoci(monitoraggio);
		
	}
	
}
