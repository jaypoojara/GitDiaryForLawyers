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

public class PasswordMain extends Activity {
	EditText edtPass;
	Button btnLogin,btnChange;
	SharedPreferences sp;
	Editor spedt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_main);
		setTitle("Password");
		sp = getSharedPreferences("password", MODE_PRIVATE);
		String pass = sp.getString("pass", "pass");
		btnLogin = (Button) findViewById(R.id.btnLoginPasswordMain);
		btnChange = (Button) findViewById(R.id.btnChangePasswordPasswordMain);
		btnChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PasswordMain.this, ChangePass.class));
			}
		});
		if(pass == "pass"){
			startActivity(new Intent(PasswordMain.this, SetPassword.class));
			finish();
		}
		edtPass = (EditText) findViewById(R.id.edtPasswordPassword);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pass = sp.getString("pass", "");
				Log.d("pass", pass);
				if(edtPass.getText().toString().equals(pass)){
					startActivity(new Intent(PasswordMain.this, MainActivity.class));
					finish();
				}
				else{
					Toast.makeText(PasswordMain.this, "Password Is Invalid", Toast.LENGTH_SHORT).show();
					edtPass.setText("");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password_main, menu);
		return true;
	}

}
