package com.dotcink.android.antitheft.security;

import com.dotcink.android.antitheft.R;
import com.dotcink.android.preference.PreferenceManager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Device admin manager.
 * @author dot.cink
 *
 */
public class DeviceAdmin {

	private static ComponentName getComponentName(Context context){
		return new ComponentName(context, DeviceAdminReceiver.class);
	}
	
	private static DevicePolicyManager getDPM(Context context){
		return (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
	}
	
	/**
	 * Check whether Device Admin has been enabled for this app.
	 * @param context
	 * @return true if enabled, false if not
	 */
	public static boolean hasDeviceAdminEnabled(Context context){
		return getDPM(context).isAdminActive(getComponentName(context));
	}
	
	/**
	 * Enable Device Admin for this app.
	 * @param context
	 */
	public static void enableDeviceAdmin(Context context){
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
        		getComponentName(context));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
        		context.getString(R.string.device_admin_add_explanation));
        context.startActivity(intent);
	}

	/**
	 * Disable Device Admin for this app.
	 * @param context
	 */
	public static void disableDeviceAdmin(Context context){
		getDPM(context).removeActiveAdmin(getComponentName(context));
	}
	
	/**
	 * Factory data reset.
	 * @param context
	 * @return
	 */
	public static boolean factoryReset(Context context){
		if(hasDeviceAdminEnabled(context)){
			getDPM(context).wipeData(0);
			return true;
		}
		return false;
	}

	public static class DeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver{
		
		@Override
		public void onEnabled(Context context, Intent intent) {
			PreferenceManager.putBoolean(context, 
					context.getString(R.string.factory_reset_preference_key), 
					true, null, 0);
			super.onEnabled(context, intent);
		}
		
		@Override
		public void onDisabled(Context context, Intent intent) {
			PreferenceManager.putBoolean(context, 
					context.getString(R.string.factory_reset_preference_key), 
					false, null, 0);
			super.onDisabled(context, intent);
		}
	}
}