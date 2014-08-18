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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditRecord extends Activity implements OnClickListener,OnItemSelectedListener{
	private static final int DATE_DIALOG_ID = 0;
	String date_selected;
	//String caseNo,courtName,partyName,city,category,date,comment;
	String all_date[];
	TextView tvCaseno,tvCourt,tvParty,tvCategory,tvStage,tvSelectdate,tvComment,tvCity;
	EditText edtCaseno,edtCourt,edtParty,edtCity,edtComment,edtOppnentName,edtJudgeName;
	Spinner spCategory;
	Button btnUpdate,btnClear,btnDate,btnAllHearingDate;
	String cat;
	SQLiteDatabase db;
	DatabaseHelper helper;
	ArrayAdapter<CharSequence> adapter;
	int srno,count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_record);
		setTitle("Edit Record");
		setViewId();
		initialize_adapters();
		setAdapters();
		getAllDate();
		retriveAllRecord();
		spCategory.setOnItemSelectedListener(this);
		btnClear.setOnClickListener(this);
		btnDate.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnAllHearingDate.setOnClickListener(this);
	}

	
	private void setAdapters() {
		// TODO Auto-generated method stub
		spCategory.setAdapter(adapter);
	}

	private void initialize_adapters() {
		// TODO Auto-generated method stub
		adapter =ArrayAdapter.createFromResource(EditRecord.this, R.array.category,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	private void getAllDate(){
		db = helper.getReadableDatabase();
		Cursor c = db.query(DatabaseHelper.DATE_TABLE, null, DatabaseHelper.SRNO+"=?",new String[]{String.valueOf(srno)}, null, null, null);
		c.moveToFirst();
		all_date = new String[c.getCount()];
		for(int i=0;i<c.getCount();i++){
			all_date[i]=c.getString(c.getColumnIndex(DatabaseHelper.DATE));
			c.moveToNext();
		}
	}
	private void setViewId() {
		
		helper = new DatabaseHelper(EditRecord.this);
		db = helper.getReadableDatabase();
		tvCaseno = (TextView) findViewById(R.id.tvCasenoEdit);
		tvCourt = (TextView) findViewById(R.id.tvCourtnameEdit);
		tvParty = (TextView) findViewById(R.id.tvClientEdit);
		tvSelectdate = (TextView) findViewById(R.id.tvDateEdit);
		tvCategory = (TextView) findViewById(R.id.tvCategoryEdit);
		tvComment = (TextView) findViewById(R.id.tvCommentEdit);
		edtCaseno = (EditText) findViewById(R.id.edtCasenoEdit);
		edtCourt = (EditText) findViewById(R.id.edtCourtnameEdit);
		edtComment = (EditText) findViewById(R.id.edtCommentEdit);
		edtParty = (EditText) findViewById(R.id.edtClientEdit);
		//edtCaseDetailsClientName = (EditText) findViewById(R.id.edtClientNameCaseDetails);
		edtOppnentName = (EditText) findViewById(R.id.edtOpponentNameEditRecord);
		edtCity = (EditText) findViewById(R.id.edtCityEdit);
		spCategory = (Spinner) findViewById(R.id.spCategoryEdit);
		edtJudgeName = (EditText) findViewById(R.id.edtJudgeNameEdit);
		btnClear = (Button) findViewById(R.id.btnClearEdit);
		btnDate = (Button) findViewById(R.id.btnCreateDate);
		btnUpdate = (Button) findViewById(R.id.btnCreateEditEdit1);
		btnAllHearingDate = (Button) findViewById(R.id.btnAllHearingDateEdit);
		srno = getIntent().getExtras().getInt("SRNO");
	}
	private void retriveAllRecord() {
		// TODO Auto-generated method stub
		int position=0;
		db = helper.getReadableDatabase();
		Cursor d = db.query(DatabaseHelper.TABLE, null, DatabaseHelper.SRNO+"=?", new String[]{String.valueOf(srno)}, null, null, null);
		d.moveToFirst();
		edtOppnentName.setText(d.getString(d.getColumnIndex(DatabaseHelper.OPPONENT_NAME)));
		edtCaseno.setText(d.getString(d.getColumnIndex(DatabaseHelper.CASE_NO)));
		edtJudgeName.setText(d.getString(d.getColumnIndex(DatabaseHelper.JUDGE_NAME)));
		edtCity.setText(d.getString(d.getColumnIndex(DatabaseHelper.CITY)));
		edtParty.setText(d.getString(d.getColumnIndex(DatabaseHelper.PARTY_NAME)));
		edtComment.setText(d.getString(d.getColumnIndex(DatabaseHelper.COMMENT)));
		edtCourt.setText(d.getString(d.getColumnIndex(DatabaseHelper.COURT_NAME)));
		position = adapter.getPosition(d.getString(d.getColumnIndex(DatabaseHelper.CATEGORY)));
		spCategory.setSelection(position);
	//	Log.d("SRNO", srno+"");
		Cursor c = db.query(DatabaseHelper.DATE_TABLE,null, DatabaseHelper.SRNO+"=?", new String[]{String.valueOf(srno)}, null, null, null);
		c.moveToFirst();
		c.moveToLast();
		date_selected = c.getString(c.getColumnIndex(DatabaseHelper.DATE));
		btnDate.setText(date_selected);
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
			 Toast.makeText(EditRecord.this, date_selected, Toast.LENGTH_LONG).show();
		 	 //Toast.makeText(EditRecord.this, c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR), Toast.LENGTH_LONG).show();
		 }
	 };
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_record, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnDate){
			showDialog(DATE_DIALOG_ID);
		}
		if(v==btnUpdate){
			if(!check_all_fields()){
				create_dialoge_box();
				finish();
			}
			
		}
		if(v==btnClear){
			cleardata();
		}
		if(v==btnAllHearingDate){
			create_all_date_dialogue_box();
		}
	}
	private void create_all_date_dialogue_box() {
		AlertDialog.Builder builder = new AlertDialog.Builder(EditRecord.this);
		builder.setTitle("All Hearing Dates").setItems(all_date, null);
		
		AlertDialog alert = builder.create();
		alert.show();
	}


	private boolean check_all_fields() {
		// TODO Auto-generated method stub
		boolean counter = false;
		if(edtOppnentName.getText().toString().isEmpty()){
			Toast.makeText(EditRecord.this, "Please Enter Opponent Name", Toast.LENGTH_SHORT).show();
			counter = true;
		}
		if(edtCaseno.getText().toString().isEmpty()){
			Toast.makeText(EditRecord.this, "Please Enter Case No.", Toast.LENGTH_SHORT).show();
			counter = true;
		}
		if(edtCourt.getText().toString().isEmpty()){
			Toast.makeText(EditRecord.this, "Please Enter Court Name", Toast.LENGTH_SHORT).show();
			counter = true;;
		}
		if(edtParty.getText().toString().isEmpty()){
			Toast.makeText(EditRecord.this, "Please Enter Party.", Toast.LENGTH_SHORT).show();
			counter = true;
		}
		if(date_selected.isEmpty()){
			Toast.makeText(EditRecord.this, "Please Enter a date", Toast.LENGTH_LONG).show();
			counter = true;;
		}
		if(cat.isEmpty()){
			Toast.makeText(EditRecord.this, "Please Select a Category", Toast.LENGTH_LONG).show();
			counter = true;;
		}
		if(edtCity.getText().toString().isEmpty()){
			Toast.makeText(EditRecord.this, "Please Enter a City", Toast.LENGTH_SHORT).show();
			counter = true;
		}
		return counter;
	}


	private void create_dialoge_box() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(EditRecord.this);
		builder.setTitle("Are you sure do you want to Edit Record?" );
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				update_data();
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
		edtOppnentName.setText("");
		edtCaseno.setText("");
		edtComment.setText("");
		edtCourt.setText("");
		edtParty.setText("");
		edtCity.setText("");
		tvSelectdate.setText("Select date");
		btnDate.setText("Select A Date");
	}
	private void update_data() {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		ContentValues cv1 = new ContentValues();
		cv.put(DatabaseHelper.CASE_NO, edtCaseno.getText().toString());
		cv.put(DatabaseHelper.JUDGE_NAME, edtJudgeName.getText().toString());
		cv.put(DatabaseHelper.COURT_NAME, edtCourt.getText().toString());
		cv.put(DatabaseHelper.PARTY_NAME, edtParty.getText().toString());
		cv.put(DatabaseHelper.OPPONENT_NAME, edtOppnentName.getText().toString());
		cv.put(DatabaseHelper.DATE, btnDate.getText().toString());
		cv.put(DatabaseHelper.CATEGORY, cat);
		cv.put(DatabaseHelper.COMMENT, edtComment.getText().toString());
		cv.put(DatabaseHelper.CITY, edtCity.getText().toString());
		
		cv1.put(DatabaseHelper.SRNO, srno);
		cv1.put(DatabaseHelper.DATE,btnDate.getText().toString());
		for(int i=0;i<all_date.length;i++){
			if(all_date[i].equals(btnDate.getText().toString())){
				count++;
			}
		}
		db = helper.getWritableDatabase();
		db.update(DatabaseHelper.TABLE, cv,DatabaseHelper.SRNO + "=" + srno , null);
		if(count==0)
			db.insert(DatabaseHelper.DATE_TABLE, null, cv1);
		startActivity(new Intent(EditRecord.this, MainActivity.class));
		
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
