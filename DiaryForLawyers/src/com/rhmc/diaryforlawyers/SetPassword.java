package com.rhmc.diaryforlawyers;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPassword extends Activity {
	Button btnPass;
	EditText edtPass;
	SharedPreferences sp;
	SharedPreferences.Editor edt;
	//long time_millis;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_password);
		setTitle("Set Password");
		btnPass = (Button) findViewById(R.id.btnPinNumSetpass);
		//btnPass.setClickable(false);
		edtPass = (EditText) findViewById(R.id.edtPassSetPass);
		sp = getSharedPreferences("password", MODE_PRIVATE);
		edt = sp.edit();
		btnPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edtPass.getText().toString().length() >= 4){
					Log.d("pass", edtPass.getText().toString());
					edt.putString("pass", edtPass.getText().toString());
					edt.commit();
					startActivity(new Intent(SetPassword.this, PasswordMain.class));
				}
				else
					Toast.makeText(SetPassword.this, "Password lenght should be grater than 4..!!", Toast.LENGTH_LONG).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_password, menu);
		return true;
	}

}
