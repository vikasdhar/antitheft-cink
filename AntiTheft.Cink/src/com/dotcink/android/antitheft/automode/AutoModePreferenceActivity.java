package com.dotcink.android.antitheft.automode;

import com.dotcink.android.log.Log;
import com.dotcink.android.preference.PreferenceManager;
import com.dotcink.android.telephony.SimManager;
import com.dotcink.android.antitheft.R;
import com.dotcink.android.antitheft.security.DeviceAdmin;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Preference activity for auto mode
 * @author dot.cink
 *
 */
public class AutoModePreferenceActivity 
extends android.preference.PreferenceActivity {

	private Context me;

	// preference views
	Preference registerSimPreference;
	Preference checkSimPreference;
	Preference smsNotificationPreference;
	Preference factoryResetPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.auto_mode);
		me = this;

		// get preference vies
		registerSimPreference = 
				findPreference(this.getString(R.string.registered_sims_preference_key));
		checkSimPreference = 
				findPreference(this.getString(R.string.check_sim_preference_key));
		smsNotificationPreference = 
				findPreference(this.getString(R.string.sms_notification_key));
		factoryResetPreference = 
				findPreference(this.getString(R.string.factory_reset_preference_key));
		makeDependency();

		// set the OnPreferenceClickListener of "SIM Register"
		registerSimPreference
		.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Log.append(me, R.string.registering_sim, Log.FLAG_TIME);

				String subscriberId = SimManager.getSubscriberId(me);
				if (TextUtils.isEmpty(subscriberId)){
					Toast.makeText(me, me.getString(R.string.can_not_get_sim_info),
							Toast.LENGTH_SHORT).show();
					return false;
				}
				
				if(PreferenceManager.putString(me,
						me.getString(R.string.registered_sims_preference_key),
						subscriberId, null, 0))
				{
					Log.append(me, subscriberId, 0);
					Log.append(me, R.string.log_record_success, Log.FLAG_APPEND_NL);
					Toast.makeText(me, me.getString(R.string.register_sim_success), 
							Toast.LENGTH_SHORT).show();
					makeDependency();
					return true;
				}
				Log.append(me, R.string.log_record_failed, Log.FLAG_APPEND_NL);
				Toast.makeText(me, me.getString(R.string.register_sim_failed),
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		
		// check if secure phone number is set
		smsNotificationPreference
		.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if("true".equals(newValue.toString()) 
						&& TextUtils.isEmpty(
								PreferenceManager.getString(AutoModePreferenceActivity.this,
										AutoModePreferenceActivity.this.getString
										(R.string.secure_phone_number_preference_key), 
										null, null, 0)))
				{
					Toast.makeText(me, 
							me.getString(R.string.no_secure_phone_number_set), 
							Toast.LENGTH_SHORT).show();
					return false;
				}
				return true;
			}
		});

		// enable or disable device-admin
		factoryResetPreference
		.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if("true".equals(newValue.toString())){
					DeviceAdmin.enableDeviceAdmin(me);
				}else{
					DeviceAdmin.disableDeviceAdmin(me);
				}
				return false;
			}
		});
	}

	/**
	 * Make sure that simCheckPreference depended on simRegisterPreference
	 */
	private void makeDependency(){
		if (!TextUtils.isEmpty(PreferenceManager.getString(this, 
				this.getString(R.string.registered_sims_preference_key), 
				null, null, 0))){
			checkSimPreference.setEnabled(true);
		}else {
			PreferenceManager.putBoolean(me, 
					me.getString(R.string.check_sim_preference_key), 
					false, null, 0);

			checkSimPreference.setEnabled(false);
		}
	}
}