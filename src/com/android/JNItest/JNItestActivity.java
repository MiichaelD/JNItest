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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/** This project is used to test JNI functions excecuting code in C++ from Java
 * 
 * Also it uses a CustomArrayAdapter to fit data in a custom view and displays it
 * in a ListView.
 * 
 * Plus, added functionality to handle screen rotations, saving contents from the
 * ArrayAdapter and resoring them when the screen is created again. to achieve this
 * I implemented the interface Parcelable to the class Result to Marshal it, store it
 * and restore it easily.d */
public class JNItestActivity extends Activity {
   
	public static String TAG="JNItest";
	private static final String OPS_KEY = "OPERATIONS_KEY";
	public static boolean debug = true && BuildConfig.DEBUG;
	public int op=0;
	
	// Views
	public TextView tv;
	private ListView mResultsView;
	public Button btn_Calc;
	
	//Array adapter for the Result thread
    private CustomArrayAdapter mResultArrayAdapter;
	
	public Dialog menu;
	

    
    static{
    	Log.i(TAG,"Static Lib Loading");
    	System.loadLibrary("JNItest");
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	if(debug)
    		Log.i(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv=((TextView)findViewById(R.id.tf_greetings));
        tv.setText(stringFromJNICPP());
        
        btn_Calc = ((Button)findViewById(R.id.btn_calc));
        btn_Calc.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
       	 		if(debug)
       	 			Log.i(TAG,"Open menu");
                menu.show();
            }
        });
        
        mResultArrayAdapter = new CustomArrayAdapter(this, R.layout.message);
        
        mResultsView = (ListView) findViewById(R.id.in);
        mResultsView.setAdapter(mResultArrayAdapter);
        
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
                	
                	String operator = null;
                	
                	switch(r1.getCheckedRadioButtonId()){
                	case R.id.radio0:op=1;
	                	operator = "x"; 
	                	break;
                	case R.id.radio1:op=2;
	                	operator = "+"; 
	                	break;
                	case R.id.radio2:op=3;
	                	operator = "/"; 
	                	break;
                	case R.id.radio3:op=4;
	                	operator = "-"; 
	                	break;
                	}
                	
                	if(debug)
           	 			Log.i(TAG,"Operacion: "+op);
                	
                		Result  r = new Result();
                		r.setOperator(operator);
                		r.setX(Double.parseDouble(((EditText)menu.findViewById(R.id.editText1)).getText().toString()));
                		r.setY(Double.parseDouble(((EditText)menu.findViewById(R.id.editText2)).getText().toString()));
                		r.setRes(operacion(r.getX(),r.getY(), op));
                	mResultArrayAdapter.add(r);
                	menu.dismiss();
				} catch (Throwable e) {		if(debug) 		Log.e(TAG,e.getMessage()+" - Click");
					Toast.makeText(JNItestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
            }
        });
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
    
    /** Save data from ListView when rotating the device 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle) */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		 if(debug)
	    		Log.i(TAG,"onRestoreInstanceState restoring  values");
		// Initialize the array adapter for the conversation thread
        if (savedInstanceState != null) {
        	Result[] values = (Result[]) savedInstanceState.getParcelableArray(OPS_KEY);
            for (Result result : values) {
            	mResultArrayAdapter.add(result);
            }
        }
	}
	
	
	/** Save data from ListView when rotating the device */
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        int operations = mResultArrayAdapter.getCount();
        
        if(debug)
    		Log.i(TAG,"onSaveInstanceState saving "+operations+" values");
        
        Result[] values =  new Result[operations];
        for(int i =0 ; i < operations;i++)
        	values[i] = mResultArrayAdapter.getItem(i);
        
        savedState.putParcelableArray(OPS_KEY, values);
    }
    
}