package com.winimpresa.mobile.to;

public class Buono {
	

	
	
	private String titolo;
	private int tipologia;
	private int status;
	
	public Buono(String titolo,int tipologia,int status){
		
		this.titolo    = titolo;
		this.tipologia = tipologia; 
		this.status    = status;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getTitolo() {
		return titolo;
	}


	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}


	public int getTipologia() {
		return tipologia;
	}


	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	
	

}
