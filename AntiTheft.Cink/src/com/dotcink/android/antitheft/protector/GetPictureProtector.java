package com.dotcink.android.antitheft.protector;

import com.dotcink.android.antitheft.R;
import com.dotcink.android.camera.CameraManager;
import com.dotcink.android.preference.PreferenceManager;
import com.dotcink.mail.Gmail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class GetPictureProtector extends Protector {
	
	/******************************************/
	
	/**
	 * A broadcast meaning that a picture has been taken.
	 */
	public final static String GET_PICTURE_BROADCAST = 
			"com.dotcink.android.antitheft.protector.GetPictureProtector.PICTURE_TAKEN";
	
	/******************************************/


	public GetPictureProtector(Context context) {
		super(context);
	}
	@Override
	public void protect() {
		CameraManager.takeOnePicture(context, GET_PICTURE_BROADCAST);
		super.protect();
	}
	
	public static class GetPictureReceiver extends BroadcastReceiver{

		String secureMailAddress;
		String gmailAccount;
		String gmailPassword;

		@Override
		public void onReceive(Context context, Intent intent) {
		
			secureMailAddress = 
					PreferenceManager.getString(context, 
							context.getString(R.string.secure_gmail_addr_preference_key), 
							null, null, 0);
			if (TextUtils.isEmpty(secureMailAddress))
				return;

			String picturePath = intent.getExtras().getString(CameraManager.EXTRA_PICTRUE_PATH);
			if(picturePath == null)
				return;
			
			gmailAccount = context.getString(R.string.gmail_account);
			gmailPassword = context.getString(R.string.gmail_password);
			
			Gmail gmail = new Gmail(gmailAccount, gmailPassword);
			String[] toArr = {secureMailAddress};
			gmail.setTo(toArr); 
			gmail.setFrom(gmailAccount);
			gmail.setSubject(context.getString(R.string.picture_mail_subject));
			gmail.setBody(context.getString(R.string.picture_mail_body)); 

			try {
				gmail.addAttachment(picturePath);
				gmail.send();
			} catch(Exception e) {
				e.printStackTrace();
			} 		

		}
	}
}