package com.dotcink.android.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manager of preferences to retrieve and modify the preference values.
 * @author dot.cink
 */
public class PreferenceManager {

	/**
	 * @see Context.MODE_PRIVATE
	 */
	public static final int MODE_PRIVATE = Context.MODE_PRIVATE;
	/**
	 * @see Context.MODE_WORLD_READABLE
	 */
	public static final int MODE_WORLD_READABLE = 
			Context.MODE_WORLD_READABLE;
	/**
	 * @see Context.MODE_WORLD_WRITEABLE
	 */
	public static final int MODE_WORLD_WRITEABLE = 
			Context.MODE_WORLD_WRITEABLE;

	private static SharedPreferences defaultSharedPreferences;

	/**
	 * Retrieve a boolean value from the preferences.
	 * @param context The context where this works.
	 * @param key The name of the preference to retrieve.
	 * @param defValue Value to return if this preference does not exist.
	 * @param name Desired preferences file (Note: null means the DEFAULT
	 * preferences file).
	 * @param mode Operating mode. Use 0 or MODE_PRIVATE for the
	 * default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to
	 * control permissions. (Arbitrary if "mode" is null)
	 * @return the preference value if it exists, or defValue.
	 */
	public static boolean getBoolean(Context context, String key, 
			boolean defValue, String name, int mode){
		return getSharedPreferences(context, name, mode)
				.getBoolean(key, defValue);
	}
	/**
	 * Retrieve a String value from the preferences.
	 * @param context The context where this works.
	 * @param key The name of the preference to retrieve.
	 * @param defValue Value to return if this preference does not exist.
	 * @param name Desired preferences file (Note: null means the DEFAULT
	 * preferences file).
	 * @param mode Operating mode. Use 0 or MODE_PRIVATE for the
	 * default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to
	 * control permissions.  (Arbitrary if "mode" is null)
	 * @return the preference value if it exists, or defValue.
	 */
	public static String getString(Context context, String key, 
			String defValue, String name, int mode){
		return getSharedPreferences(context, name, mode)
				.getString(key, defValue);
	}
	/**
	 * Set a boolean value in the preferences.
	 * @param context The context where this works.
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @param name Desired preferences file (Note: null means the DEFAULT
	 * preferences file).
	 * @param mode Operating mode. Use 0 or MODE_PRIVATE for the
	 * default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to
	 * control permissions.  (Arbitrary if "mode" is null)
	 * @return true if success, otherwise false.
	 */
	public static boolean putBoolean(Context context, String key, 
			boolean value, String name, int mode){
		return getSharedPreferences(context, name, mode).edit()
				.putBoolean(key, value).commit();
	}
	/**
	 * Set a String value in the preferences.
	 * @param context The context where this works.
	 * @param key The name of the preference to modify.
	 * @param value The new value for the preference.
	 * @param name Desired preferences file (Note: null means the DEFAULT
	 * preferences file).
	 * @param mode Operating mode. Use 0 or MODE_PRIVATE for the
	 * default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to
	 * control permissions.  (Arbitrary if "mode" is null)
	 * @return true if success, otherwise false.
	 */
	public static boolean putString(Context context, 
			String key, String value, String name, int mode){
		return getSharedPreferences(context, name, mode).edit()
				.putString(key, value).commit();
	}
	/**
	 * Checks whether the preferences contains a preference.
	 * @param context The context where this works.
	 * @param key The name of the preference to check.
	 * @param name Desired preferences file (Note: null means the DEFAULT
	 * preferences file).
	 * @param mode Operating mode. Use 0 or MODE_PRIVATE for the
	 * default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to
	 * control permissions.  (Arbitrary if "mode" is null)
	 * @return true if the preference exists in the preferences, otherwise
	 false.
	 */
	public static boolean contains(Context context, String key,
			String name, int mode){
		return getSharedPreferences(context, name, mode).contains(key);
	}
	
	/**
	 * Get the preferences instance.
	 * @param context The context where this works.
	 * @param name Desired preferences file (Note: null means the DEFAULT
	 * preferences file).
	 * @param mode Operating mode. Use 0 or MODE_PRIVATE for the
	 * default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to
	 * control permissions.  (Arbitrary if "mode" is null)
	 * @return the single SharedPreferences instance that can be used to 
	 * retrieve and modify the preference values.
	 */
	private static SharedPreferences getSharedPreferences(Context context,
			String name, int mode){
		if(name==null){
			if(defaultSharedPreferences != null)
				return defaultSharedPreferences;
			return defaultSharedPreferences = 
					android.preference.
					PreferenceManager.getDefaultSharedPreferences(context);
		}
		return context.getSharedPreferences(name, mode);
	}
}