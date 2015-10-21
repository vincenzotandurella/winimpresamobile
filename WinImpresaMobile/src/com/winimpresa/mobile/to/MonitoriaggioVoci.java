package com.winimpresa.mobile.to;

public class MonitoriaggioVoci extends Monitoraggio {
	
	private int id_voce;
	private String ambiente;
	private String settore;
	private String tipo_dispositivo;
	private String cod_dispositivo;
	private String sigla_dispositivo;
	private String erogatore;
	private String qrcode;
	private String attivita;
	private String statoEsca;
	private String prodottoSost;
	
	
	 public String getAttivita() {
		return attivita;
	}


	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}


	public String getStatoEsca() {
		return statoEsca;
	}


	public void setStatoEsca(String statoEsca) {
		this.statoEsca = statoEsca;
	}


	public String getProdottoSost() {
		return prodottoSost;
	}


	public void setProdottoSost(String prodottoSost) {
		this.prodottoSost = prodottoSost;
	}


	public String getQrcode() {
		return qrcode;
	}


	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}


	public String getErogatore() {
		return erogatore;
	}


	public int getId_voce() {
		return id_voce;
	}


	public void setId_voce(int id_voce) {
		this.id_voce = id_voce;
	}


	public void setErogatore(String erogatore) {
		this.erogatore = erogatore;
	}


	public String getAmbiente() {
		return ambiente;
	}


	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}


	public String getSettore() {
		return settore;
	}


	public void setSettore(String settore) {
		this.settore = settore;
	}


	public String getTipo_dispositivo() {
		return tipo_dispositivo;
	}


	public void setTipo_dispositivo(String tipo_dispositivo) {
		this.tipo_dispositivo = tipo_dispositivo;
	}


	public String getCod_dispositivo() {
		return cod_dispositivo;
	}


	public void setCod_dispositivo(String cod_dispositivo) {
		this.cod_dispositivo = cod_dispositivo;
	}


	public String getSigla_dispositivo() {
		return sigla_dispositivo;
	}


	public void setSigla_dispositivo(String sigla_dispositivo) {
		this.sigla_dispositivo = sigla_dispositivo;
	}


	public MonitoriaggioVoci (){
		 super();
	 }

	 
	 
	 
	 
	 
}
