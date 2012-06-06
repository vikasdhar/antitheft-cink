package com.dotcink.android.antitheft.inspector;

import com.dotcink.android.antitheft.FunctionManager;

import android.content.Context;

/**
 * An Inspector to check device's states when in AUTO MODE.
 * <p>It employs all other Inspector's.
 * @author dot.cink
 */
public class AutoModeInspector extends Inspector{

	SimInspector simInspector;
	
	public AutoModeInspector(Context context) {
		super(context);
	}

	/**
	 * First use FunctionInspector to check whether an inspector should be
	 * enabled, if yes call it to do its work.
	 * @return Once one inspector fails, return BAD. 
	 */
	@Override
	public int inspectForMore() {
		if(FunctionManager.hasEnabled(context, FunctionManager.SIM_INSPECTOR)){
			if(simInspector == null)
				simInspector = new SimInspector(context);
			if(simInspector.inspectForMore() == BAD)
				return BAD;
		}
		return NORMAL;
	}
}