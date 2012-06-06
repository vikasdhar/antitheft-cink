package com.dotcink.android.location;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationProducer implements LocationListener, GpsStatus.Listener {

	/**
	 * *********** STATIC FINAL ***********
	/**
	 * @Permission ACCESS_FINE_LOCATION
	 * @see android.location.LocationManager.GPS_PROVIDER
	 */
	static final public String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
	/**
	 * @Permission ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION
	 * @see android.location.LocationManager.NETWORK_PROVIDER
	 */
	static final public String NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER;

	/**
	 * *********** LOCAL ***********
	 */
	// request parameters
	private int timeInterval;
	private int distanceInterval;

	private String locationProvider;

	private UseLocation useLocation;
	private UseGpsStatus useGpsStatus;

	private LocationManager locationManager;

	public LocationProducer(Context context){
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		timeInterval = 60000;
		distanceInterval = 0;
	}

	public void onLocationChanged(Location location) {
		if (useLocation != null)
			useLocation.useLocation(location);
	}
	public void onProviderDisabled(String provider) {

	}
	public void onProviderEnabled(String provider) {

	}
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
	public void onGpsStatusChanged(int event) {
		if(useGpsStatus != null && locationManager != null)
			useGpsStatus.useGpsStatus(locationManager.getGpsStatus(null));
	}

	/**
	 * Begin to listen location updates.
	 * @param useLocation interface to processing location updates.
	 * @param locationProvider GPS_PROVIDER or NETWORK_PROVIDER, and null means NETWORK_PROVIDER
	 * @param intervals time interval and distance interval(optional); default is (60000,0)
	 * @return true if success, otherwise false.
	 */
	public boolean startLocationUpdates(UseLocation useLocation, String locationProvider, int ...intervals){

		if (locationManager == null){
			return false;
		}

		this.useLocation = useLocation;

		if (locationProvider != GPS_PROVIDER && locationProvider != NETWORK_PROVIDER){
			return false;
		}else{
			this.locationProvider = locationProvider;
		}

		if (intervals.length > 0){
			timeInterval = intervals[0];
		}
		if (intervals.length > 1){
			distanceInterval = intervals[1];
		}

		locationManager.requestLocationUpdates(this.locationProvider, timeInterval, distanceInterval, this);

		return true;
	}

	/**
	 * Begin to listen to GPS status updates.
	 * @param useGpsStatus interface to process GPS status updates.
	 * @return true if success, otherwise false.
	 */
	public boolean startGpsUpdates(UseGpsStatus useGpsStatus){

		if (locationManager == null){
			return false;
		}

		this.useGpsStatus = useGpsStatus;

		locationManager.addGpsStatusListener(this);

		return true;
	}

	/**
	 * Stop listening location updates.
	 */
	public void stopLocationUpdates(){
		if(locationManager == null)
			return;
		locationManager.removeGpsStatusListener(this);
	}

	/**
	 * Stop listening GPS updates.
	 */
	public void stopGpsUpdates(){
		if(locationManager == null)
			return;
		locationManager.removeUpdates(this);
	}
}