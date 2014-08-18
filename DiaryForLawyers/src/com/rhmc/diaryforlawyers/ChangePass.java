package com.rhmc.diaryforlawyers;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePass extends Activity {
	Button btnChangePass;
	EditText edtPass;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pass);
		setTitle("Change Password");
		btnChangePass = (Button) findViewById(R.id.btnConfirmChangePassword);
		edtPass = (EditText) findViewById(R.id.edtConfirmPasswordChangePassword);
		btnChangePass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sp = getSharedPreferences("pass_pref", MODE_PRIVATE);
				if(edtPass.getText().toString().equals(sp.getString("pass", ""))){
					startActivity(new Intent(ChangePass.this, SetPassword.class));
					//finish();
				}
				else
					Toast.makeText(ChangePass.this, "Please Enter Previous Password Correct" , Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_pass, menu);
		return true;
	}

}
