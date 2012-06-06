package com.dotcink.android.camera;

import android.content.Context;
import android.content.Intent;

public class CameraManager {

	/**
	 * An Intent extra.
	 */
	public static final String EXTRA_NEED_BROADCAST = 
			"dotcink.intent.extra.NEED_BROADCAST";
	public static final String EXTRA_PICTRUE_PATH = 
			"dotcink.intent.extra.PICTURE_PATH";

	public static void takeOnePicture(Context context, String broadcast){
		Intent intent = new Intent(context, CameraActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(EXTRA_NEED_BROADCAST, broadcast);
		context.startActivity(intent);
	}
}