package com.dotcink.android.file;

import android.os.Environment;

/**
 * File manager.
 * @author dot.cink
 *
 */
public class File {

	/**
	 * Wipe SDCard's contents.
	 * @Permission WRITE_EXTERNAL_STORAGE
	 * @param context
	 * @return true if successful, otherwise false.
	 */
	public static boolean wipeSdcard(){
		return deleteDir(Environment.getExternalStorageDirectory());
	}

	/**
	 * Delete a directory and its contents
	 * @param dir directory to be deleted.
	 * @param context
	 * @return true if successful, otherwise false.
	 */
	private static boolean deleteDir(java.io.File dir){
		// exclude .android_secure and its contents
		if(".android_secure".equals(dir.getName()))
			return true;

		if(dir.listFiles() != null)
			for (java.io.File child : dir.listFiles()) {
				if(!deleteDir(child)){
					return false;
				}
			}
		// exclude /sdcard
		if(Environment.getExternalStorageDirectory().equals(dir))
			return true;

		return dir.delete();
	}
}