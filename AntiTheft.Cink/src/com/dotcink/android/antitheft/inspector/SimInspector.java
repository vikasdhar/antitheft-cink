package com.dotcink.android.antitheft.inspector;

import android.content.Context;
import android.text.TextUtils;

import com.dotcink.android.log.Log;
import com.dotcink.android.preference.PreferenceManager;
import com.dotcink.android.telephony.SimManager;
import com.dotcink.android.antitheft.R;

/**
 * Check whether SIM have been changed abnormally.
 * @author dot.cink
 *
 */
public class SimInspector extends Inspector {

	/**
	 * Inspect result flag.
	 * @means There is no SIM registered.
	 */
	public static final int NO_SIM_REGISTERED = 1;

	public SimInspector(Context context){
		super(context);
	}

	/**
	 * Checks whether non-registered SIM is being used.
	 */
	@Override
	public int inspectForMore() {
		Log.append(context, R.string.checking_sim, Log.FLAG_TIME);
		String subscriberId = PreferenceManager
				.getString(context,
						context.getString(R.string.registered_sims_preference_key),
						null, null, 0);
		if(TextUtils.isEmpty(subscriberId)){
			Log.append(context, R.string.checking_sim_none, Log.FLAG_APPEND_NL);
			return NO_SIM_REGISTERED;
		}else if (subscriberId.equals(SimManager.getSubscriberId(context))){
			Log.append(context, subscriberId + SimManager.getSubscriberId(context), 0);
			Log.append(context, R.string.log_record_success, Log.FLAG_APPEND_NL);
			return NORMAL;
		}else {
			Log.append(context, R.string.log_record_failed, Log.FLAG_APPEND_NL);
			return BAD;
		}
	}
}