package com.dotcink.android.antitheft.inspector;

import android.content.Context;

/**
 * An inspector that checks something to find some problem.
 * @author dot.cink
 */
public abstract class Inspector {

	/**
	 * Inspect result flag.
	 * @means Everything is OK.
	 */
	public static final int NORMAL = 0;
	/**
	 * Inspect result flag.
	 * @means Something is wrong.
	 */
	public static final int BAD = -1;

	protected Context context;
	
	public Inspector(Context context) {
		this.context = context;
	}
	
	/**
	 * Begin to inspect.
	 * @return true if "Everything is OK", otherwise false.
	 */
	public boolean inspect(){
		return inspectForMore() == NORMAL;
	}
	
	/**
	 * Begin to inspect.
	 * @return the inspect result flag.
	 */
	abstract public int inspectForMore();
}
