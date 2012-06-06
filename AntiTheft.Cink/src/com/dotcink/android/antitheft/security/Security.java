package com.dotcink.android.antitheft.security;

import com.dotcink.android.preference.PreferenceManager;

import android.content.Context;
import android.text.TextUtils;

/**
 * Security manager of this app.
 * @author dot.cink
 *
 */
public class Security {

	/**
	 * The key of app password preference.
	 */
	private static final String appPasswordKey = "AppPassword";

	/**
	 * Check whether app password has been set or not.
	 * @param context The context where this works.
	 * @return true if set, otherwise false.
	 */
	public static boolean hasPasswordSet(Context context){
		return !TextUtils.isEmpty(PreferenceManager.getString(context, appPasswordKey
				, null, null, 0));
	}
	
	/**
	 * Check whether $password is right or wrong.
	 * @param context
	 * @param password Password string to check
	 * @return true if right, otherwise false.
	 */
	public static boolean checkPassword(Context context, String password){
		return com.dotcink.android.security.Security
				.checkMd5sum(password, PreferenceManager.getString(context, appPasswordKey
					, null, null, 0));
	}
	
	/**
	 * Set password.
	 * @param context
	 * @param password password string to be set.
	 */
	public static void setPassword(Context context, String password){
		PreferenceManager.putString(context, appPasswordKey, 
				com.dotcink.android.security.Security.md5sum(password), null, 0);
	}
}