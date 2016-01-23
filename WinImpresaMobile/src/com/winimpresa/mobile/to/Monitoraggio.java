package com.winimpresa.mobile.to;

public class Monitoraggio {
	
	private long id_monitoraggio;
	private int tipo_scheda;
	private long monitoraggio_n;
	private int evaso;
	private String cliente;
	private String cliente2;
	private String dataMonitoraggio;
	private String citta;
	private long commessa_n;
	private String sigla;
	private String descrizione;
	private String data;
	private String area;
	private String noteIntervento;
	private String ora_inizio;
	private String ora_fine;
	private String segnalazioni_anomalie;
	private String automezzo;
	private int km;
	private String comunicazione_operatore;
	private String andamento;
	private String vpa;
	private String tipo_monitoraggio;
	private String dataNoFormat;
	private boolean statoEvaso;
	
	

	
	
	
	
	
	
	
	public boolean isStatoEvaso() {
		return statoEvaso;
	}

	public void setStatoEvaso(boolean statoEvaso) {
		this.statoEvaso = statoEvaso;
	}

	public String getDataNoFormat() {
		return dataNoFormat;
	}

	public void setDataNoFormat(String dataNoFormat) {
		this.dataNoFormat = dataNoFormat;
	}

	public String getTipo_monitoraggio() {
		if(tipo_monitoraggio==null)
			return "";
	     else
		return tipo_monitoraggio;
	}

	public void setTipo_monitoraggio(String tipo_monitoraggio) {
		this.tipo_monitoraggio = tipo_monitoraggio;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getAndamento() {
		return andamento;
	}

	public void setAndamento(String andamento) {
		this.andamento = andamento;
	}

	public String getComunicazione_operatore() {
		return comunicazione_operatore;
	}

	public void setComunicazione_operatore(String comunicazione_operatore) {
		this.comunicazione_operatore = comunicazione_operatore;
	}

	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}

	public String getAutomezzo() {
		return automezzo;
	}

	public void setAutomezzo(String automezzo) {
		this.automezzo = automezzo;
	}

	public String getSegnalazioni_anomalie() {
		return segnalazioni_anomalie;
	}

	public void setSegnalazioni_anomalie(String segnalazioni_anomalie) {
		this.segnalazioni_anomalie = segnalazioni_anomalie;
	}

	public String getOra_inizio() {
		return ora_inizio;
	}

	public void setOra_inizio(String ora_inizio) {
		this.ora_inizio = ora_inizio;
	}

	public String getOra_fine() {
		return ora_fine;
	}

	public void setOra_fine(String ora_fine) {
		this.ora_fine = ora_fine;
	}

	public String getNoteIntervento() {
		return noteIntervento;
	}

	public void setNoteIntervento(String noteIntervento) {
		this.noteIntervento = noteIntervento;
	}

	public long getCommessa_n() {
		return commessa_n;
	}

	public void setCommessa_n(long commessa_n) {
		this.commessa_n = commessa_n;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Monitoraggio(){}
	
	public long getId_monitoraggio() {
		return id_monitoraggio;
	}
	public void setId_monitoraggio(long id_monitoraggio) {
		this.id_monitoraggio = id_monitoraggio;
	}
	public int getTipo_scheda() {
		return tipo_scheda;
	}
	public void setTipo_scheda(int tipo_scheda) {
		this.tipo_scheda = tipo_scheda;
	}
	public long getMonitoraggio_n() {
		return monitoraggio_n;
	}
	public void setMonitoraggio_n(int monitoraggio_n) {
		this.monitoraggio_n = monitoraggio_n;
	}
	public int getEvaso() {
		return evaso;
	}
	public void setEvaso(int evaso) {
		this.evaso = evaso;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCliente2() {
		return cliente2;
	}
	public void setCliente2(String cliente2) {
		this.cliente2 = cliente2;
	}
	public String getDataMonitoraggio() {
		return dataMonitoraggio;
	}
	public void setDataMonitoraggio(String dataMonitoraggio) {
		this.dataMonitoraggio = dataMonitoraggio;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	
	

}
