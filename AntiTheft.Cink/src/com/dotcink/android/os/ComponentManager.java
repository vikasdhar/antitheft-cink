package com.dotcink.android.os;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.text.TextUtils;

/**
 * Component manager for Service.
 * @author dot.cink
 *
 */
public class ComponentManager {
	
	/**
	 * Check whether a service is running or not.
	 * @param context
	 * @param serviceCanonicalName
	 * @return true if running, otherwise false.
	 */
	public static  boolean isServiceRunning(Context context, String serviceCanonicalName) {
		if(TextUtils.isEmpty(serviceCanonicalName))
			return false;

		ActivityManager manager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
	    for (RunningServiceInfo runningServiceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceCanonicalName.equals(runningServiceInfo.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}

}
