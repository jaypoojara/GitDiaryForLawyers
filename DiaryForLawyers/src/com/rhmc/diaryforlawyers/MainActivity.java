package com.rhmc.diaryforlawyers;

import java.util.Calendar;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.ContactsContract.Contacts.Data;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	SQLiteDatabase db;
	DatabaseHelper helper;
	Uri aFilePath = null;
	Button btnCreate,btnVew,btnSetAlarm,btnCancelAlarm;
	SharedPreferences sp;
	SharedPreferences.Editor edt;
	public static PendingIntent pendingAlarmIntent;
	AlarmManager alarmManager;
	public static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Select Your Option");
		btnCreate = (Button) findViewById(R.id.btCreateMain);
		btnVew = (Button) findViewById(R.id.btnViewRecordMain);
		btnSetAlarm = (Button) findViewById(R.id.btnSetAlarmMain);
		btnCancelAlarm = (Button) findViewById(R.id.btnCancelAlarmMain);
		btnCancelAlarm.setOnClickListener(this);
		btnCreate.setOnClickListener(this);
		btnVew.setOnClickListener(this);
		btnSetAlarm.setOnClickListener(this);
		
		sp = getSharedPreferences("alarm", MODE_PRIVATE);
		edt = sp.edit();
		context = getApplicationContext();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnCreate){
			startActivity(new Intent(MainActivity.this, CreateNewRecord.class));
			//finish();
		}
		if(v==btnVew){
			startActivity(new Intent(MainActivity.this, ViewRecord.class));
			//finish();
		}
		if(v==btnSetAlarm){
	//		showFileChooser();
			pickTimeHandler(v);
			
		}
		if(v==btnCancelAlarm){
			//alarmManager.cancel(getPI());
			if(getPI()!=null){
				alarmManager.cancel(getPI());			}
			stopService(new Intent(MainActivity.this,MyAlarmService.class));
			Toast.makeText(MainActivity.this, "YOUR ALRAM HAS BEEN CANCELED", Toast.LENGTH_SHORT).show();
		}
	}

//	private void showFileChooser() {
//		// TODO Auto-generated method stub
//		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//		startActivityForResult(Intent.createChooser(i, "Choose Alarm Tone"), 1);
//		
//	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(requestCode==1){
//			if(resultCode==RESULT_OK){
//				aFilePath = data.getData();
//				
//				if(aFilePath!=null){
//					
//				ContentValues cv = new ContentValues();
//		        cv.put(DatabaseHelper.TABLE_COL, aFilePath.toString());
//		        helper = new DatabaseHelper(MainActivity.this);
//		        db = helper.getWritableDatabase();
//		        db.insert(DatabaseHelper.TABLE_URI, null, cv);
//		        //db.close();
//				}
//			}
//		}
//		
//	}
//	public String getRealPathFromURI(Uri contentUri) {
//        String [] proj      = {MediaStore.Audio.Media.DATA};
//        Cursor cursor       = managedQuery( contentUri, proj, null, null,null);
//        
//        if (cursor == null) return null;
// 
//        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
// 
//        cursor.moveToFirst();
// 
//        return cursor.getString(column_index);
//    }

	private void pickTimeHandler(View v) {
		// TODO Auto-generated method stub
		showDialog(0);
		
	}
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		//Toast.makeText(MainActivity.this, "Dialog Creater", Toast.LENGTH_LONG).show();
		Calendar c = Calendar.getInstance();
		int hr = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		return new TimePickerDialog(this,
			     theTimeSetListener,
			     hr, min, false);
		
	}
	private TimePickerDialog.OnTimeSetListener theTimeSetListener
    = new TimePickerDialog.OnTimeSetListener(){
  @Override
  public void onTimeSet(TimePicker view, int hour, int minute) {
    
    int theHour = hour;
    int theMinute = minute;
	//Toast.makeText(MainActivity.this, path, Toast.LENGTH_LONG).show();
    Calendar AlarmCal = Calendar.getInstance();
    AlarmCal.setTimeInMillis(System.currentTimeMillis());
    AlarmCal.set(Calendar.HOUR_OF_DAY, theHour);  // set user selection
    AlarmCal.set(Calendar.MINUTE, theMinute);        // set user selection
    AlarmCal.set(Calendar.SECOND, 0);
    Intent alarmIntent = new Intent(MainActivity.this, MyReceiver.class);
    pendingAlarmIntent = PendingIntent.getBroadcast(getBaseContext(), 0,
                                 alarmIntent, 0);

    alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    alarmManager.setRepeating(AlarmManager.RTC, AlarmCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingAlarmIntent);
    edt.putLong("time_alarm", AlarmCal.getTimeInMillis());
    edt.commit();
  }
};
	public static Context getAppContext(){
		return MainActivity.context;
		
	}
	public static PendingIntent getPI(){
		return pendingAlarmIntent;
	}
}
