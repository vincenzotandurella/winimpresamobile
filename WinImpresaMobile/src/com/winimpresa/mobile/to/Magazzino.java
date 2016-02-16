package com.winimpresa.mobile.to;



public class Magazzino {

	private int id;
	private String codice ;
	private String descrizione;
	private String principio;
	private String concetrato;
	private String soluzione;
	private String quantita;
	private String prezzo;
	
	private Articolo articolo;
	private Monitoraggio monitoriaggio;
	
	
	
	
	public String getPrincipio() {
		return principio;
	}
	public void setPrincipio(String principio) {
		this.principio = principio;
	}
	public String getConcetrato() {
		return concetrato;
	}
	public void setConcetrato(String concetrato) {
		this.concetrato = concetrato;
	}
	public String getSoluzione() {
		return soluzione;
	}
	public void setSoluzione(String soluzione) {
		this.soluzione = soluzione;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
	public Monitoraggio getMonitoriaggio() {
		return monitoriaggio;
	}
	public void setMonitoriaggio(Monitoraggio monitoriaggio) {
		this.monitoriaggio = monitoriaggio;
	}
	public Articolo getArticolo() {
		return articolo;
	}
	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	
	
}
