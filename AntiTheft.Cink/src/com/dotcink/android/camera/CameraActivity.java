package com.dotcink.android.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dotcink.android.antitheft.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;

public class CameraActivity extends Activity {

	public static final int MEDIA_TYPE_IMAGE = 1;

	private static String subDir = "antitheft";

	//	private static Context me = null;
	private Camera mCamera;
	private CameraPreview mPreview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture);

		//		me = this;

		if (! checkCameraHardware(this)) {
//			Log.d(TAG, "No camera found");
			finish();
		}

		// Create an instance of Camera
		mCamera = getCameraInstance();
		if (mCamera == null) {
//			Log.d(TAG, "No camera available!");
			finish();
		}

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		((ViewGroup)findViewById(R.id.capture_preview_area)).addView(mPreview,0);

		if(true){
			// Mute
			AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);

			mCamera.takePicture(new ShutterCallback() {

				public void onShutter() {
					// Unmute
					AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
					mgr.setStreamMute(AudioManager.STREAM_SYSTEM, false);
				}
			}, null, new PictureCallback() {
				public void onPictureTaken(byte[] data, Camera camera) {
					File pictureFile = CameraActivity.getOutputMediaFile
							(CameraActivity.MEDIA_TYPE_IMAGE);
					if (pictureFile == null){
//						Log.d(TAG, "Error creating media file...");
						finish();
					}
					try {
						FileOutputStream fos = new FileOutputStream(pictureFile);
						fos.write(data);
						fos.close();
						
						// send broadcast
						String broadcast = getIntent().getExtras().getString(CameraManager.EXTRA_NEED_BROADCAST);
						if(broadcast != null){
							Intent intent = new Intent();
							intent.putExtra(CameraManager.EXTRA_PICTRUE_PATH, pictureFile.getAbsolutePath());
							intent.setAction(broadcast);
							sendBroadcast(intent);
						}
					} catch (FileNotFoundException e) {
//						Log.d(TAG, "File not found: " + e.getMessage());
					} catch (IOException e) {
//						Log.d(TAG, "Error accessing file: " + e.getMessage());
					}
					finish();
				}
			});
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		releaseCamera();			// release the camera immediately on pause event
	}

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = Camera.open();	// attempt to get a Camera instance
		}
		catch (Exception e) {
			// Camera is not available (in use or dose not exist)
		}
		return c;	//returns null if camera is unavailable
	}

	private void releaseCamera(){
		if (mCamera != null) {
			mCamera.release();		// release the camera for other applications
			mCamera = null;
		}
	}

	/** Create a file Uri for saving an image or video */
	//	private static Uri getOutputMediaFileUri(int type){
	//		return Uri.fromFile(getOutputMediaFile(type));
	//	}
	/** Create a File for saving an image or video */
	public static File getOutputMediaFile(int type){
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStrorageState() before doing this.
		if (! Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			//    		Toast.makeText(me, "SD card is not mounted or read-only.", Toast.LENGTH_LONG).show();// "me" is ok???????????????????????????
//			Log.d(null, "SD card is not mounted or read-only.");
			return null;
		}

		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), subDir );
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
			if(! mediaStorageDir.mkdirs()){
//				Log.d(null, "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_" + timeStamp + ".jpg");
			//		} else if (type == MEDIA_TYPE_VIDEO) {
			//			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
			//					"VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}
}
