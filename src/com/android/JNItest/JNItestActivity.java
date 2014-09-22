package com.android.JNItest;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class JNItestActivity extends Activity {
    /** Called when the activity is first created. */
	public static String TAG="JNItest";
	public boolean debug = true && BuildConfig.DEBUG;
	public int op=0;
	public TextView tv;
	public Dialog menu;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	if(debug)
    		Log.i(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv=((TextView)findViewById(R.id.myTextField));
        tv.setText(stringFromJNICPP());
        
    	menu = new Dialog(JNItestActivity.this);
        menu.setContentView(R.layout.multi);
        menu.setTitle(R.string.input);
        menu.setCancelable(true);
        
        final RadioGroup r1=((RadioGroup)(menu.findViewById(R.id.radioGroup1)));
        
        Button button = (Button) menu.findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
       	 		if(debug)
       	 			Log.i(TAG,"Multiply-OK");
                try {
                	
                	switch(r1.getCheckedRadioButtonId()){
                	case R.id.radio0:op=1;break;
                	case R.id.radio1:op=2;break;
                	case R.id.radio2:op=3;break;
                	case R.id.radio3:op=4;break;
                	}
                	
                	if(debug)
           	 			Log.i(TAG,"Operacion: "+op);
                		
                	
                	
                	tv.append("\nResultado: "+operacion(
                			Double.parseDouble(
                					(
                					((EditText)menu.findViewById(R.id.editText1))
                					.getText()
                					).toString()
                					),
                			Double.parseDouble((((EditText)menu.findViewById(R.id.editText2)).getText()).toString()),
                			op)               			
                			);
                	menu.dismiss();
				} catch (Throwable e) {	if(debug) 		Log.e(TAG,e.getMessage()+" - Click");}
            }
        });
    }
    
    static{
    	System.loadLibrary("JNItest");
    }
    
    public native String stringFromJNICPP();
    public native double operacion(double a,double b,int op);

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(debug)
    		Log.i(TAG,"OnOptionItemSelected");
        switch (item.getItemId()) {
        case R.menu.option_menu:
        	menu.show();
            return true;
        default:
            
            return false;
        }
    }
}