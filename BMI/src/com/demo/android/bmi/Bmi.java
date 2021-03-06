package com.demo.android.bmi;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Bmi extends ActionBarActivity {
	private static final String TAG = "Bmi";
	public static final String PREF = "BMI_PREF";
	public static final String PREF_HEIGHT = "BMI_Height";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi);
		
		findViews();
		restorePrefs();
		setListensers();
		Log.v(TAG, "onCreate");
	}
	
	private Button button_calc;
	private EditText fieldheight;
	private EditText fieldweight;
	private TextView view_result;
	private TextView view_suggest;
	
	private void findViews()
	{
		Log.d(TAG, "find Views");
		button_calc = (Button) findViewById(R.id.submit);
		fieldheight = (EditText) findViewById(R.id.height);
		fieldweight = (EditText) findViewById(R.id.weight);
		view_result = (TextView) findViewById(R.id.result);
		view_suggest = (TextView) findViewById(R.id.suggest);
	}
	
	private void setListensers()
	{
		Log.d(TAG, "set Listeners");
		button_calc.setOnClickListener((android.view.View.OnClickListener) calcBMI);
	}
		
		// listen for button clicks
//		Button button = (Button)findViewById(R.id.submit);
//		button.setOnClickListener((android.view.View.OnClickListener) calcBMI);
	
	
	private Button.OnClickListener calcBMI = new Button.OnClickListener() {	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			DecimalFormat nf = new DecimalFormat("0.00");
//			try {
//				double height = Double.parseDouble(fieldheight.getText().toString())/100;
//				double weight = Double.parseDouble(fieldweight.getText().toString());
//				double BMI = weight/(height * height);
//				
//				// Present result
//				view_result.setText("Your BMI is "+nf.format(BMI));
//				
//				// give health advice
//				if(BMI>25) {
//					view_suggest.setText(R.string.advice_heavy);
//				}
//				else if(BMI<20) {
//					view_suggest.setText(R.string.advice_light);
//				}
//				else {
//					view_suggest.setText(R.string.advice_average);
//				}
//				
//				//openOptionsDialog();
//			}
//			catch(Exception err)
//			{
//				Toast.makeText(Bmi.this, R.string.input_error, Toast.LENGTH_SHORT).show();
//			}
			
			Intent intent = new Intent();
			intent.setClass(Bmi.this, Report.class);
			Bundle bundle = new Bundle();
			bundle.putString("KEY_HEIGHT", fieldheight.getText().toString());
			bundle.putString("KEY_WEIGHT", fieldweight.getText().toString());
			intent.putExtra("REPORT_INTENT", bundle);
			startActivity(intent);
		}
	};
	
	private void openOptionsDialog() {
		//Toast.makeText(Bmi.this, "BMI ������", Toast.LENGTH_SHORT).show();
		
		new AlertDialog.Builder(Bmi.this)
		.setTitle(R.string.about_title)
		.setMessage(R.string.about_msg)
		.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setNegativeButton(R.string.homepage_label, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// go to url
				Uri uri = Uri.parse(getString(R.string.homepage_uri));
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);				
			}
		})
		.show();
		
	}
	
	protected static final int MENU_ABOUT = Menu.FIRST;
	protected static final int MENU_Quit = Menu.FIRST + 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bmi, menu);
		menu.add(0, MENU_ABOUT, 0, "����...");
		menu.add(0, MENU_Quit, 0, "����");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
		
		super.onOptionsItemSelected(item);
		switch(item.getItemId()) {
		case MENU_ABOUT:
			openOptionsDialog();
			break;
		case MENU_Quit:
			finish();
			break;
		}
		return true;
	}

	// Restore preferences
	private void restorePrefs()
	{
		SharedPreferences setting = getSharedPreferences(PREF, 0);
		String pref_height = setting.getString(PREF_HEIGHT, "");
		if(! "".equals(pref_height))
		{
			fieldheight.setText(pref_height);
			fieldweight.requestFocus();
		}
	}
	
	public void onStart()
	{
		super.onStart();
		Log.v(TAG, "onStart");
	}
	
	public void OnResume()
	{
		super.onResume();
		Log.v(TAG, "onResume");
	}
	
	public void OnPause()
	{
		super.onPause();
		Log.v(TAG, "onPause");
	}
	
	public void onStop()
	{
		super.onStop();
		SharedPreferences settings = getSharedPreferences(PREF, 0);
		settings.edit()
		.putString(PREF_HEIGHT, fieldheight.getText().toString())
		.commit();
		Log.v(TAG, "onStop");
	}
	
	public void onRestart()
	{
		super.onRestart();
		Log.v(TAG, "onRestart");
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}
}
