package com.dotcink.android.antitheft;

import com.dotcink.android.antitheft.automode.AutoModeThread;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MainService extends Service {
	AutoModeThread autoModeThread;

	@Override
	public void onCreate() {
		super.onCreate();
		autoModeThread = new AutoModeThread(this);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		autoModeThread.start();
	}

	@Override
	public void onDestroy() {
		autoModeThread.terminate();
		autoModeThread = null;
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
