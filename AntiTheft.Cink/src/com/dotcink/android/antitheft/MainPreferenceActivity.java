package com.dotcink.android.antitheft;

import com.dotcink.android.log.Log;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class MainPreferenceActivity extends android.preference.PreferenceActivity {

	private Context me;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.general);
		
		me = this;

		findPreference(this.getString(R.string.auto_startup_preference_key))
		.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				MainPreferenceActivity.this.getPackageManager()
				.setComponentEnabledSetting(
						new ComponentName(MainPreferenceActivity.this, 
								StartupReceiver.class),
								"true".equals(newValue.toString())
								? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
										: PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
										PackageManager.DONT_KILL_APP);
				if("true".equals(newValue.toString()))
					Log.append(me, R.string.enabled_auto_startup, Log.FLAG_TIME|Log.FLAG_APPEND_NL);
				else
					Log.append(me, R.string.disabled_auto_startup, Log.FLAG_TIME|Log.FLAG_APPEND_NL);
				return true;
			}
		});
	}
}
