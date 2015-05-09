package com.relianceit.relianceorder.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

	private Context _context;

	public ConnectionDetector(Context context) {
		this._context = context;
	}

	/**
	 * Checking for all possible internet providers
	 * **/
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public NetworkType getNetworkType() {
		NetworkType type = NetworkType.TYPE_NONE;
		ConnectivityManager manager = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			int nType = info.getType();
			if (nType == ConnectivityManager.TYPE_MOBILE)
				type = NetworkType.TYPE_MOBILE;
			else if (nType == ConnectivityManager.TYPE_WIFI)
				type = NetworkType.TYPE_WI_FI;
		}
		return type;
	}

	public enum NetworkType {
		TYPE_NONE, TYPE_MOBILE, TYPE_WI_FI
	}
}
