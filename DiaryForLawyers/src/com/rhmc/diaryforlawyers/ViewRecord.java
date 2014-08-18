package com.rhmc.diaryforlawyers;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewRecord extends Activity {
	Button btnViewAllView,btnViewTodayView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_record);
		setTitle("View Records");
		btnViewAllView = (Button) findViewById(R.id.btnViewAllViewRecord);
		btnViewTodayView = (Button) findViewById(R.id.btnViewTodayView);
		btnViewTodayView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ViewRecord.this, ViewTodayRecordList.class));
				finish();
			}
		});
		btnViewAllView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ViewRecord.this,ViewAllRecordList.class));
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_record, menu);
		return true;
	}

}
