package com.dotcink.android.location;

import android.location.Location;

/**
 * Used for processing location updates when it has changed
 * @author dot.cink.
 *
 */
public interface UseLocation {
	abstract public void useLocation(Location location);
}
