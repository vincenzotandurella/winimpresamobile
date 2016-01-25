package com.winimpresa.mobile.database;

import java.util.ArrayList;

import com.winimpresa.mobile.R;
import com.winimpresa.mobile.to.Articolo;
import com.winimpresa.mobile.to.Magazzino;
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

		String selectQuery = 		"SELECT DISTINCT  id_Monitoraggio,Nominativo,Tipo_Scheda,Nom2,applicazione,commessa_n,Area,citta,data_Monitoraggio,Tipo_Dispositivo,evaso, Andamento  "
								+   "FROM " + TABLE_NAME +  " as m  ";
							//	+ "inner  join Monitoraggio_voci mv on (m.id_Monitoraggio=mv.cod_Mon) ";

		
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
			monit.setSigla(( cursor.getString(cursor.getColumnIndex("Tipo_Dispositivo"))));
			monit.setAndamento(( cursor.getString(cursor.getColumnIndex("Andamento"))));
			
			int ev =  cursor.getInt(cursor.getColumnIndex("evaso"));
			
			switch (ev) {
			case 0:
				monit.setEvaso(R.drawable.zero);
				monit.setStatoEvaso(false);
				break;

			case 1:
				monit.setEvaso(R.drawable.uno);
				monit.setStatoEvaso(true);
				break;
			default:
				break;
			}
			
			
		
			
			
			listMonitoriaggio.add(monit);
		     
		}
		cursor.close();
		
	   return listMonitoriaggio;
		
	}
	
	
	public  int selectAllMonitoraggioStatus(){

		String selectQuery = 		"SELECT * "
								+   "FROM " + TABLE_NAME +  " WHERE EVASO = 0 ";
							

		
		Cursor			cursor	= ourDatabase.rawQuery(selectQuery, null);
	
	
		
		
		
	   return cursor.getCount();
		
	}
	

	public boolean updateStatoMonitoraggio (Monitoraggio mon){
		
	
		try{
		int status = 0;
		
		if(mon.isStatoEvaso())
			status=1;
			
			
		String updateQuery = 	"UPDATE  "+TABLE_NAME+" SET "
							  + "evaso='"+status+"'"
							
							  + "WHERE  id_Monitoraggio='"+mon.getId_monitoraggio()+"'"; 
				
      ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));
      ourDatabase.execSQL(updateQuery);
  
  
        
   
        
           return true;
      
        
		}
		catch(Exception e){
			return false;
		}
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
        	monit.setDataNoFormat(( cursor.getString(cursor.getColumnIndex("data_Monitoraggio"))));
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
	
	
public ArrayList<Magazzino> getListMagazzino (ArrayList<Magazzino> list , long currentBuono,String tabella){
		
		
		
		String selectQuery = 		" SELECT * "
								+   " FROM  "+tabella   
								+ 	" where id_monitoraggio ="+currentBuono+"";

        Cursor	cursor	= ourDatabase.rawQuery(selectQuery, null);
   
        Monitoraggio mon = this.getInfoMonitoraggio(currentBuono);


		while (cursor.moveToNext()) {
        
         
            Magazzino dati = new Magazzino();
            Articolo art = new Articolo();
            dati.setArticolo(art);
            dati.setMonitoriaggio(mon);
            dati.getArticolo().setCodice(cursor.getString(cursor.getColumnIndex("codice")));
            dati.setId(cursor.getInt(cursor.getColumnIndex("id")));
            dati.getArticolo().setId(cursor.getString(cursor.getColumnIndex("id articolo")));
            dati.getArticolo().setDescrizione(cursor.getString(cursor.getColumnIndex("articolo")));
            dati.getArticolo().setUnita(cursor.getString(cursor.getColumnIndex("unità")));
            
            
            
            list.add(dati);
        	
        }
        
        return list;
	}

public int getMaxId(String table) {
	String selectQuery = " SELECT max(\"id\") as max " + " FROM "
			+ table;

	Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
	int num = 0;
	while (cursor.moveToNext()) {
		num = cursor.getInt(cursor.getColumnIndex("max"));

	}

	cursor.close();
	return num;
}

public boolean aggiungiCaricoScarico (Magazzino maga, String operazione){
	
	
	try{
		int new_id = this.getMaxId(operazione)+1; 
		
		
	String updateQuery = 	"INSERT INTO "+operazione+"   "
						+ "('id','id_monitoraggio','monitoraggio_n','data_monitoraggio','id articolo','codice','articolo','unità','qtà','prezzo','principio','perc_conc','lt_soluz')  "
						 +  " VALUES ('"+new_id+"','"+maga.getMonitoriaggio().getId_monitoraggio()+"',"
						 +  "'"+maga.getMonitoriaggio().getMonitoraggio_n() +"','"+maga.getMonitoriaggio().getDataNoFormat()+"',"
						 + "'"+maga.getArticolo().getId() +"','"+maga.getArticolo().getCodice()+"','"+maga.getArticolo().getDescrizione()+"',"
						 + "'"+maga.getArticolo().getUnita()+"', '"+maga.getQuantita()+"','"+maga.getPrezzo()+"','"+maga.getPrincipio()+"','"+maga.getConcetrato()+"',"
						 + " '"+maga.getSoluzione()+"' )";
			

     ourDatabase.execSQL(updateQuery);
	 ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));

     

    
       return true;
  
    
	}
	catch(Exception e){
		   System.out.println(e);
		return false;
	}
}

public boolean updateCaricoScarico (Magazzino maga, String operazione){
	
	
	try{
		
		
		
	
	String updateQuery = 	"UPDATE  "+operazione+" SET "
			  + "\"id articolo\"='"+maga.getArticolo().getId()+"', "
			  + "codice ='"+maga.getArticolo().getCodice()+"', "
			  + "articolo ='"+maga.getArticolo().getDescrizione()+"', "
			  + "unità = '"+maga.getArticolo().getUnita()+"', "
			  + "qtà='"+maga.getQuantita()+"', "
			  + "prezzo = '"+maga.getPrezzo()+"' , "
			  + "principio = '"+maga.getPrincipio()+"' , "
			  + "perc_conc='"+maga.getConcetrato()+"' ,"
			  + "lt_soluz='"+maga.getSoluzione()+"' "
			  
			  + "WHERE  id='"+maga.getId()+"'"; 		

     ourDatabase.execSQL(updateQuery);
	 ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));

     

    
       return true;
  
    
	}
	catch(Exception e){
		   System.out.println(e);
		return false;
	}
}


public boolean deleteCaricoScarico (Magazzino maga, String operazione){
	
	
	try{
		
		
		
	
	String updateQuery = 	"DELETE FROM  "+operazione+"  "
			
			  
			  + "WHERE  id='"+maga.getId()+"'"; 		

     ourDatabase.execSQL(updateQuery);
	 ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));

     

    
       return true;
  
    
	}
	catch(Exception e){
		   System.out.println(e);
		return false;
	}
}
	
	public boolean updateBuono (Monitoraggio mon){
		
	
		try{
		
		String updateQuery = 	"UPDATE  "+TABLE_NAME+" SET "
							  + "CampoNote='"+mon.getNoteIntervento().replaceAll("'", "''")+"', "
							  + "Segnalazioni ='"+mon.getSegnalazioni_anomalie().replaceAll("'", "''")+"', "
							  + "ora_iniz ='1899-12-30 "+mon.getOra_inizio()+"', "
							  + "ora_fine = '1899-12-30 "+mon.getOra_fine()+"', "
							  + "Automezzo='"+mon.getAutomezzo()+"', "
							  + "Km = '"+mon.getKm()+"' , "
							  + "Andamento = '"+mon.getAndamento()+"' , "
							  + "comunica_ope='"+mon.getComunicazione_operatore().replaceAll("'", "''")+"' "
							  
							  + "WHERE  id_Monitoraggio='"+mon.getId_monitoraggio()+"'"; 
				
	
      ourDatabase.execSQL(updateQuery);
  	ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));
  
        
   
        
           return true;
      
        
		}
		catch(Exception e){
			return false;
		}
	}
	
	
	
	
	
	
	
	
}
