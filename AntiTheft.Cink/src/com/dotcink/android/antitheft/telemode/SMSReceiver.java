package com.dotcink.android.antitheft.telemode;

import com.dotcink.android.telephony.SmsManager;
import com.dotcink.android.telephony.SmsMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		abortBroadcast();

		final SmsMessage smsMessage = SmsManager.parseSmsMessage(intent);
		if (CommandInterpreter.isCommand(context, smsMessage)){
			new Thread() {
				public void run() {
					CommandInterpreter.interpretSmsMessage(context, smsMessage);
				}
			}.start();
		}else{
			clearAbortBroadcast();
		}
	}
}