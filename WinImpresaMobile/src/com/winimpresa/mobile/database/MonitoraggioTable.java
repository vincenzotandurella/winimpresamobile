package com.winimpresa.mobile.database;

import java.util.ArrayList;

import com.winimpresa.mobile.R;
import com.winimpresa.mobile.to.Monitoraggio;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MonitoraggioTable  {
	public SQLiteDatabase ourDatabase;
	public Context ctx;
	
	private final String TABLE_NAME = "Monitoraggio";
	
	
	public MonitoraggioTable(Context ctx , SQLiteDatabase db){
		this.ourDatabase = db;
		this.ctx = ctx;
	}
	
	
	public  ArrayList<Monitoraggio> selectAllMonitoraggio(ArrayList<Monitoraggio> listMonitoriaggio){
		
		String selectQuery = 		"SELECT DISTINCT id_Monitoraggio,Nominativo,Tipo_Scheda,Nom2,applicazione,commessa_n,Area,citta,data_Monitoraggio,sigla_dispositivo,evaso  "
								+   "FROM " + TABLE_NAME +  " as m inner  join Monitoraggio_voci mv on (m.id_Monitoraggio=mv.cod_Mon) where Tipo_Scheda=1  LIMIT 10;";

		
		Cursor			cursor	= ourDatabase.rawQuery(selectQuery, null);

	
		while (cursor.moveToNext()) {
			Monitoraggio monit= new Monitoraggio();
			
			
			monit.setId_monitoraggio(( cursor.getInt(cursor.getColumnIndex("id_Monitoraggio"))));
			monit.setTipo_scheda(( cursor.getInt(cursor.getColumnIndex("Tipo_Scheda"))));
			monit.setCliente(( cursor.getString(cursor.getColumnIndex("Nominativo"))));
			monit.setCliente2(( cursor.getString(cursor.getColumnIndex("Nom2"))));
			monit.setDescrizione(( cursor.getString(cursor.getColumnIndex("applicazione"))));
			monit.setCommessa_n(( cursor.getInt(cursor.getColumnIndex("commessa_n"))));
			monit.setArea(( cursor.getString(cursor.getColumnIndex("Area"))));
			monit.setCitta(( cursor.getString(cursor.getColumnIndex("citta"))));
			monit.setData(GlobalConstants.spiltDate(( cursor.getString(cursor.getColumnIndex("data_Monitoraggio")))));
			monit.setSigla(( cursor.getString(cursor.getColumnIndex("sigla_dispositivo"))));
			
			int ev =  cursor.getInt(cursor.getColumnIndex("evaso"));
			
			switch (ev) {
			case 0:
				monit.setEvaso(R.drawable.zero);
				break;

			case 1:
				monit.setEvaso(R.drawable.uno);
				break;
			default:
				break;
			}
			
			
		
			
			
			listMonitoriaggio.add(monit);
		     
		}
		cursor.close();
		
	   return listMonitoriaggio;
		
	}
	
	
	
	public Monitoraggio getInfoMonitoraggio (long id_monitoraggio){
		
		Monitoraggio monit = new Monitoraggio();
		
		String selectQuery = 		" SELECT id_Monitoraggio,Monitoraggio_n,commessa_n,data_Monitoraggio,CampoNote,ora_iniz,ora_fine,Segnalazioni,Automezzo,Km,comunica_ope,Andamento,vpa,Nominativo,Tipo_Scheda,Nom2,tipo_monit "
								+   " FROM " + TABLE_NAME    
								+ 	" where id_Monitoraggio ="+id_monitoraggio+"";

        Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
   
        
        
        if(cursor.getCount()>0){
            cursor.moveToFirst();
        	
            monit.setId_monitoraggio(( cursor.getInt(cursor.getColumnIndex("id_Monitoraggio"))));
            monit.setTipo_scheda(( cursor.getInt(cursor.getColumnIndex("Tipo_Scheda"))));
            monit.setMonitoraggio_n(( cursor.getInt(cursor.getColumnIndex("Monitoraggio_n"))));
        	monit.setData(GlobalConstants.spiltDate(( cursor.getString(cursor.getColumnIndex("data_Monitoraggio")))));
        	monit.setNoteIntervento(( cursor.getString(cursor.getColumnIndex("CampoNote"))));
        	monit.setOra_inizio(( GlobalConstants.spiltTime(cursor.getString(cursor.getColumnIndex("ora_iniz")))));
        	monit.setOra_fine(( GlobalConstants.spiltTime(cursor.getString(cursor.getColumnIndex("ora_fine")))));
        	monit.setSegnalazioni_anomalie(( cursor.getString(cursor.getColumnIndex("Segnalazioni"))));
        	monit.setAutomezzo(( cursor.getString(cursor.getColumnIndex("Automezzo"))));
        	monit.setKm(( cursor.getInt(cursor.getColumnIndex("Km"))));
        	monit.setComunicazione_operatore(( cursor.getString(cursor.getColumnIndex("comunica_ope"))));
        	monit.setAndamento(( cursor.getString(cursor.getColumnIndex("Andamento"))));
        	monit.setVpa(( cursor.getString(cursor.getColumnIndex("vpa"))));
        	monit.setTipo_monitoraggio(( cursor.getString(cursor.getColumnIndex("tipo_monit"))));
        	monit.setCommessa_n(( cursor.getInt(cursor.getColumnIndex("commessa_n"))));
		
        	
        }
        
        return monit;
	}
	
	public boolean updateBuono (Monitoraggio mon){
		
	 System.out.println("id"+mon.getId_monitoraggio());
		try{
		
		String updateQuery = 	"UPDATE  "+TABLE_NAME+" SET "
							  + "CampoNote='"+mon.getNoteIntervento().replaceAll("'", "''")+"', "
							  + "Segnalazioni ='"+mon.getSegnalazioni_anomalie().replaceAll("'", "''")+"', "
							  + "ora_iniz ='1899-12-30 "+mon.getOra_inizio()+"', "
							  + "ora_fine = '1899-12-30 "+mon.getOra_fine()+"', "
							  + "Automezzo='"+mon.getAutomezzo()+"', "
							  + "Km = '"+mon.getKm()+"' , "
							  + "comunica_ope='"+mon.getComunicazione_operatore().replaceAll("'", "''")+"', evaso=1 "
							  
							  + "WHERE  id_Monitoraggio='"+mon.getId_monitoraggio()+"'"; 
				

     ourDatabase.execSQL(updateQuery);
      
      /* ContentValues values = new ContentValues();
        values.put("CampoNote", "pippo2"); // Contact Name
        String strFilter = "id_Monitoraggio="+mon.getId_monitoraggio();
        Log.d("UserLog","newname=" + values.getAsString("CampoNote"));
        ourDatabase.update(TABLE_NAME, values, strFilter, null);
        */
        
   
        
           return true;
      
        
		}
		catch(Exception e){
			return false;
		}
	}
	
	
	
	
	
	
	
	
}
