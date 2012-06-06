package com.dotcink.android.location;

import android.location.GpsStatus;

/**
 * Used for processing GPS status when it has changed.
 * @author dot.cink
 *
 */
public interface UseGpsStatus {
	abstract public void useGpsStatus(GpsStatus gpsStatus);
}
