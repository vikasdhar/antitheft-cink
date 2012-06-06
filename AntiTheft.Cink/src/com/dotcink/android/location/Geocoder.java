package com.dotcink.android.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Location;

public class Geocoder {

	/**
	 * Get one address string from a location.
	 * @param context
	 * @param location
	 * @return an address parsed from the location, or null if failed.
	 */
	public static String getAddressFromLocation(Context context, Location location){
		try {
			List<Address> addlist = 
					new android.location.Geocoder(context, Locale.getDefault())
			.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
			if(addlist.size() > 0)
				return addlist.get(0).getAddressLine(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
