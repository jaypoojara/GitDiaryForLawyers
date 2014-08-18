package com.rhmc.diaryforlawyers;

import java.io.IOException;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MyAlarm extends Activity {
	MediaPlayer mMediaPlayer;
	//SQLiteDatabase db;
	//DatabaseHelper helper;
	//Cursor c;
	PendingIntent pi;
	SharedPreferences sp;
	long time_millis;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_alarm);
//		helper = new DatabaseHelper(MyAlarm.this);
//		db = helper.getReadableDatabase();
//		c = db.rawQuery("select * from " + DatabaseHelper.TABLE_URI, null);
//		c.moveToFirst();
//		c.moveToLast();
//		String data = c.getString(0);
	      // Create a dialog that the user must dismiss
		sp = getSharedPreferences("alarm", MODE_PRIVATE);
		//sp = getSharedPreferences("pass_pref", MODE_PRIVATE);
		time_millis = sp.getLong("time_alarm", 0);
		Log.d("time",time_millis+"");
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	      alertDialog.setMessage("Remember:Every Case is defensible...!!!");   
	      alertDialog.setCancelable(false);
	      mMediaPlayer = new MediaPlayer();
	      mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	      try {
			mMediaPlayer.setDataSource(MyAlarm.this, uri);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
			mMediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      mMediaPlayer.setLooping(true);
	      mMediaPlayer.start();
	      alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	 
	      public void onClick(DialogInterface dialog, int which) {
	     	  mMediaPlayer.stop();
	     	  final Intent i = new Intent(MyAlarm.this,MyReceiver.class);
//	     	 pi = MainActivity.getPI();
	     	 
	     	 
	     	 stopService(new Intent(MyAlarm.this,MyAlarmService.class));
	     	//startService(new Intent(MyAlarm.this,MyAlarmService.class));
	     	Handler handler = new Handler();
	     	handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//startService(new Intent(MyAlarm.this,MyAlarmService.class));
					//pi = PendingIntent.getBroadcast(getBaseContext(), 1, i, 0);
			     	 AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			     	 am.set(AlarmManager.RTC_WAKEUP, time_millis, MainActivity.getPI());
			     	 startService(i);
				}
			},60000);
	     	 
	     	 dialog.dismiss(); 
	     	 startActivity(new Intent(MyAlarm.this, ViewTodayRecordList.class));
	    	 finish();
	      } }); 
	 
	      alertDialog.setIcon(R.drawable.logo);
	      alertDialog.show();

	}
	

}
