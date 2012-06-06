package com.dotcink.android.log;

import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;

/**
 * Log utilities.
 * @author dot.cink
 *
 */
public class Log {
	
	public static final int FLAG_TIME = 0x00000001;
	public static final int FLAG_APPEND_NL = 0x00000002;

	/**
	 * Append this log record to log database or file.
	 * @param context
	 * @param record a log record.
	 * @param flags flags that controls the format of this log record.
	 */
	public static void append(Context context, String record, int flags){
		String result = "";

		if(match(flags, FLAG_TIME)){
			result += DateFormat.format("yyyy-MM-dd kk:mm:ss", new Date()) +  " ";
		}
		
		result += record;
		
		if(match(flags, FLAG_APPEND_NL)){
			result += "\n";
		}
		
		android.util.Log.d("Log", result);
	}

	/**
	 * Append a log record and its time to log database or file.
	 * @param context
	 * @param resId resource id of a log record.
	 * @param flag flag that controls the format of this log record.
	 */
	public static void append(Context context, int resId, int flag){
		append(context, context.getString(resId), flag);
	}
	
	/**
	 * checks whether $flags matches $flag.
	 * @param flags flag composed of many flags with many meanings.
	 * @param flag a flag with only one meaning
	 * @return true if matched, otherwise false.
	 */
	private static boolean match(int flags, int flag){
		return (flags & flag) != 0;
	}

}