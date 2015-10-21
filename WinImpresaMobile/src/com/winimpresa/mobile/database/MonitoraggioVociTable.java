package com.winimpresa.mobile.database;



import java.util.ArrayList;

import com.winimpresa.mobile.to.Monitoraggio;
import com.winimpresa.mobile.to.MonitoriaggioVoci;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MonitoraggioVociTable extends ConnectionDb {
	
	private final String TABLE_NAME = "Monitoraggio_voci";
	
	
	public MonitoraggioVociTable(Context ctx , SQLiteDatabase db){
		super(ctx,db);
	}
	
	
	public int getNumDipositivi(long cod_monitoraggio) {
		String selectQuery = 		" SELECT *  "
								+   " FROM " + TABLE_NAME    
								+ 	" where cod_Mon ="+cod_monitoraggio+"";

					Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
					
					
					
					return cursor.getCount();
					
	}
	
	
	public ArrayList<MonitoriaggioVoci> getAllDispoisitivi(ArrayList<MonitoriaggioVoci> moni ,long cod_monitoraggio) {
		String selectQuery = 		" SELECT *  "
							    +   " FROM " + TABLE_NAME    
							    + 	" where cod_Mon ="+cod_monitoraggio+"";

					Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
					
					
					
					while (cursor.moveToNext()) {
						MonitoriaggioVoci moni_voci = new MonitoriaggioVoci();
						moni_voci.setId_voce(( cursor.getInt(cursor.getColumnIndex("Id voce"))));
						moni_voci.setAmbiente(( cursor.getString(cursor.getColumnIndex("ambiente"))));
						moni_voci.setSettore(( cursor.getString(cursor.getColumnIndex("settore"))));
						moni_voci.setErogatore(( cursor.getString(cursor.getColumnIndex("Erogatore"))));
						moni_voci.setCod_dispositivo(( cursor.getString(cursor.getColumnIndex("cod_dispositivo"))));
						moni_voci.setSigla_dispositivo(( cursor.getString(cursor.getColumnIndex("sigla_dispositivo"))));
						moni_voci.setQrcode(( cursor.getString(cursor.getColumnIndex("qrcode"))));
						
						System.out.println("qrcode ---->"+cursor.getString(cursor.getColumnIndex("qrcode")));

						moni.add(moni_voci);
					}
					
					cursor.close();
					return moni;
	}
	
	public MonitoriaggioVoci getDispoisitivo(MonitoriaggioVoci moni_voci,int id_voce) {
		
		String selectQuery = 		" SELECT *  "
							    +   " FROM " + TABLE_NAME    
							    + 	" where \"Id voce\" ="+id_voce+"";

					Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
					
					
					
					if(cursor.getCount()>0){
			            cursor.moveToFirst();
						
						moni_voci.setId_voce(( cursor.getInt(cursor.getColumnIndex("Id voce"))));
						moni_voci.setAttivita(( cursor.getString(cursor.getColumnIndex("Attivita"))));
						moni_voci.setStatoEsca(( cursor.getString(cursor.getColumnIndex("Stato_Esca"))));
						moni_voci.setProdottoSost(( cursor.getString(cursor.getColumnIndex("Tipo_Sost"))));
						
						moni_voci.setQrcode(( cursor.getString(cursor.getColumnIndex("qrcode"))));
						
					

						
					}
					
					cursor.close();
					return moni_voci;
	}
	
	public boolean updateDispositivoQrcode (MonitoriaggioVoci mon){
		
		
		try{
		
		String updateQuery = 	"UPDATE  "+TABLE_NAME+" SET "
							  + "qrcode='"+mon.getQrcode()+"' "
							  + "WHERE  \"Id voce\"="+mon.getId_voce()+""; 
				

		   ourDatabase.execSQL(updateQuery);
		   
		  
      
           return true;
      
        
		}
		catch(Exception e){
			
			return false;
		}
	}
	
	
	public String[] getTaAttivita(String[] attivita) {
		String selectQuery = 		" SELECT *  "
							    +   " FROM taattivita";     
							   

					Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
					attivita = new String[cursor.getCount()];
					int i =0;
					
					while (cursor.moveToNext()) {
						attivita[i] =		cursor.getString(cursor.getColumnIndex("Descrizione"));
				
					i++;
					}
					
					cursor.close();
					return attivita;
	}
	
	public String[] getTaStatoEsca(String[] statoEsca) {
		String selectQuery = 		" SELECT *  "
							    +   " FROM TaStatoEsca";     
							   

					Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
					statoEsca = new String[cursor.getCount()];
					int i =0;
					
					while (cursor.moveToNext()) {
						statoEsca[i] =		cursor.getString(cursor.getColumnIndex("Descrizione"));
				
					i++;
					}
					
					cursor.close();
					return statoEsca;
	}
	public String[] getTaTipoSostituzione(String[] tipoSot) {
		String selectQuery = 		" SELECT *  "
							    +   " FROM tatiposostituzione";     
							   

					Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
					tipoSot = new String[cursor.getCount()];
					int i =0;
					
					while (cursor.moveToNext()) {
						tipoSot[i] =		cursor.getString(cursor.getColumnIndex("Descrizione"));
				
					i++;
					}
					
					cursor.close();
					return tipoSot;
	}
	
	
    public boolean updateMonitoraggio (MonitoriaggioVoci mon, int tipo){
		System.out.println(mon.getProdottoSost());
    	String updateQuery ="";
		try{
		
			switch (tipo) {
			case 1:
				updateQuery = 	"UPDATE  "+TABLE_NAME+" SET "
						      + "Attivita='"+mon.getAttivita()+"', "
						      + "Stato_Esca='"+mon.getStatoEsca()+"', "
						      + "Tipo_Sost='"+mon.getProdottoSost()+"' "
						      + "WHERE  \"Id voce\"="+mon.getId_voce()+"";
				break;

			default:
				break;
			}
			
			 
				

		   ourDatabase.execSQL(updateQuery);
		   
		  
      
           return true;
      
        
		}
		catch(Exception e){
			
			return false;
		}
	}
	
	
}
