package com.demo.android.bmi;

import java.text.DecimalFormat;

import android.support.v7.app.ActionBarActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Bmi extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi);
		
		findViews();
		setListensers();
	}
	
	private Button button_calc;
	private EditText fieldheight;
	private EditText fieldweight;
	private TextView view_result;
	private TextView view_suggest;
	
	private void findViews()
	{
		button_calc = (Button) findViewById(R.id.submit);
		fieldheight = (EditText) findViewById(R.id.height);
		fieldweight = (EditText) findViewById(R.id.weight);
		view_result = (TextView) findViewById(R.id.result);
		view_suggest = (TextView) findViewById(R.id.suggest);
	}
	
	private void setListensers()
	{
		button_calc.setOnClickListener((android.view.View.OnClickListener) calcBMI);
	}
		
		// listen for button clicks
//		Button button = (Button)findViewById(R.id.submit);
//		button.setOnClickListener((android.view.View.OnClickListener) calcBMI);
	
	
	private Button.OnClickListener calcBMI = new Button.OnClickListener() {	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			DecimalFormat nf = new DecimalFormat("0.00");
			double height = Double.parseDouble(fieldheight.getText().toString())/100;
			double weight = Double.parseDouble(fieldweight.getText().toString());
			double BMI = weight/(height * height);
			
			// Present result
			view_result.setText("Your BMI is "+nf.format(BMI));
			
			// give health advice
			if(BMI>25) {
				view_suggest.setText(R.string.advice_heavy);
			}
			else if(BMI<20) {
				view_suggest.setText(R.string.advice_light);
			}
			else {
				view_suggest.setText(R.string.advice_average);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bmi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
