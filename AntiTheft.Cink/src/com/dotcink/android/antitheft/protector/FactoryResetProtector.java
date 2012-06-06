package com.dotcink.android.antitheft.protector;

import com.dotcink.android.antitheft.R;
import com.dotcink.android.antitheft.security.DeviceAdmin;
import com.dotcink.android.log.Log;

import android.content.Context;

public class FactoryResetProtector extends Protector {

	public FactoryResetProtector(Context context) {
		super(context);
	}
	
	@Override
	public void protect() {
		Log.append(context, R.string.factory_resetting, Log.FLAG_TIME);
		if(DeviceAdmin.factoryReset(context)){
			Log.append(context, R.string.log_record_success, Log.FLAG_APPEND_NL);
		}else{
			Log.append(context, R.string.log_record_failed, Log.FLAG_APPEND_NL);
		}
		super.protect();
	}

}
