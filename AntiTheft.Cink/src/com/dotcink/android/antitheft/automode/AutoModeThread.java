package com.dotcink.android.antitheft.automode;

import com.dotcink.android.antitheft.FunctionManager;
import com.dotcink.android.antitheft.R;
import com.dotcink.android.antitheft.inspector.AutoModeInspector;
import com.dotcink.android.antitheft.protector.AutoModeProtector;
import com.dotcink.android.log.Log;

import android.content.Context;

/**
 * Thread that executing auto mode inspecting and protecting.
 * @author dot.cink
 *
 */
public class AutoModeThread extends Thread {
	
	Context context ;

	private long timeInterval = 5000;
	
	private boolean terminate;

	// Inspector's and Protector's of Auto Mode
	private AutoModeInspector autoModeInspector;
	private AutoModeProtector autoModeProtector;
	
	public AutoModeThread(Context context){
		super();
		this.context = context;
		terminate = false;
	}
	@Override
	public void run() {
		super.run();

		// Loop...
		Log.append(context, R.string.starting_auto_mode, Log.FLAG_APPEND_NL|Log.FLAG_TIME);
		while(!terminate){

			// auto mode begins
			if(FunctionManager.hasEnabled(context, FunctionManager.AUTO_MODE)){
				if(terminate)
					break;

				if (autoModeInspector == null)
					autoModeInspector = new AutoModeInspector(context);
				
				if(!autoModeInspector.inspect()){
					
					if(terminate)
						break;
				
					if (autoModeProtector == null)
						autoModeProtector = new AutoModeProtector(context);
					autoModeProtector.protect();
				}
			}
			
			try {
				sleep(timeInterval);
			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
		}
		Log.append(context, R.string.stopping_auto_mode, Log.FLAG_APPEND_NL|Log.FLAG_TIME);
	}
	
	/**
	 * Terminate this thread.
	 */
	public void terminate(){
		terminate = true;
		this.interrupt();
	}
}