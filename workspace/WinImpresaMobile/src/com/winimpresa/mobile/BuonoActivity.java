package com.winimpresa.mobile;

import java.util.Calendar;
















import com.winimpresa.mobile.utility.DialogGlobalBack;

import android.app.ActionBar;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class BuonoActivity extends ActivityBase {
 private ImageButton  buttonOraInizio;
 private ImageButton  buttonOraFine;
 private TextView oraInizio;
 private TextView oraFine;
 private Button  settori; 
 
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.activity_buono);
		
		ActionBar ab = getActionBar();
	    ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		ab.setIcon( new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ab.setSubtitle(user.getFullName() + " / "+user.getIdUser());
		
		buttonOraInizio  =  (ImageButton) findViewById(R.id.buttonOraInizio);
		buttonOraFine    =  (ImageButton) findViewById(R.id.buttonOraFine);
		oraInizio		 =  (TextView) findViewById(R.id.oraInizio);
		oraFine		     =  (TextView) findViewById(R.id.oraFine);
		settori		     =  (Button) findViewById(R.id.settori);
		
	
		
		buttonOraInizio.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					 Calendar mcurrentTime = Calendar.getInstance();
		                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		                int minute = mcurrentTime.get(Calendar.MINUTE);
		                TimePickerDialog tpd = new TimePickerDialog(context,
		                        new TimePickerDialog.OnTimeSetListener() {
		     
		                            @Override
		                            public void onTimeSet(TimePicker view, int hourOfDay,
		                                    int minute) {
		                                // Display Selected time in textbox
		                                oraInizio.setText(hourOfDay + ":" + minute);
		                            }
		                        }, hour, minute, false);
		                tpd.show();
				
				}
			});
		
		buttonOraFine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 Calendar mcurrentTime = Calendar.getInstance();
	                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
	                int minute = mcurrentTime.get(Calendar.MINUTE);
	                TimePickerDialog tpd = new TimePickerDialog(context,
	                        new TimePickerDialog.OnTimeSetListener() {
	     
	                            @Override
	                            public void onTimeSet(TimePicker view, int hourOfDay,
	                                    int minute) {
	                                // Display Selected time in textbox
	                            	oraFine.setText(hourOfDay + ":" + minute);
	                            }
	                        }, hour, minute, false);
	                tpd.show();
			
			}
		});
		
		settori.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goSettori();
			
			}
		});
		
	}
   
   public void backList(){
	   NavUtils.navigateUpFromSameTask(this);
	   
		 overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
		 finish();
   }
   
   public void  goSettori(){
	     Intent page_settori= new Intent(context, SettoriActivity.class);
	  	 startActivity(page_settori);
		 overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
   }

   
	@Override
	public void backActivity() {
		// TODO Auto-generated method stub
		backList();
	}
    
	 @Override
	 public void onBackPressed() {
		 showDialog();
	    
	 }
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buono, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		int id = item.getItemId();
		if (id == R.id.save) {
			return true;
		}
		else{
			showDialog();
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	public void showDialog(){
		
		DialogGlobalBack dialog = new DialogGlobalBack(this,getResources().getString(R.string.labelMessaggeControlSaveBuono));
        dialog.show(getFragmentManager(),"");
	}
}