package com.dotcink.android.antitheft.protector;

import com.dotcink.android.antitheft.R;
import com.dotcink.android.file.File;
import com.dotcink.android.log.Log;

import android.content.Context;

/**
 * Protector to wipe sdcard.
 * @author dot.cink
 *
 */
public class WipeSdcardProtector extends Protector {

	public WipeSdcardProtector(Context context) {
		super(context);
	}

	/**
	 * Wipe sdcard.
	 */
	@Override
	public void protect() {
		Log.append(context, R.string.wiping_sdcard, Log.FLAG_TIME);
		if(File.wipeSdcard()){
			Log.append(context, R.string.log_record_success, Log.FLAG_APPEND_NL);
		}else{
			Log.append(context, R.string.log_record_failed, Log.FLAG_APPEND_NL);
		}
		super.protect();
	}
	
}
