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

public class Tipo3 extends Fragment{
	public ActivityBase activityBase;
	
	private EditText blatella_n;
	private EditText blatella_a;
	private EditText blatta_n;
	private EditText blatta_a;
	private EditText supella_n;
	private EditText supella_a;
	private EditText periplaneta_n;
	private EditText periplaneta_a;
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
		View rootView = inflater.inflate(R.layout.fragment_scheda3,
				container, false);
		
		blatella_n  = (EditText) rootView.findViewById(R.id.blatella_n);
		blatella_a  = (EditText) rootView.findViewById(R.id.blatella_a);
		blatta_n  = (EditText) rootView.findViewById(R.id.blatta_n);
		blatta_a  = (EditText) rootView.findViewById(R.id.blatta_a);
		supella_n  = (EditText) rootView.findViewById(R.id.supella_n);
		supella_a  = (EditText) rootView.findViewById(R.id.supella_a);
		periplaneta_n  = (EditText) rootView.findViewById(R.id.periplaneta_n);
		periplaneta_a  = (EditText) rootView.findViewById(R.id.periplaneta_a);
		altro  = (EditText) rootView.findViewById(R.id.altro);
		
		gradoInfest = (Spinner) rootView.findViewById(R.id.gradoInfest);
		statoAdesivoSpinner = (Spinner) rootView.findViewById(R.id.adesivo);
		
	    Button saveDisp     = (Button) rootView.findViewById(R.id.saveDisp);
	    Button tornaIndietro     = (Button) rootView.findViewById(R.id.tornaIndietro);
	    
	    
	    ArrayAdapter<String> adapterGrado = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, attivitaall);
	   
	    gradoInfest.setAdapter(adapterGrado);
	    
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, statoAdesivo);
	   
	    statoAdesivoSpinner.setAdapter(adapter);
	    
	    blatella_n.setText(monitoraggio.getBlattella_n());
	    blatella_a.setText(monitoraggio.getBlattella_a());
	    blatta_n.setText(monitoraggio.getBlatta_n());
	    blatta_a.setText(monitoraggio.getBlatta_a());
	    supella_n.setText(monitoraggio.getSupella_n());
	    supella_a.setText(monitoraggio.getSupella_a());
	    periplaneta_n.setText(monitoraggio.getPeriplaneta_n());
	    periplaneta_a.setText(monitoraggio.getPeriplaneta_a());
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
		monitoraggio.setBlattella_n(blatella_n.getText().toString());
		monitoraggio.setBlattella_a(blatella_a.getText().toString());
		monitoraggio.setBlatta_n(blatta_n.getText().toString());
		monitoraggio.setSupella_n(supella_n.getText().toString());
		monitoraggio.setSupella_a(supella_a.getText().toString());
		monitoraggio.setPeriplaneta_a(periplaneta_a.getText().toString());
		monitoraggio.setPeriplaneta_n(periplaneta_n.getText().toString());
		monitoraggio.setAltro(altro.getText().toString());
		
		
		activityBase.updateMonitoraggioVoci(monitoraggio);
		
	}
	
}
