package com.winimpresa.mobile.database;

import java.util.ArrayList;

import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.GlobalConstants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MonitoraggioVociTable extends ConnectionDb {

	private final String TABLE_NAME = "Monitoraggio_voci";

	public MonitoraggioVociTable(Context ctx, SQLiteDatabase db) {
		super(ctx, db);
	}

	public int getNumDipositivi(long cod_monitoraggio) {
		String selectQuery = " SELECT *  " + " FROM " + TABLE_NAME
				+ " where cod_Mon =" + cod_monitoraggio;
		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);

		int count = 0;

		while (cursor.moveToNext()) {

			if (cursor.getString(cursor.getColumnIndex("qrcode")) != null
					&& !cursor.getString(cursor.getColumnIndex("qrcode"))
							.equalsIgnoreCase(GlobalConstants.DISPOSTIVODELETE))
				count++;

			if (cursor.getString(cursor.getColumnIndex("qrcode")) == null)
				count++;

		}

		return count;

	}

	public ArrayList<MonitoriaggioVoci> getAllDispoisitivi(
			ArrayList<MonitoriaggioVoci> moni, long cod_monitoraggio) {
		String selectQuery = " SELECT *  " + " FROM " + TABLE_NAME
				+ " where cod_Mon =" + cod_monitoraggio + "  ";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);

		while (cursor.moveToNext()) {
			MonitoriaggioVoci moni_voci = new MonitoriaggioVoci();
			moni_voci.setId_voce((cursor.getInt(cursor
					.getColumnIndex("Id voce"))));
			moni_voci.setAmbiente((cursor.getString(cursor
					.getColumnIndex("ambiente"))));
			moni_voci.setSettore((cursor.getString(cursor
					.getColumnIndex("settore"))));
			moni_voci.setErogatore((cursor.getString(cursor
					.getColumnIndex("Erogatore"))));
			moni_voci.setCod_dispositivo((cursor.getString(cursor
					.getColumnIndex("cod_dispositivo"))));
			moni_voci.setSigla_dispositivo((cursor.getString(cursor
					.getColumnIndex("sigla_dispositivo"))));
			moni_voci.setQrcode((cursor.getString(cursor
					.getColumnIndex("qrcode"))));

			try {
				if (!cursor.getString(cursor.getColumnIndex("qrcode"))
						.equalsIgnoreCase(GlobalConstants.DISPOSTIVODELETE))
					moni.add(moni_voci);

			} catch (Exception e) {
				// TODO: handle exception
				moni.add(moni_voci);
			}

		}

		cursor.close();
		return moni;
	}

	public MonitoriaggioVoci getDispoisitivo(MonitoriaggioVoci moni_voci,
			int id_voce) {

		String selectQuery = " SELECT *  " + " FROM " + TABLE_NAME
				+ " where \"Id voce\" =" + id_voce + "";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			moni_voci.setId_voce((cursor.getInt(cursor
					.getColumnIndex("Id voce"))));
			moni_voci.setAttivita((cursor.getString(cursor
					.getColumnIndex("Attivita"))));
			moni_voci.setStatoEsca((cursor.getString(cursor
					.getColumnIndex("Stato_Esca"))));
			moni_voci.setProdottoSost((cursor.getString(cursor
					.getColumnIndex("Tipo_Sost"))));
			moni_voci.setStato_adesivo((cursor.getString(cursor
					.getColumnIndex("Stato_Adesivo"))));
			moni_voci.setBlattella_n((cursor.getString(cursor
					.getColumnIndex("Blattella_N"))));
			moni_voci.setBlattella_a((cursor.getString(cursor
					.getColumnIndex("Blattella_A"))));
			moni_voci.setBlatta_n((cursor.getString(cursor
					.getColumnIndex("Blatta_N"))));
			moni_voci.setBlatta_a((cursor.getString(cursor
					.getColumnIndex("Blatta_A"))));
			moni_voci.setSupella_n((cursor.getString(cursor
					.getColumnIndex("Supella_N"))));
			moni_voci.setSupella_a((cursor.getString(cursor
					.getColumnIndex("Supella_A"))));
			moni_voci.setPeriplaneta_n((cursor.getString(cursor
					.getColumnIndex("Periplaneta_N"))));
			moni_voci.setPeriplaneta_a((cursor.getString(cursor
					.getColumnIndex("Periplaneta_A"))));
			moni_voci.setAltro((cursor.getString(cursor
					.getColumnIndex("Altro"))));
			
			moni_voci.setLasioderma((cursor.getString(cursor
					.getColumnIndex("Lasioderma"))));
			moni_voci.setTribolium((cursor.getString(cursor
					.getColumnIndex("Tribolium"))));
			moni_voci.setOryzephilus((cursor.getString(cursor
					.getColumnIndex("Oryzephilus"))));
			moni_voci.setRhyzopertha((cursor.getString(cursor
					.getColumnIndex("Rhyzopertha"))));
			moni_voci.setStegobium((cursor.getString(cursor
					.getColumnIndex("Stegobium"))));
			moni_voci.setFeromone((cursor.getString(cursor
					.getColumnIndex("Feromone"))));
			moni_voci.setFarfalle(cursor.getString(cursor
					.getColumnIndex("Farfalle")));
		
			moni_voci.setMosche((cursor.getString(cursor
					.getColumnIndex("Mosche"))));
			moni_voci.setMoscerini((cursor.getString(cursor
					.getColumnIndex("Moscerini"))));
			moni_voci.setZanzare((cursor.getString(cursor
					.getColumnIndex("Zanzare"))));
			moni_voci.setImenoptera((cursor.getString(cursor
					.getColumnIndex("Imenoptera"))));
			
			moni_voci.setPlodia((cursor.getString(cursor
					.getColumnIndex("Plodia"))));
			moni_voci.setEphestia((cursor.getString(cursor
					.getColumnIndex("Ephestia"))));
			moni_voci.setTinella((cursor.getString(cursor
					.getColumnIndex("Tinea"))));
			moni_voci.setTineola((cursor.getString(cursor
					.getColumnIndex("Tineola"))));
			
			moni_voci.setErogatore((cursor.getString(cursor
					.getColumnIndex("Erogatore"))));
			
			moni_voci.setCod_dispositivo((cursor.getString(cursor
					.getColumnIndex("cod_dispositivo"))));
			moni_voci.setSigla_dispositivo((cursor.getString(cursor
					.getColumnIndex("sigla_dispositivo"))));
			
			
			moni_voci.setGrado_infe((cursor.getString(cursor
					.getColumnIndex("grado_inf"))));
			
			moni_voci.setQrcode((cursor.getString(cursor
					.getColumnIndex("qrcode"))));

		}

		cursor.close();
		return moni_voci;
	}

	public int controllerQrcode(int id_voce, String qrcode) {
		int idVoce = 0;
		String selectQuery = " SELECT *  " + " FROM " + TABLE_NAME
				+ " where \"Id voce\" !=" + id_voce + " and qrcode='" + qrcode
				+ "'";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);

		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			idVoce = cursor.getInt(cursor.getColumnIndex("Id voce"));

		}

		return idVoce;
	}

	public boolean updateDispositivoQrcode(MonitoriaggioVoci mon) {

		try {

			String updateQuery = "UPDATE  " + TABLE_NAME + " SET " + "qrcode='"
					+ mon.getQrcode() + "' " + "WHERE  \"Id voce\"="
					+ mon.getId_voce() + "";
			// INSERIRE NELLA LOG TABELLA

			ourDatabase.execSQL(updateQuery);
			ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));
			return true;

		} catch (Exception e) {

			return false;
		}
	}

	
	public String[] getArticoli(String[] articoli) {
		String selectQuery = " SELECT *   FROM Articoli where obsoleto=0";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
		articoli = new String[cursor.getCount()+1];
		int i = 1;
		articoli[0] = "/";

		while (cursor.moveToNext()) {
			articoli[i] = cursor
					.getString(cursor.getColumnIndex("Descrizione"));

			i++;
		}

		cursor.close();
		return articoli;
	}
	
	public String[] getTipoDispositivo(String[] tipoDisp) {
		String selectQuery = " SELECT *   FROM Tipo_Dispositivo ";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
		tipoDisp = new String[cursor.getCount()+1];
		int i = 1;
		tipoDisp[0] = "/";

		while (cursor.moveToNext()) {
			tipoDisp[i] = cursor
					.getString(cursor.getColumnIndex("Descrizione"));

			i++;
		}

		cursor.close();
		return tipoDisp;
	}
	
	
	public String[] getTaAttivita(String[] attivita) {
		String selectQuery = " SELECT *  " + " FROM taattivita";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
		attivita = new String[cursor.getCount()+1];
		int i = 1;
		attivita[0] = "/";

		while (cursor.moveToNext()) {
			attivita[i] = cursor
					.getString(cursor.getColumnIndex("Descrizione"));

			i++;
		}

		cursor.close();
		return attivita;
	}

	public String[] getTaStatoEsca(String[] statoEsca) {
		String selectQuery = " SELECT *  " + " FROM TaStatoEsca";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
		statoEsca = new String[cursor.getCount() + 1];

		int i = 1;
		statoEsca[0] = "/";

		while (cursor.moveToNext()) {

			statoEsca[i] = cursor.getString(cursor
					.getColumnIndex("Descrizione"));

			i++;
		}

		cursor.close();
		return statoEsca;
	}

	public String[] getTaTipoSostituzione(String[] tipoSot) {
		String selectQuery = " SELECT *  " + " FROM tatiposostituzione";

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
		tipoSot = new String[cursor.getCount() + 1];
		int i = 1;
		tipoSot[0] = "/";
		while (cursor.moveToNext()) {
			tipoSot[i] = cursor.getString(cursor.getColumnIndex("Descrizione"));

			i++;
		}

		cursor.close();
		return tipoSot;
	}

	public boolean updateMonitoraggio(MonitoriaggioVoci mon, int tipo) {
		System.out.println(mon.getProdottoSost());
		String updateQuery = "";
		try {

			switch (tipo) {
			case 1:
				updateQuery = "UPDATE  " + TABLE_NAME + " SET " + "Attivita='"
						+ mon.getAttivita() + "', " + "Stato_Esca='"
						+ mon.getStatoEsca() + "', " + "Tipo_Sost='"
						+ mon.getProdottoSost() + "' " + "WHERE  \"Id voce\"="
						+ mon.getId_voce() + "";
				break;
				
			
				
			case 2:
				updateQuery = "UPDATE  " + TABLE_NAME + " SET " + "Attivita='"
						+ mon.getAttivita() + "', " + "Stato_Adesivo='"
						+ mon.getStato_adesivo() + "' " 
						+ "WHERE  \"Id voce\"="
						+ mon.getId_voce() + "";
				break;
				
			case 3:
				updateQuery = "UPDATE  " + TABLE_NAME + " SET " 
			            + "Blattella_N ='"+mon.getBlattella_n()+"', "
			            + "Blattella_A ='"+mon.getBlattella_a()+"', "
			            + "Blatta_A ='"+mon.getBlatta_a()+"', "
			            + "Blatta_N ='"+mon.getBlatta_n()+"', "
			            + "Supella_N ='"+mon.getSupella_n()+"', "
			            + "Supella_A ='"+mon.getSupella_a()+"', "
			            + "Periplaneta_N ='"+mon.getPeriplaneta_n()+"', "
			            + "Periplaneta_A ='"+mon.getPeriplaneta_a()+"', "
			            + "Altro ='"+mon.getAltro()+"', "
			           + "grado_inf ='"+mon.getGrado_infe()+"', "
			           + "Stato_Adesivo='"+ mon.getStato_adesivo() + "' " 
						+ "WHERE  \"Id voce\"="+ mon.getId_voce() + "";
				break;
				
			case 4:
				updateQuery = "UPDATE  " + TABLE_NAME + " SET " 
			            + "Lasioderma ='"+mon.getLasioderma()+"', "
			            + "Tribolium ='"+mon.getTribolium()+"', "
			            + "Oryzephilus ='"+mon.getOryzephilus()+"', "
			            + "Rhyzopertha ='"+mon.getRhyzopertha()+"', "
			            + "Stegobium ='"+mon.getStegobium()+"', "
			            + "Feromone ='"+mon.getFeromone()+"', "
			            + "Altro ='"+mon.getAltro()+"', "
			           + "grado_inf ='"+mon.getGrado_infe()+"', "
			           + "Stato_Adesivo='"+ mon.getStato_adesivo() + "' " 
						+ "WHERE  \"Id voce\"="+ mon.getId_voce() + "";
				break;
				
			case 5:
				updateQuery = "UPDATE  " + TABLE_NAME + " SET " 
			            + "Farfalle ='"+mon.getFarfalle()+"', "
			            + "Moscerini ='"+mon.getMoscerini()+"', "
			            + "Zanzare ='"+mon.getZanzare()+"', "
			            + "Mosche ='"+mon.getMosche()+"', "
			            + "Imenoptera ='"+mon.getImenoptera()+"', "
			            + "Altro ='"+mon.getAltro()+"', "
			           + "grado_inf ='"+mon.getGrado_infe()+"', "
			           + "Stato_Adesivo='"+ mon.getStato_adesivo() + "' " 
						+ "WHERE  \"Id voce\"="+ mon.getId_voce() + "";
				break;
			case 6:
				updateQuery = "UPDATE  " + TABLE_NAME + " SET " 
			            + "Plodia ='"+mon.getPlodia()+"', "
			            + "Ephestia ='"+mon.getEphestia()+"', "
			            + "Tinea ='"+mon.getTinella()+"', "
			            + "Tineola ='"+mon.getTineola()+"', "
			            + "Altro ='"+mon.getAltro()+"', "
			           + "grado_inf ='"+mon.getGrado_infe()+"', "
			            + "Feromone ='"+mon.getFeromone()+"', "
			           + "Stato_Adesivo='"+ mon.getStato_adesivo() + "' " 
						+ "WHERE  \"Id voce\"="+ mon.getId_voce() + "";
				break;
				
			case 19:
				updateQuery = "UPDATE  " + TABLE_NAME + " SET " + "Attivita='"
						+ mon.getAttivita() + "', " + "Stato_Esca='"
						+ mon.getStatoEsca() + "', " + "Tipo_Sost='"
						+ mon.getProdottoSost() + "' " + "WHERE  \"Id voce\"="
						+ mon.getId_voce() + "";
				break;
				
				default:
					updateQuery = "UPDATE  " + TABLE_NAME + " SET " + "Attivita='"
							+ mon.getAttivita() + "', " + "Stato_Esca='"
							+ mon.getStatoEsca() + "', " + 
							"Erogatore='" + mon.getErogatore() + "', " +
							"cod_dispositivo='" + mon.getCod_dispositivo() + "', " +
							"sigla_dispositivo='" + mon.getSigla_dispositivo() + "', " +
							"Tipo_Sost='" + mon.getProdottoSost() + "' " 
							
							+ "WHERE  \"Id voce\"="
							+ mon.getId_voce() + "";
			}
			
		
			System.out.println(updateQuery);
			ourDatabase.execSQL(updateQuery);
			ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));

			return true;
			
		} catch (Exception e) {

			return false;
		}
	}

	public boolean updateMonitoraggioVoci(MonitoriaggioVoci mon) {

		String updateQuery = "";
		try {

			updateQuery = "UPDATE  " + TABLE_NAME + " SET " + "ambiente='"
					+ mon.getAmbiente().replaceAll("'", "''") + "', "
					+ "settore='" + mon.getSettore().replaceAll("'", "''")
					+ "', " + "cod_dispositivo='"
					+ mon.getCod_dispositivo().replaceAll("'", "''") + "', "
					+ "sigla_dispositivo='"
					+ mon.getSigla_dispositivo().replaceAll("'", "''") + "', "
					+ "Erogatore='" + mon.getErogatore().replaceAll("'", "''")
					+ "' " + "WHERE  \"Id voce\"=" + mon.getId_voce() + "";

			ourDatabase.execSQL(updateQuery);
			ourDatabase.execSQL(GlobalConstants.getQueryLog(updateQuery));
			return true;

		} catch (Exception e) {

			return false;
		}
	}

	public MonitoriaggioVoci insertMonitoraggioVoci(MonitoriaggioVoci mon,
			long cod) {

		mon.setId_voce(getMaxId() + 1);

		mon.setAmbiente("");
		mon.setSettore("");
		mon.setErogatore("");
		mon.setCod_dispositivo("");
		mon.setSigla_dispositivo("");
		mon.setQrcode(null);

		String insertQuery = "";
		try {

			insertQuery = "INSERT INTO  " + TABLE_NAME
					+ " (\"Id voce\",cod_Mon) VALUES ( '" + mon.getId_voce()
					+ "' ,'" + cod + "') ";
			
			
			ourDatabase.execSQL(insertQuery);
			ourDatabase.execSQL(GlobalConstants.getQueryLog(insertQuery));
			System.out.println("query "+insertQuery);
			
			return mon;

		} catch (Exception e) {

			return null;
		}
	}

	public int getMaxId() {
		String selectQuery = " SELECT max(\"Id voce\") as max " + " FROM "
				+ TABLE_NAME;

		Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
		int num = 0;
		while (cursor.moveToNext()) {
			num = cursor.getInt(cursor.getColumnIndex("max"));

		}

		cursor.close();
		return num;
	}

}
