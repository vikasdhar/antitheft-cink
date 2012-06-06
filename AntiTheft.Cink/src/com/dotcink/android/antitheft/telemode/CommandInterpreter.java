package com.dotcink.android.antitheft.telemode;

import com.dotcink.android.location.Geocoder;
import com.dotcink.android.location.LocationProducer;
import com.dotcink.android.location.UseLocation;
import com.dotcink.android.preference.PreferenceManager;
import com.dotcink.android.telephony.SmsManager;
import com.dotcink.android.telephony.SmsMessage;
import com.dotcink.android.antitheft.R;
import com.dotcink.android.antitheft.protector.GetPictureProtector;
import com.dotcink.android.antitheft.protector.WipeSdcardProtector;
import com.dotcink.android.antitheft.security.DeviceAdmin;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

/**
 * Command interpreter.
 * @author dot.cink
 *
 */
public class CommandInterpreter{

	/**
	 * Interpret an SMS message to do respective actions.
	 * @param context The context where this works.
	 * @param smsMessage SmsMessage to be interpreted.
	 * @return true if success, otherwise false.
	 */
	public static boolean interpretSmsMessage(final Context context, SmsMessage smsMessage){
		String messageBody = smsMessage.getMessageBody();
		final String sender = smsMessage.getSender();
		if (TextUtils.isEmpty(messageBody))
			return false;
		if(!sender.equals(PreferenceManager.getString(context, 
				context.getString(R.string.secure_phone_number_preference_key),
				null, null, 0))){
			SmsManager.sendMessage(sender, context.getString(R.string.its_not_secure_phone_number));
			return false;
		}
		if(messageBody.equalsIgnoreCase(context.getString(R.string.wipe_sdcard_sms_cmd))){
			// Wipe sdcard
			new WipeSdcardProtector(context).protect();
		}else if(messageBody.equalsIgnoreCase(context.getString(R.string.factory_reset_sms_cmd))){
			// factory data reset
			DeviceAdmin.factoryReset(context);
		}else if(messageBody.equalsIgnoreCase(context.getString(R.string.get_location_sms_cmd))){
			// Get location information in sms.
			final LocationProducer locationProducer = new LocationProducer(context);
			locationProducer.startLocationUpdates(new UseLocation() {
				public void useLocation(Location location) {
					String message;
					message = "(" + location.getLongitude() + "," + location.getLatitude() + ")";

					//Get Location Text
					String address = Geocoder.getAddressFromLocation(context, location);
					if(address != null)
						message += "  " + address;

					SmsManager.sendMessage(sender, message);
					locationProducer.stopLocationUpdates();
				}
			}, LocationProducer.GPS_PROVIDER);
		}else if(messageBody.equalsIgnoreCase(context.getString(R.string.get_picture_sms_cmd))){
			String secureMailAddress = 
					PreferenceManager.getString(context, 
							context.getString(R.string.secure_gmail_addr_preference_key), 
							null, null, 0);
			if (TextUtils.isEmpty(secureMailAddress)){
				SmsManager.sendMessage(sender, context.getString(R.string.get_picture_sms_cmd_no_secure_mail_set));
				return false;
			}
			
			new GetPictureProtector(context).protect();
		}
		else{
			SmsManager.sendMessage(smsMessage.getSender(), context.getString(R.string.sms_cmd_list));
		}
		return true;
	}

	/**
	 * Check whether a sms message is a command.
	 * @param context
	 * @param smsMessage
	 * @return
	 */
	public static boolean isCommand(Context context, SmsMessage smsMessage){
		String pre = context.getString(R.string.sms_cmd_prefix);
		String messageBody = smsMessage.getMessageBody();
		if(messageBody!=null && messageBody.length()>=pre.length() &&
				messageBody.substring(0, pre.length()).equalsIgnoreCase(pre))
			return true;
		return false;
	}

}