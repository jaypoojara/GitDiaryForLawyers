package com.rhmc.diaryforlawyers;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MyAlarmService extends Service{
	MediaPlayer mMediaPlayer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//Toast.makeText(MyAlarmService.this, "MyAlarmService Started :)", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.logo48, "Todays Hearing List", System.currentTimeMillis());
		Intent i = new Intent(MyAlarmService.this, ViewTodayRecordList.class);
		PendingIntent pi = PendingIntent.getActivity(MyAlarmService.this, 0, i, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		n.setLatestEventInfo(MyAlarmService.this, "Todays Record", sdf.format(new Date()), pi);
		nm.notify(0,n);
//		AlertDialog.Builder builder = new AlertDialog.Builder(MyAlarmService.this);
//		builder.setTitle("Remember:Every Case is defensible...!!!");
//		builder.setCancelable(false);
//		
//		final Intent alertIntent = new Intent();
//	    alertIntent.setClass( this , ViewTodayRecordList.class );
//	    alertIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	    
//		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//		mMediaPlayer = new MediaPlayer();
//	      mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//	      try {
//			mMediaPlayer.setDataSource(MyAlarmService.this, uri);
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	      try {
//			mMediaPlayer.prepare();
//		} catch (IllegalStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	      mMediaPlayer.setLooping(true);
//	      mMediaPlayer.start();
//	      builder.setPositiveButton("OK", new OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				mMediaPlayer.stop();
//				//startActivity(new Intent(MyAlarmService.this,ViewTodayRecordList.class));
//				startActivity( alertIntent );
//				dialog.dismiss();
//			}
//		});
//	      AlertDialog alert = builder.create();
//	      alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//	      alert.show();
		Log.d("Service", "SErvice Called");
		Intent alertIntent = new Intent();
		alertIntent.setClass( this , MyAlarm.class );
	    alertIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity( alertIntent );
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
