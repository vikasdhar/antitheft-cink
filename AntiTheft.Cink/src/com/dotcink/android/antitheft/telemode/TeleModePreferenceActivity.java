package com.dotcink.android.antitheft.telemode;

import com.dotcink.android.antitheft.R;
import com.dotcink.android.log.Log;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class TeleModePreferenceActivity extends android.preference.PreferenceActivity {

	private Context me;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.tele_mode);
		
		me = this;

		// Toggle SMS Control state
		findPreference(this.getString(R.string.sms_control_preference_key))
		.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				TeleModePreferenceActivity.this.getPackageManager().setComponentEnabledSetting(
						new ComponentName(TeleModePreferenceActivity.this, SMSReceiver.class),
						"true".equals(newValue.toString())
						? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
								: PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 
								PackageManager.DONT_KILL_APP);
				if("true".equals(newValue.toString()))
					Log.append(me, R.string.enabled_sms_control, Log.FLAG_TIME|Log.FLAG_APPEND_NL);
				else
					Log.append(me, R.string.disabled_sms_control, Log.FLAG_TIME|Log.FLAG_APPEND_NL);
				return true;
			}
		});
	}
}