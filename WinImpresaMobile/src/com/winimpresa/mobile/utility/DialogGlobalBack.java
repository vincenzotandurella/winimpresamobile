package com.winimpresa.mobile.utility;

import com.winimpresa.mobile.ActivityBase;
import com.winimpresa.mobile.R;
import com.winimpresa.mobile.interfaces.ActivityInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class DialogGlobalBack extends DialogFragment {
    private   ActivityInterface activity ;
    private   String message;
	
	public DialogGlobalBack(ActivityBase act,String mes){
    	
		activity = act;
		message  = mes;
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
               .setPositiveButton(getResources().getString(R.string.labelSi), new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                	   activity.backActivity();
                   }
               })
               .setNegativeButton(getResources().getString(R.string.labelNo), new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}