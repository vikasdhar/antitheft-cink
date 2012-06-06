package com.dotcink.android.antitheft.protector;

import android.content.Context;

/**
 * A protector that protects something.
 * @author dot.cink
 *
 */
public abstract class Protector {

	protected Context context;
	protected boolean hasProtected;
	
	public Protector(Context context) {
		this.context = context;
		hasProtected = false;
	}

	/**
	 * Perform its duties.
	 */
	public void protect(){
		hasProtected = true;
	}
	
	/**
	 * Check whether this protector has been performed.
	 * @return true if performed, otherwise false.
	 */
	public boolean hasProtected(){
		return hasProtected;
	}
}