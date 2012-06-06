package com.dotcink.android.telephony;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Manager of SIM information.
 * @author dot.cink
 */
public class SimManager {
	/**
	 * Get the unique subscriber ID, for example,
	 * the IMSI for a GSM phone or null if it is unavailable.
	 * 
	 * @Permission READ_PHONE_STATE
	 * @param context
	 * @return the unique subscriber ID, for example,
	 * the IMSI for a GSM phone or null if it is unavailable.
	 */
	static public String getSubscriberId(Context context){
		return ((TelephonyManager)context
				.getSystemService(Context.TELEPHONY_SERVICE))
				.getSubscriberId();
	}
	
}
