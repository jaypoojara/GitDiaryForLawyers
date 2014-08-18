package com.rhmc.diaryforlawyers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Log.d("REC","Receiver called");
		Intent i = new Intent(arg0,MyAlarmService.class);
		arg0.startService(i);
		
	}

}
