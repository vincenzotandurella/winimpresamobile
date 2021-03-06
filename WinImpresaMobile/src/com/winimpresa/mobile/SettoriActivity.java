package com.winimpresa.mobile;

import java.util.Calendar;

import com.winimpresa.mobile.to.MonitoriaggioVoci;
import com.winimpresa.mobile.utility.DialogGlobalBack;

import android.app.ActionBar;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class SettoriActivity extends ActivityBase {
	
	private ViewFlipper viewFlipper;
    private TextView paginazione;
    private EditText siglaDisp;
    private int test = 3;
    private Button  avanti;
    private Button indietro;
  
	
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
    	super.onCreate(savedInstanceState,R.layout.activity_settori);
		ActionBar ab = getActionBar();
	    ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		ab.setIcon( new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ab.setSubtitle(user.getFullName() + " / "+user.getIdUser());
		
	
		
		 viewFlipper   = (ViewFlipper) findViewById(R.id.viewflipper);
	     avanti        = (Button) findViewById(R.id.avanti);
	     indietro      = (Button) findViewById(R.id.indietro);
	     paginazione   = (TextView) findViewById(R.id.paginazione);
	     
		 this.createSettoriTest();
		 setPaginazione();
		 avanti.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (viewFlipper.getDisplayedChild() == 3)
		                  return;
					
					
				 	 viewFlipper.setInAnimation(context, R.anim.slide_in_from_right);
		            	// Current screen goes out from left. 
		            	 viewFlipper.setOutAnimation(context, R.anim.slide_out_to_left);
					 viewFlipper.showNext();
					 setPaginazione();
				}
			});
		 
		 indietro.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (viewFlipper.getDisplayedChild() == 0)
		                  return;
					 viewFlipper.setInAnimation(context, R.anim.slide_in_from_left);
		               
		                viewFlipper.setOutAnimation(context, R.anim.slide_out_to_right);
					 viewFlipper.showPrevious();
					 setPaginazione();
				}
			});
	}

	public void createSettoriTest(){
		
		
		for(int i=0; i<=test; i++){	
			
          	 LayoutInflater inflater = (LayoutInflater) this
		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 View view = inflater.inflate(R.layout.view_settore_default, null);

			 viewFlipper.addView(view,i);
			 
			// TextView text = (TextView) view.findViewById(R.id.text);
			// text.setText("Testo "+i);
			 
			 
		}
		
		
	}
	
	public void setCodQRcode(String result){
		
	
	     View view = viewFlipper.getCurrentView();
	     siglaDisp     = (EditText) view.findViewById(R.id.siglaDisp);
		 siglaDisp.setText(result);
	}
	
	
	public  void setPaginazione(){
		
		String paginazione = " / ";
	     
		paginazione = viewFlipper.getDisplayedChild()+1 +paginazione + viewFlipper.getChildCount();
		
		this.paginazione.setText(paginazione);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settori, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
		case R.id.qrcode:
			scattaQR_CODE();
			break;

		default:
			showDialog();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void backActivity() {
		// TODO Auto-generated method stub
		backBuono();
	}
	 
	
	public void backBuono(){
		   NavUtils.navigateUpFromSameTask(this);
		   
			 overridePendingTransition( R.anim.push_down_in, R.anim.push_down_out);
			 finish();
	   }
	
	 @Override
	 public void onBackPressed() {
		 showDialog();
	    
	 }
	 
		
		public void showDialog(){
			
			DialogGlobalBack dialog = new DialogGlobalBack(this,getResources().getString(R.string.labelMessaggeControlSaveSettore));
	        dialog.show(getFragmentManager(),"");
		}
	

  public void scattaQR_CODE(){
	  String packageString = "com.winimpresa.mobile";
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.setPackage(packageString);
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		 startActivityForResult(intent, 0);	
  }
		
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        if (requestCode == 0) {
	            
	            if (resultCode == RESULT_OK) {
	            	//tvStatus.setText(intent.getStringExtra("SCAN_RESULT_FORMAT"));
	            	//tvResult.setText(intent.getStringExtra("SCAN_RESULT"));
	            	Toast.makeText(this, "Selected position: " + intent.getStringExtra("SCAN_RESULT"), Toast.LENGTH_SHORT).show();
	            	
	            	setCodQRcode(intent.getStringExtra("SCAN_RESULT"));
	            } else if (resultCode == RESULT_CANCELED) {
	            	
	            }
	        }
	    }
	    

	
}
