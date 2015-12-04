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

public class TipoAltro extends Fragment  {
	
	public ActivityBase activityBase;
	private Spinner spinnerAttivita;
	private Spinner spinnerStatoEsca;
	private Spinner spinnerProdottoSost;
	private Spinner spinnerCodDisp;
	private Spinner spinnerErogatore;
	private EditText sigla_dispositivo;
	private MonitoriaggioVoci monitoraggio;
    String[] attivitaall = new String[]{}; 
    String[] statoEscaall = new String[]{}; 
    String[] prodottoSostall = new String[]{}; 
    String[] articoli = new String[]{}; 
    String[] tipoDisp = new String[]{}; 
	
    
   

	@Override
	public void onAttach(Activity a) {
	    super.onAttach(a);
	    activityBase = (ActivityBase) a;
	    
	    attivitaall = activityBase.taAttivita(attivitaall);
	    statoEscaall = activityBase.taStatoEsca(statoEscaall);
	    prodottoSostall = activityBase.taTipoSostituzione(prodottoSostall);
	    articoli        = activityBase.taArticoli(articoli);
	    tipoDisp   =   activityBase.taTipoDispositivo(tipoDisp);
	    monitoraggio  = activityBase.getMonitoriaggioVoci();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_scheda_altro,
				container, false);
		spinnerAttivita 	= (Spinner) rootView.findViewById(R.id.attivita);
		spinnerStatoEsca 	= (Spinner) rootView.findViewById(R.id.statoEsca);
		spinnerProdottoSost = (Spinner) rootView.findViewById(R.id.tipo_sost);
		spinnerCodDisp 		= (Spinner) rootView.findViewById(R.id.coddisp);
		spinnerErogatore 	= (Spinner) rootView.findViewById(R.id.erogatore);
		sigla_dispositivo  	= (EditText) rootView.findViewById(R.id.sigla_dispositivo);
		
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
	    
	    
	  ArrayAdapter<String> adapterErogatore = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, tipoDisp);
	    spinnerErogatore.setAdapter(adapterErogatore);
	    
	    ArrayAdapter<String> adapterCodDisp = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_spinner_dropdown_item, articoli);
	    spinnerCodDisp.setAdapter(adapterCodDisp);
	     
	   
	    spinnerAttivita.setSelection(Arrays.asList(attivitaall).indexOf(monitoraggio.getAttivita()),true);
	    spinnerStatoEsca.setSelection( Arrays.asList(statoEscaall).indexOf(monitoraggio.getStatoEsca()),true);
	    spinnerProdottoSost.setSelection( Arrays.asList(prodottoSostall).indexOf(monitoraggio.getProdottoSost()),true);
	    spinnerErogatore.setSelection( Arrays.asList(tipoDisp).indexOf(monitoraggio.getErogatore()),true);
	    spinnerCodDisp.setSelection( Arrays.asList(articoli).indexOf(monitoraggio.getCod_dispositivo()),true);
	    sigla_dispositivo.setText(monitoraggio.getSigla_dispositivo());
	    
	    
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
		monitoraggio.setErogatore(spinnerErogatore.getSelectedItem().toString());
		monitoraggio.setCod_dispositivo(spinnerCodDisp.getSelectedItem().toString());
		monitoraggio.setSigla_dispositivo(sigla_dispositivo.getText().toString());
		activityBase.updateMonitoraggioVoci(monitoraggio);
		
	}
	
	
	
}


