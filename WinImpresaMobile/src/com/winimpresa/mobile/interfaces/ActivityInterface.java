package com.winimpresa.mobile.interfaces;

import com.winimpresa.mobile.to.MonitoriaggioVoci;

public interface ActivityInterface {

	
	 public void backActivity();
	 
	 public void showMessage(String msg);
	 public void realseWithDrop();
	 public String[] taAttivita(String[] attivita);
	 public String[] taStatoEsca(String[] statoEsca);
	 public String[] taTipoSostituzione(String[] prodottoSostituito);
	 public String[] taArticoli(String[] articoli);
	 public String[] taTipoDispositivo (String[] tipoDisp);
	 public MonitoriaggioVoci getMonitoriaggioVoci();
	 public boolean updateMonitoraggioVoci(MonitoriaggioVoci mon);

}

