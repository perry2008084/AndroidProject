package com.demo.android.bmi;

import java.text.DecimalFormat;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Report extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		findViews();
		showResults();
		setListeners();
	}
	
	private Button button_back;
	private TextView view_result;
	private TextView view_suggest;
	
	private void findViews()
	{
		button_back = (Button) findViewById(R.id.report_back);
		view_result = (TextView) findViewById(R.id.result);
		view_suggest = (TextView) findViewById(R.id.suggest);
	}
	
	private void setListeners() {
		button_back.setOnClickListener(backMain);
	}
	
	private Button.OnClickListener backMain = new Button.OnClickListener()
	{
		public void onClick(View v)
		{
			Report.this.finish();
		}
	};
	
	private void showResults() {
		DecimalFormat nf = new DecimalFormat("0.00");
		
		Bundle bunde = this.getIntent().getExtras();
		double height = Double.parseDouble(bunde.getString("KEY_HEIGHT"))/100;
		double weight = Double.parseDouble(bunde.getString("KEY_WEIGHT"));
		double BMI = weight / (height * height);
		view_result.setText(getString(R.string.bmi_result) + nf.format(BMI));
		
		if (BMI > 25) {
			view_suggest.setText(R.string.advice_heavy);
		}
		else if (BMI < 20) {
			view_suggest.setText(R.string.advice_light);
		}
		else
		{
			view_suggest.setText(R.string.advice_average);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report, menu);
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
