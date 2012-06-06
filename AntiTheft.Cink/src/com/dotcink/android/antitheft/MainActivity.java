package com.dotcink.android.antitheft;

import com.dotcink.android.antitheft.automode.AutoModePreferenceActivity;
import com.dotcink.android.antitheft.security.AuthActivity;
import com.dotcink.android.antitheft.telemode.TeleModePreferenceActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private MainActivity me = null;
	
	// views
	private ToggleButton toggleAutoModeButton;
	private ToggleButton toggleTeleModeButton;
	private Button autoModeButton;
	private Button teleModeButton;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        me = this;
        

		Intent intent = new Intent(this, AuthActivity.class);
        intent.setAction(AuthActivity.ACTION_VERIFY_PASSWORD);
        startActivityForResult(intent, AuthActivity.REQUEST_VERIFY_PASSWORD);
       
        // set default preferences
        PreferenceManager.setDefaultValues(this, R.xml.auto_mode, false);

        //get views
        autoModeButton = (Button)findViewById(R.id.auto_mode);
        teleModeButton = (Button)findViewById(R.id.tele_mode);
    	toggleAutoModeButton = (ToggleButton)findViewById(R.id.toggle_auto_mode);
    	toggleTeleModeButton = (ToggleButton)findViewById(R.id.toggle_tele_mode);

        setAllOnClickListener();
    }
	
	@Override
	protected void onResume() {
		makeDependency();

        super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.general_settings:
			Intent intent = new Intent(me, MainPreferenceActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == AuthActivity.REQUEST_VERIFY_PASSWORD){
			if(resultCode != AuthActivity.RESULT_SUCCESS){
				finish();
			}
		}
	}
	/**
	 * Check toggle buttons.
	 */
	private void makeDependency(){
		toggleAutoModeButton.setChecked(FunctionManager.hasEnabled(me, FunctionManager.AUTO_MODE));
		toggleTeleModeButton.setChecked(FunctionManager.hasEnabled(me, FunctionManager.TELE_MODE));
	}
	/**
	 * Set all onClickListner of all this activity's views.
	 */
	void setAllOnClickListener(){
		autoModeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(me,
						AutoModePreferenceActivity.class);
				startActivity(intent);
			}
		});
		teleModeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(me, 
						TeleModePreferenceActivity.class);
				startActivity(intent);
			}
		});
		toggleAutoModeButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked && !FunctionManager.hasEnabled(me, FunctionManager.AUTO_MODE)){
					FunctionManager.enable(me, FunctionManager.AUTO_MODE);
				}else if(!isChecked && FunctionManager.hasEnabled(me, FunctionManager.AUTO_MODE)){
					FunctionManager.disable(me, FunctionManager.AUTO_MODE);
				}
			}
		});
		toggleTeleModeButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					FunctionManager.enable(me, FunctionManager.TELE_MODE);
				}else{
					FunctionManager.disable(me, FunctionManager.TELE_MODE);
				}
			}
		});
	}
}