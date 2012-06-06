package com.dotcink.android.antitheft;

import com.dotcink.android.preference.PreferenceManager;
import com.dotcink.android.antitheft.R;
import com.dotcink.android.antitheft.telemode.SMSReceiver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Function manager.<br>
 * Set or get states (enabled or disabled) of functions.
 * @author dot.cink
 */
public class FunctionManager{

	/**
	 * Inspect result flag.
	 * @means There is no such function.
	 */
	public static final int NO_SUCH_FUNCTION = 500;

	//Inspectors 
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means auto startup after boot up completely.
	 */
	public static final int AUTO_STARTUP = 501;
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means auto mode
	 */
	public static final int AUTO_MODE = 600;
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means tele mode
	 */
	public static final int TELE_MODE = 700;
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means sms control
	 */
	public static final int SMS_CONTROL = 701;
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means SimInspector
	 */
	public static final int SIM_INSPECTOR = 601;

	//Protectors
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means SmsNotificationProtector
	 */
	public static final int SMS_NOTIFICATION_PROTECTOR = 651;
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means WipeSdcardProtector
	 */
	public static final int WIPE_SDCARD_PROTECTOR = 652;
	/**
	 * Flag used by inspect(int which) to check whether this is enabled.
	 * @means FactoryResetProtector
	 */
	public static final int FACTORY_RESET_PROTECTOR = 1005;


	/**
	 * Checks whether a function is enabled.
	 * @param which A flag representing a function.
	 * @return true if this function has been enabled, otherwise false
	 */
	public static boolean hasEnabled(Context context, int which) {
		switch (which) {
		case AUTO_STARTUP:
			return get(context, R.string.auto_startup_preference_key);
		case AUTO_MODE:
			return get(context, R.string.auto_mode_preference_key);
		case TELE_MODE:
			return get(context, R.string.tele_mode_preference_key);
		case SIM_INSPECTOR:
			return get(context, R.string.check_sim_preference_key);
		case SMS_NOTIFICATION_PROTECTOR:
			return get(context, R.string.sms_notification_key);
		case WIPE_SDCARD_PROTECTOR:
			return get(context, R.string.wipe_sdcard_preference_key);
		case FACTORY_RESET_PROTECTOR:
			return get(context, R.string.factory_reset_preference_key);
		case SMS_CONTROL:
			return get(context, R.string.sms_control_preference_key);
		default:
			return false;
		}
	}

	/**
	 * Enable a function.
	 * @param context
	 * @param which A flag representing a function.
	 */
	public static void enable(Context context, int which){
		set(context, which, true);
		switch (which) {
		case AUTO_STARTUP:
			context.getPackageManager().setComponentEnabledSetting(
					new ComponentName(context, StartupReceiver.class), 
							PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
							PackageManager.DONT_KILL_APP);
			break;
		case AUTO_MODE:
			Intent service = new Intent(context, MainService.class);
			context.startService(service);
			break;
		case TELE_MODE:
			if(hasEnabled(context, SMS_CONTROL)){
				context.getPackageManager().setComponentEnabledSetting(
						new ComponentName(context, SMSReceiver.class),
						PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 
						PackageManager.DONT_KILL_APP);
			}
			break;
		case SMS_CONTROL:
			if(hasEnabled(context, TELE_MODE)){
				context.getPackageManager().setComponentEnabledSetting(
						new ComponentName(context, SMSReceiver.class),
						PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 
						PackageManager.DONT_KILL_APP);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Disable a function.
	 * @param context
	 * @param which A flag representing a function.
	 */
	public static void disable(Context context, int which){
		set(context, which, false);
		switch (which) {
		case AUTO_STARTUP:
			context.getPackageManager().setComponentEnabledSetting(
					new ComponentName(context, StartupReceiver.class), 
							PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
							PackageManager.DONT_KILL_APP);
			break;
		case AUTO_MODE:
			Intent service = new Intent(context, MainService.class);
			context.stopService(service);
			break;
		case TELE_MODE:
			// disable all tele mode functions
			context.getPackageManager().setComponentEnabledSetting(
					new ComponentName(context, SMSReceiver.class),
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
			break;
		case SMS_CONTROL:
			context.getPackageManager().setComponentEnabledSetting(
					new ComponentName(context, SMSReceiver.class),
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
		default:
			break;
		}
	}

	/**
	 * Enable or disable a function.
	 * @param context
	 * @param which A flag representing a function.
	 * @param value true if enable it, false if disable it.
	 */
	private static void set(Context context, int which, boolean value){
		switch (which) {
		case AUTO_STARTUP:
			put(context, R.string.auto_startup_preference_key, value);
			break;
		case AUTO_MODE:
			put(context, R.string.auto_mode_preference_key, value);
			break;
		case TELE_MODE:
			put(context, R.string.tele_mode_preference_key, value);
			break;
		case SIM_INSPECTOR:
			put(context, R.string.check_sim_preference_key, value);
			break;
		case SMS_NOTIFICATION_PROTECTOR:
			put(context, R.string.sms_notification_key, value);
			break;
		case WIPE_SDCARD_PROTECTOR:
			put(context, R.string.wipe_sdcard_preference_key, value);
			break;
		case FACTORY_RESET_PROTECTOR:
			put(context, R.string.factory_reset_preference_key, value);
			break;
		case SMS_CONTROL:
			put(context, R.string.sms_control_preference_key, value);
			break;
		default:
			;
		}
	}

	/**
	 * Get the value of CheckPreference's from default shared preferences file.
	 * @param resId The resource id of the key of the CheckPreference.
	 * @return true if enabled, otherwise false (default value).
	 */
	private static boolean get(Context context, int resId){
		return PreferenceManager.getBoolean(context, context.getString(resId), false, null, 0);
	}

	/**
	 * Put the value of CheckPreference's to default shared preferences file.
	 * @param context
	 * @param resId The resource id of the key of the CheckPreference.
	 * @param value the value to be changed to.
	 */
	private static void put(Context context, int resId, boolean value){
		PreferenceManager.putBoolean(context, context.getString(resId), value, null, 0);
	}
}