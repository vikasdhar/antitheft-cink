package com.dotcink.android.antitheft.protector;

import com.dotcink.android.log.Log;
import com.dotcink.android.preference.PreferenceManager;
import com.dotcink.android.telephony.SmsManager;
import com.dotcink.android.antitheft.R;

import android.content.Context;
import android.text.TextUtils;

/**
 * Protector to send an SMS message of notification that this phone may 
 * be stolen to Secure Phone. 
 * @author dot.cink
 *
 */
public class SmsNotificationProtector extends Protector {

	public SmsNotificationProtector(Context context) {
		super(context);
	}

	@Override
	/**
	 * Send an SMS message of notification that this phone may 
	 * be stolen to Secure Phone. 
	 */
	public void protect() {
		String recipient = 
				PreferenceManager.getString(context, 
				context.getString(R.string.secure_phone_number_preference_key), 
				null, null, 0);
		String message = context.getString(R.string.sms_notification_content);
		if (!TextUtils.isEmpty(recipient) && !TextUtils.isEmpty(message)){
			SmsManager.sendMessage(recipient, message);
			Log.append(context, R.string.sending_sms_notification, Log.FLAG_TIME|Log.FLAG_APPEND_NL);
		}
		super.protect();
	}

}