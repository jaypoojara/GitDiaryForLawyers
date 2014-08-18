package com.rhmc.diaryforlawyers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAllRecordList extends Activity implements OnClickListener,OnItemClickListener,OnItemLongClickListener{
	ListView lstView;
	String caseNo[],courtName[],partyName[],city[],hdate[],opponentname[];
	Cursor c,d;
	DatabaseHelper helper;
	SQLiteDatabase db;	
	Button btnSortName,btnSortDate;
	int srno[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_view_all_record_list);
		setTitle("All Records");
		lstView = (ListView) findViewById(R.id.lstAll);
		btnSortName = (Button) findViewById(R.id.btnSortListViewNamwViewAllRecords);
		btnSortDate = (Button) findViewById(R.id.btnSortListViewCaseNoViewAllRecords);
		btnSortDate.setOnClickListener(this);
		btnSortName.setOnClickListener(this);
		initialization();
	}
	private void initialization() {
		// TODO Auto-generated method stub
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		String date_today = sdf.format(new Date());
		retrive_all_record();
		//Toast.makeText(ViewAllRecordList.this, date_today, Toast.LENGTH_LONG).show();
//		helper = new DatabaseHelper(ViewAllRecordList.this);
//		db = helper.getReadableDatabase();
//		c = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE,null);
//		c.moveToFirst();
//		caseNo = new String[c.getCount()];
//		courtName = new String[c.getCount()];
//		partyName = new String[c.getCount()];
//		city = new String[c.getCount()];
//		hdate = new String[c.getCount()];
//		srno = new int[c.getCount()];
//		for(int i=0;i<c.getCount();i++){
//			try{
//				d = db.rawQuery("SELECT * FROM "+DatabaseHelper.DATE_TABLE+" WHERE "+DatabaseHelper.SRNO+" = " + c.getInt(0), null);
//				Log.d("SQL", "SELECT * FROM "+DatabaseHelper.TABLE+" WHERE "+DatabaseHelper.SRNO+" = " + c.getInt(0));
//				d.moveToFirst();
//				d.moveToLast();
//				caseNo[i] = c.getString(c.getColumnIndex(DatabaseHelper.CASE_NO));
//				courtName[i] = c.getString(c.getColumnIndex(DatabaseHelper.COURT_NAME));
//				partyName[i] = c.getString(c.getColumnIndex(DatabaseHelper.PARTY_NAME));
//				city[i] = c.getString(c.getColumnIndex(DatabaseHelper.CITY));
//				hdate[i] = d.getString(d.getColumnIndex(DatabaseHelper.DATE));
//				srno[i] = c.getInt(c.getColumnIndex(DatabaseHelper.SRNO));
//				//Log.i("TEST1", hdate[i]);
//				c.moveToNext();
//			}
//			catch(Exception e){
//				
//			}
//		}
//		lstView.setAdapter(new CustomTodayAdapter(ViewAllRecordList.this));
//		lstView.setOnItemClickListener(this);
//		lstView.setOnItemLongClickListener(this);
	}
	public class CustomTodayAdapter extends ArrayAdapter<String>{

		public CustomTodayAdapter(Context context) {
			super(context,0,caseNo);
			// TODO Auto-generated constructor stub
		}
		LayoutInflater li = getLayoutInflater();
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = li.inflate(R.layout.custom_view_record, parent,false);
			TextView tvCaseNo = (TextView) convertView.findViewById(R.id.tvCasenoShowCustom);
			TextView tvCourtName = (TextView) convertView.findViewById(R.id.tvCourtNameShowCustom);
			TextView tvPartyName = (TextView) convertView.findViewById(R.id.tvClientNameViewCustom);
			TextView tvCity = (TextView) convertView.findViewById(R.id.tvCityShowCustom);
			TextView tvDate = (TextView) convertView.findViewById(R.id.txtViewDateCustom);
			TextView tvOpponentName = (TextView) convertView.findViewById(R.id.tvOpponentNameShowCustom);
			tvCaseNo.setTextColor(Color.BLUE);
			tvCourtName.setTextColor(Color.BLUE);
			tvPartyName.setTextColor(Color.BLUE);
			tvCity.setTextColor(Color.BLUE);
			tvDate.setTextColor(Color.BLUE);
			tvOpponentName.setTextColor(Color.BLUE);
			tvCaseNo.setText( caseNo[position]);
			tvCourtName.setText(courtName[position]);
			tvPartyName.setText(partyName[position]);
			tvCity.setText(city[position]);
			tvOpponentName.setText(opponentname[position]);
			tvDate.setText(hdate[position]);
			if(position % 2 ==0){
				convertView.setBackgroundResource(R.drawable.listview_selector_even);
				
			}
			else{
				convertView.setBackgroundResource(R.drawable.listview_selector_odd);
			}
			return convertView;
		
		}
		
	}
	private void retrive_all_record() {
		helper = new DatabaseHelper(ViewAllRecordList.this);
		db = helper.getReadableDatabase();
		c = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE,null);
		c.moveToFirst();
		caseNo = new String[c.getCount()];
		courtName = new String[c.getCount()];
		partyName = new String[c.getCount()];
		city = new String[c.getCount()];
		hdate = new String[c.getCount()];
		srno = new int[c.getCount()];
		opponentname = new String[c.getCount()];
		for(int i=0;i<c.getCount();i++){
			try{
				d = db.rawQuery("SELECT * FROM "+DatabaseHelper.DATE_TABLE+" WHERE "+DatabaseHelper.SRNO+" = " + c.getInt(0), null);
				Log.d("SQL", "SELECT * FROM "+DatabaseHelper.TABLE+" WHERE "+DatabaseHelper.SRNO+" = " + c.getInt(0));
				d.moveToFirst();
				d.moveToLast();
				caseNo[i] = c.getString(c.getColumnIndex(DatabaseHelper.CASE_NO));
				courtName[i] = c.getString(c.getColumnIndex(DatabaseHelper.COURT_NAME));
				partyName[i] = c.getString(c.getColumnIndex(DatabaseHelper.PARTY_NAME));
				city[i] = c.getString(c.getColumnIndex(DatabaseHelper.CITY));
				hdate[i] = d.getString(d.getColumnIndex(DatabaseHelper.DATE));
				srno[i] = c.getInt(c.getColumnIndex(DatabaseHelper.SRNO));
				opponentname[i] = c.getString(c.getColumnIndex(DatabaseHelper.OPPONENT_NAME));
				//Log.i("TEST1", hdate[i]);
				c.moveToNext();
			}
			catch(Exception e){
				
			}
		}
		lstView.setAdapter(new CustomTodayAdapter(ViewAllRecordList.this));
		lstView.setOnItemClickListener(this);
		lstView.setLongClickable(true);
		lstView.setOnItemLongClickListener(this);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_record_list, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnSortName){

			String merged[] = new String[partyName.length];
			for(int temp=0;temp<partyName.length;temp++)
			{
				merged[temp] = partyName[temp]+"=="+caseNo[temp]+"=="+courtName[temp]+"=="+city[temp]+"=="+hdate[temp]+"==" + srno[temp];
				
			}
			Arrays.sort(merged);
			for(int temp=0;temp<merged.length;temp++){
				partyName[temp]=merged[temp].split("==")[0];
				caseNo[temp]=merged[temp].split("==")[1];
				courtName[temp]=merged[temp].split("==")[2];
				city[temp]=merged[temp].split("==")[3];
				hdate[temp] = merged[temp].split("==")[4];
				srno[temp]=Integer.valueOf(merged[temp].split("==")[5]);
			}
			CustomTodayAdapter cta = new CustomTodayAdapter(ViewAllRecordList.this);
			lstView.setAdapter(cta);
			cta.notifyDataSetChanged();
		}
		if(v==btnSortDate){
			String merged[] = new String[partyName.length];
			for(int temp=0;temp<partyName.length;temp++)
			{
				merged[temp] = hdate[temp]+"=="+partyName[temp]+"=="+caseNo[temp]+"=="+courtName[temp]+"=="+city[temp]+"==" + srno[temp];
				
			}
			Arrays.sort(merged);
			for(int temp=0;temp<merged.length;temp++){
				hdate[temp]=merged[temp].split("==")[0];
				partyName[temp]=merged[temp].split("==")[1];
				caseNo[temp]=merged[temp].split("==")[2];
				courtName[temp]=merged[temp].split("==")[3];
				city[temp]=merged[temp].split("==")[4];
				srno[temp]=Integer.valueOf(merged[temp].split("==")[5]);
			}
			CustomTodayAdapter cta = new CustomTodayAdapter(ViewAllRecordList.this);
			lstView.setAdapter(cta);
			cta.notifyDataSetChanged();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(ViewAllRecordList.this, EditRecord.class);
		i.putExtra("SRNO", srno[arg2]);
		//Log.d("PutExtra", myad.getItem((int) myad.getItemId(arg2)));
		startActivity(i);
		finish();
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		Toast.makeText(ViewAllRecordList.this, "Long Clicked", Toast.LENGTH_SHORT).show();
		AlertDialog.Builder builder = new AlertDialog.Builder(ViewAllRecordList.this);
		builder.setTitle("Are you sure do you want to delete record ?");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				db = helper.getWritableDatabase();
				db.delete(DatabaseHelper.TABLE, DatabaseHelper.SRNO+"=?", new String[]{String.valueOf(srno[arg2])});
				db.delete(DatabaseHelper.DATE_TABLE, DatabaseHelper.SRNO+"=?", new String[]{String.valueOf(srno[arg2])});
				retrive_all_record();
				CustomTodayAdapter cta = new CustomTodayAdapter(ViewAllRecordList.this);
				lstView.setAdapter(cta);
				cta.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		return true;
	}
	

}
