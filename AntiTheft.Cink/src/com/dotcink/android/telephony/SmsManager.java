package com.dotcink.android.telephony;

import java.util.List;

import android.content.Intent;
import android.telephony.SmsMessage;

/**
 * Manager of SMS communication.
 * @author dot.cink
 */
public class SmsManager {

	final static String TAG = "SmsManager";

	/**
	 * Parse SMS message from system's SMS broadcast intent.
	 * @Permission RECEIVE_SMS
	 * @param intent The SMS broadcast intent.
	 * @return An SmsMessage or null if failed.
	 */
	public static com.dotcink.android.telephony.SmsMessage parseSmsMessage(Intent intent){
		if(intent == null)
			return null;

		Object[] orig = (Object[])intent.getExtras().get("pdus");
		if(orig == null)
			return null;

		String sender =  SmsMessage.createFromPdu((byte[])(orig)[0]).getOriginatingAddress();

		String messageBody = "";
		for(Object o : orig){
			messageBody += SmsMessage.createFromPdu((byte[])o).getMessageBody();
		}
		
		return new com.dotcink.android.telephony.SmsMessage(sender, messageBody);
	}
	
	/**
	 * Send an SMS message to a recipient.<br>
	 * <b>Note:</b> Now neither sentIntent nor deliveryIntent is set.<br>
	 * @Permission SEND_SMS
	 * @param recipient to whom this message will be sent
	 * @param message the message body
	 */
	public static void sendMessage(String recipient, String message){
		if(recipient == null || message == null)
			return;

		android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
		
		List<String> messages = sms.divideMessage(message);
		
		for (String m : messages){
			sms.sendTextMessage(recipient, null, m, null, null);
//			Log.d(TAG, "sms notification sent.");

//        SmsManager sms = SmsManager.getDefault();
//
//        List<String> messages = sms.divideMessage(contentTextEdit.getText().toString());
//
//        String recipient = recipientTextEdit.getText().toString();
//        for (String message : messages) {
//            sms.sendTextMessage(recipient, null, message, PendingIntent.getBroadcast(
//                    SmsMessagingDemo.this, 0, new Intent(ACTION_SMS_SENT), 0), null);
        }
	}
}