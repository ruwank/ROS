package com.relianceit.relianceorder;

import android.app.Application;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.relianceit.relianceorder.db.ROSDbHelper;
import com.relianceit.relianceorder.models.ROSUser;
import com.relianceit.relianceorder.util.AppDataManager;
import com.relianceit.relianceorder.util.Constants;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();
	 
    private RequestQueue mRequestQueue;

    private static AppController mInstance;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        fillUserUsingSavedData();
    }
 
    public static synchronized AppController getInstance() {
        return mInstance;
    }
 
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
 
        return mRequestQueue;
    }
 
//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }
 
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

	public String getDeviceId() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        Log.d(TAG, "Device Id: " + deviceId);
        //return deviceId;
        return "18388499282";
    }

   public void fillUserUsingSavedData() {
       ROSUser user = ROSUser.getInstance();
       user.setUsername(AppDataManager.getData(getApplicationContext(), Constants.DM_USERNAME_KEY));

       String encodedToken = AppDataManager.getData(getApplicationContext(), Constants.DM_ACCESS_TOKEN_KEY);
       if (encodedToken != null) {
           byte[] data = Base64.decode(encodedToken, Base64.DEFAULT);
           try {
               String token = new String(data, "UTF-8");
               user.setAccessToken(token);
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
       }

       user.setDeviceToken(getDeviceId());
   }
}
