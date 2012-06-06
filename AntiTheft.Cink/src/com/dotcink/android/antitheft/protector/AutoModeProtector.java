package com.dotcink.android.antitheft.protector;

import android.content.Context;

import com.dotcink.android.antitheft.FunctionManager;

public class AutoModeProtector extends Protector{
	
	private Context context;
	
	private SmsNotificationProtector smsNotificationProtector;
	private WipeSdcardProtector wipeSdcardProtector;
	private FactoryResetProtector factoryResetProtector;
	
	public AutoModeProtector(Context context){
		super(context);
		this.context = context;
	}
	
	@Override
	public void protect() {
		if(FunctionManager.hasEnabled(context, FunctionManager.SMS_NOTIFICATION_PROTECTOR)){
			if(smsNotificationProtector == null)
				smsNotificationProtector = new SmsNotificationProtector(context);
			if(!smsNotificationProtector.hasProtected())
				smsNotificationProtector.protect();
		}
		if(FunctionManager.hasEnabled(context, FunctionManager.WIPE_SDCARD_PROTECTOR)){
			if(wipeSdcardProtector == null)
				wipeSdcardProtector = new WipeSdcardProtector(context);
			if(!wipeSdcardProtector.hasProtected())
				wipeSdcardProtector.protect();
		}
		if(FunctionManager.hasEnabled(context, FunctionManager.FACTORY_RESET_PROTECTOR)){
			if(factoryResetProtector == null)
				factoryResetProtector = new FactoryResetProtector(context);
			if(!factoryResetProtector.hasProtected())
				factoryResetProtector.protect();
		}
		super.protect();
	}
	
}