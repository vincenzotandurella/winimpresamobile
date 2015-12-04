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

public class Tipo4 extends Fragment{
	public ActivityBase activityBase;
	
	private EditText lasioderma;
	private EditText tribolium;
	private EditText oryzephilus;
	private EditText rhyzopertha;
	private EditText stegobium;
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
		View rootView = inflater.inflate(R.layout.fragment_scheda4,
				container, false);
		
		lasioderma  = (EditText) rootView.findViewById(R.id.lasioderma);
		tribolium  = (EditText) rootView.findViewById(R.id.tribolium);
		oryzephilus  = (EditText) rootView.findViewById(R.id.oryzephilus);
		rhyzopertha  = (EditText) rootView.findViewById(R.id.rhyzopertha);
		stegobium  = (EditText) rootView.findViewById(R.id.stegobium);
	
		altro  = (EditText) rootView.findViewById(R.id.altro4);
		
		gradoInfest = (Spinner) rootView.findViewById(R.id.gradoInfes4);
		statoAdesivoSpinner = (Spinner) rootView.findViewById(R.id.statoAdesivo4);
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
	    
	    
	    stegobium.setText(monitoraggio.getStegobium());
	    lasioderma.setText(monitoraggio.getLasioderma());
	    tribolium.setText(monitoraggio.getTribolium());
	    oryzephilus.setText(monitoraggio.getOryzephilus());
	    rhyzopertha.setText(monitoraggio.getRhyzopertha());
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
		
		monitoraggio.setLasioderma(lasioderma.getText().toString());
		monitoraggio.setStegobium(stegobium.getText().toString());
		monitoraggio.setTribolium(tribolium.getText().toString());
		monitoraggio.setOryzephilus(oryzephilus.getText().toString());
		monitoraggio.setRhyzopertha(rhyzopertha.getText().toString());
		
		
		monitoraggio.setAltro(altro.getText().toString());
		
		
		activityBase.updateMonitoraggioVoci(monitoraggio);
		
	}
	
}
