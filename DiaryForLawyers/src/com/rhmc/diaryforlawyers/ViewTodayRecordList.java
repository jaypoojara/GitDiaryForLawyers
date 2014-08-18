package com.rhmc.diaryforlawyers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTodayRecordList extends Activity implements OnItemClickListener{
	String caseNo[],courtName[],partyName[],city[],opponentname[];
	int srno[];
	Cursor c,d;
	String date_today;
	DatabaseHelper helper;
	SQLiteDatabase db;	
	ListView li;
	CustomTodayAdapter myad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_today_record_list);
		setTitle("Today's Record");
		initialization();
		
	}

	private void initialization() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		date_today = sdf.format(new Date());
		Toast.makeText(ViewTodayRecordList.this, date_today, Toast.LENGTH_LONG).show();
		helper = new DatabaseHelper(ViewTodayRecordList.this);
		db = helper.getReadableDatabase();
		d = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE+" WHERE "+DatabaseHelper.DATE+" = '"+date_today+"'"+";" ,null);
		caseNo = new String[d.getCount()];
		courtName = new String[d.getCount()];
		partyName = new String[d.getCount()];
		city = new String[d.getCount()];
		srno = new int[d.getCount()];
		opponentname = new String[d.getCount()];
		//c.moveToFirst();
//		String sql;
//		for(int i=0;i<c.getCount();i++){
//			sql = "select * from "+DatabaseHelper.TABLE;
//			d = db.query(DatabaseHelper.TABLE, null, DatabaseHelper.SRNO+"=?", new String[]{String.valueOf(c.getInt(0))}, null, null, null);
////			Log.d("SQL", c.getInt(0)+"");
//			d.moveToFirst();
//	//		Log.d("Data1", d.getString(d.getColumnIndex(DatabaseHelper.CITY))+"");
//			
//		//	Log.d("Date", d.getString(d.getColumnIndex(DatabaseHelper.PARTY_NAME))+"");
//			caseNo[i] = d.getString(d.getColumnIndex(DatabaseHelper.CASE_NO));
//			courtName[i] = d.getString(d.getColumnIndex(DatabaseHelper.COURT_NAME));
//			partyName[i] = d.getString(d.getColumnIndex(DatabaseHelper.PARTY_NAME));
//			city[i] = d.getString(d.getColumnIndex(DatabaseHelper.CITY));
//			srno[i] = d.getInt(d.getColumnIndex(DatabaseHelper.SRNO));
//			c.moveToNext();
//		}
		d.moveToFirst();
		for(int i=0;i<d.getCount();i++){
			caseNo[i] = d.getString(d.getColumnIndex(DatabaseHelper.CASE_NO));
			courtName[i] = d.getString(d.getColumnIndex(DatabaseHelper.COURT_NAME));
			partyName[i] = d.getString(d.getColumnIndex(DatabaseHelper.PARTY_NAME));
			city[i] = d.getString(d.getColumnIndex(DatabaseHelper.CITY));
			srno[i] = d.getInt(d.getColumnIndex(DatabaseHelper.SRNO));
			opponentname[i] = d.getString(d.getColumnIndex(DatabaseHelper.OPPONENT_NAME));
			d.moveToNext();
		}
		li = (ListView) findViewById(R.id.lstToday);
		myad = new CustomTodayAdapter(ViewTodayRecordList.this);
		li.setAdapter(myad);
		for(int j=0;j<caseNo.length;j++){
			Log.d("CaseNo",caseNo[j]);
		}
		li.setOnItemClickListener(this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_today_record_list, menu);
		return true;
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
			TextView tvOpponentname = (TextView) convertView.findViewById(R.id.tvOpponentNameShowCustom);
			tvCaseNo.setText(caseNo[position]);
			tvCourtName.setText(courtName[position]);
			tvPartyName.setText(partyName[position]);
			tvCity.setText(city[position]);
			tvDate.setText(date_today);
			tvOpponentname.setText(opponentname[position]);
			tvCaseNo.setTextColor(Color.BLUE);
			tvCourtName.setTextColor(Color.BLUE);
			tvPartyName.setTextColor(Color.BLUE);
			tvCity.setTextColor(Color.BLUE);
			tvDate.setTextColor(Color.BLUE);
			tvOpponentname.setTextColor(Color.BLUE);
			if(position % 2 ==0){
				convertView.setBackgroundResource(R.drawable.listview_selector_even);
				
			}
			else{
				convertView.setBackgroundResource(R.drawable.listview_selector_odd);
			}

			return convertView;
		
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent(ViewTodayRecordList.this, EditRecord.class);
		i.putExtra("SRNO", srno[arg2]);
		//Log.d("PutExtra", myad.getItem((int) myad.getItemId(arg2)));
		startActivity(i);
		finish();
	}
}
