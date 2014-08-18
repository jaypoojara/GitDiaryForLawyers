package com.rhmc.diaryforlawyers;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewRecord extends Activity implements OnClickListener,OnItemSelectedListener{
	private static final int DATE_DIALOG_ID = 0;
	String date_selected;
	TextView tvCaseno,tvCourt,tvParty,tvCategory,tvStage,tvSelectdate,tvComment,tvCity;
	EditText edtCaseno,edtCourt,edtParty,edtCity,edtComment,edtOppnentName,edtJudgeName;
	Spinner spCategory;
	Button btnSubmit,btnClear,btnDate;
	String cat;
	SQLiteDatabase db;
	DatabaseHelper helper;
	ArrayAdapter<CharSequence> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_record);
		setTitle("Create New Record");
		setViewId();
		initialize_adapters();
		setAdapters();
		spCategory.setOnItemSelectedListener(this);
		btnClear.setOnClickListener(this);
		btnDate.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		
	}
	
	
	private void setAdapters() {
		// TODO Auto-generated method stub
		spCategory.setAdapter(adapter);
	}

	private void initialize_adapters() {
		// TODO Auto-generated method stub
		adapter =ArrayAdapter.createFromResource(CreateNewRecord.this, R.array.category,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
	}

	private void setViewId() {
		helper = new DatabaseHelper(CreateNewRecord.this);
		db = helper.getWritableDatabase();
		tvCaseno = (TextView) findViewById(R.id.tvCasenoRecord);
		tvCourt = (TextView) findViewById(R.id.tvCourtnameRecord);
		tvParty = (TextView) findViewById(R.id.tvClientRecord);
		tvSelectdate = (TextView) findViewById(R.id.tvDateRecord);
		tvCategory = (TextView) findViewById(R.id.tvCategoryRecord);
		tvComment = (TextView) findViewById(R.id.tvCommentRecord);
		edtCaseno = (EditText) findViewById(R.id.edtCasenoRecord);
		edtCourt = (EditText) findViewById(R.id.edtCourtnameRecord);
		edtComment = (EditText) findViewById(R.id.edtCommentRecord);
		edtParty = (EditText) findViewById(R.id.edtClientRecord);
		edtCity = (EditText) findViewById(R.id.edtCityRecord);
		//edtCaseDetailsClientName = (EditText) findViewById(R.id.edtClientNameCaseDetails);
		edtOppnentName = (EditText) findViewById(R.id.edtVSName);
		spCategory = (Spinner) findViewById(R.id.spCategoryRecord);
		edtJudgeName = (EditText) findViewById(R.id.edtJudgeCreate);
		btnClear = (Button) findViewById(R.id.btnClearRecord);
		btnDate = (Button) findViewById(R.id.btnCreateDate);
		btnSubmit = (Button) findViewById(R.id.btnCreateRecordRecord1);
	}
	@Override
	 protected Dialog onCreateDialog(int id) {
		 Calendar c = Calendar.getInstance();
		 int cyear = c.get(Calendar.YEAR);
		 int cmonth = c.get(Calendar.MONTH);
		 int cday = c.get(Calendar.DAY_OF_MONTH);
		 switch (id) {
		 	case DATE_DIALOG_ID:
		 		return new DatePickerDialog(this,  mDateSetListener,  cyear, cmonth, cday);
		 }
		 return null;
	 }
	 private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
	 // onDateSet method
		 public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			 String day,month;
			 if(String.valueOf(dayOfMonth).length()==1){
				 day = "0"+String.valueOf(dayOfMonth);
			 }
			 else{
				 day = String.valueOf(dayOfMonth);
			 }
			 if(String.valueOf(monthOfYear).length()==1){
				 month = "0"+String.valueOf(monthOfYear+1);
			 }
			 else{
				 month = String.valueOf(monthOfYear);
			 }
			 
			 date_selected = day+"/"+month+"/"+String.valueOf(year);
			 tvSelectdate.setText("Date Selected");
			 btnDate.setText(date_selected);
			 Toast.makeText(CreateNewRecord.this, date_selected, Toast.LENGTH_LONG).show();
		 	 //Toast.makeText(CreateNewRecord.this, c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
		 }
	 };
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_record, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnDate){
			showDialog(DATE_DIALOG_ID);
		}
		if(v==btnSubmit){
			if(!check_all_fields()){
//				insert_data();
				//create_dialoge_box();
				AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewRecord.this);
				builder.setTitle("Are you sure do you want to create record?" );
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						insert_data();
						finish();
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						finish();
					}
				});
				AlertDialog alert_dialoge = builder.create();
				alert_dialoge.show();

//				finish();
			}
		}
		if(v==btnClear){
			cleardata();
		}
	}
	private boolean check_all_fields() {
		// TODO Auto-generated method stub
		boolean counter = false;
		
		if(edtOppnentName.getText().toString().isEmpty()){
			Toast.makeText(CreateNewRecord.this, "Please Enter Case No.", Toast.LENGTH_LONG).show();
			counter = true;
		}
		if(edtCaseno.getText().toString().isEmpty()){
			Toast.makeText(CreateNewRecord.this, "Please Enter Case No.", Toast.LENGTH_LONG).show();
			counter = true;
		}
		if(edtCourt.getText().toString().isEmpty()){
			Toast.makeText(CreateNewRecord.this, "Please Enter Court Name", Toast.LENGTH_LONG).show();
			counter = true;;
		}
		if(edtParty.getText().toString().isEmpty()){
			Toast.makeText(CreateNewRecord.this, "Please Enter Party Name", Toast.LENGTH_LONG).show();
			counter = true;
		}
		if(date_selected.isEmpty()){
			Toast.makeText(CreateNewRecord.this, "Please Enter a date", Toast.LENGTH_LONG).show();
			counter = true;;
		}
		if(cat.isEmpty()){
			Toast.makeText(CreateNewRecord.this, "Please Select a Category", Toast.LENGTH_LONG).show();
			counter = true;;
		}
		if(edtCity.getText().toString().isEmpty()){
			Toast.makeText(CreateNewRecord.this, "Please Enter a City", Toast.LENGTH_LONG).show();
			counter = true;
		}
		return counter;
	}


	private void create_dialoge_box() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewRecord.this);
		builder.setTitle("Are you sure do you want to create record?" );
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				insert_data();
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		AlertDialog alert_dialoge = builder.create();
		alert_dialoge.show();
	}


	private void cleardata() {
		// TODO Auto-generated method stub
		edtCaseno.setText("");
		edtComment.setText("");
		edtCourt.setText("");
		edtParty.setText("");
		edtCity.setText("");
		tvSelectdate.setText("Select date");
		btnDate.setText("Select A Date");
	}
	private void insert_data() {
		// TODO Auto-generated method stub
		db = helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		ContentValues cv1 = new ContentValues();
		cv.put(DatabaseHelper.CASE_NO, edtCaseno.getText().toString());
		cv.put(DatabaseHelper.JUDGE_NAME, edtJudgeName.getText().toString());
		cv.put(DatabaseHelper.COURT_NAME, edtCourt.getText().toString());
		cv.put(DatabaseHelper.PARTY_NAME, edtParty.getText().toString());
		cv.put(DatabaseHelper.OPPONENT_NAME, edtOppnentName.getText().toString());
		cv.put(DatabaseHelper.DATE,date_selected);
		cv.put(DatabaseHelper.CASE_NO, edtCaseno.getText().toString());
		cv.put(DatabaseHelper.CATEGORY, cat);
		cv.put(DatabaseHelper.COMMENT, edtComment.getText().toString());
		cv.put(DatabaseHelper.CITY, edtCity.getText().toString());
		
		db.insert(DatabaseHelper.TABLE,null, cv);
		db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE+" WHERE " + DatabaseHelper.CASE_NO +" = '"+ edtCaseno.getText().toString() + "' AND " + DatabaseHelper.PARTY_NAME + " = '" + edtParty.getText().toString() +"'", null);
		c.moveToFirst();
		db = helper.getWritableDatabase();
		cv1.put(DatabaseHelper.SRNO, c.getString(c.getColumnIndex(DatabaseHelper.SRNO)));
		cv1.put(DatabaseHelper.DATE, date_selected);
		db.insert(DatabaseHelper.DATE_TABLE, null, cv1);
		db.close();
		startActivity(new Intent(CreateNewRecord.this, MainActivity.class));
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		cat = arg0.getItemAtPosition(arg2).toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
