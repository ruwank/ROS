package com.relianceit.relianceorder.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.relianceit.relianceorder.AppController;
import com.relianceit.relianceorder.db.ROSDbHelper;
import com.relianceit.relianceorder.models.ROSCustomer;
import com.relianceit.relianceorder.models.ROSProduct;
import com.relianceit.relianceorder.models.ROSStock;
import com.relianceit.relianceorder.models.ROSUser;
import com.relianceit.relianceorder.util.AppURLs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sura on 5/5/15.
 */
public class GeneralServiceHandler {

    public static final String TAG = GeneralServiceHandler.class.getSimpleName();

    private boolean customerListDownloaded = false;
    private boolean stockListDownloaded = false;
    private boolean productListDownloaded = false;

    private Context context = null;

    public GeneralServiceHandler(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void cancelRequests(final String requestTag) {
        AppController.getInstance().cancelPendingRequests(requestTag);
    }

    public static interface DailyUpdateListener {
        public abstract void onDailyUpdateSuccess();
        public abstract void onDailyUpdateErrorResponse(VolleyError error);
    }

    public void doDailyContentUpdate(final String requestTag, final DailyUpdateListener listener) {

        if (!customerListDownloaded) {
            downloadCustomerList(requestTag, listener);
        } else if (!stockListDownloaded) {
            downloadStocksList(requestTag, listener);
        } else if (!productListDownloaded) {
            downloadProductsList(requestTag, listener);
        }
        if (customerListDownloaded && stockListDownloaded && productListDownloaded) {
            this.context = null;
            listener.onDailyUpdateSuccess();
        }
    }

    private void customersDownloaded(ArrayList<ROSCustomer> customers, final String requestTag, final DailyUpdateListener listener) {
//        ArrayList<ROSCustomer> customersToDb = new ArrayList<ROSCustomer>();
//        for (int i = 0; i < customers.size(); i++) {
//            ROSCustomer customer = customers.get(i);
//            customer.fillDbFields();
//            customersToDb.add(customer);
//        }

        ROSDbHelper dbHelper = new ROSDbHelper(context);
        dbHelper.clearCustomerTable(context);
        //dbHelper.insertCustomers(context, customersToDb);
        dbHelper.addCustomers(context);

        customerListDownloaded = true;
        doDailyContentUpdate(requestTag, listener);
    }

    private void stocksDownloaded(ArrayList<ROSStock> stocks, final String requestTag, final DailyUpdateListener listener) {
//        ArrayList<ROSStock> stocksToDb = new ArrayList<ROSStock>();
//        for (int i = 0; i < stocks.size(); i++) {
//            ROSStock stock = stocks.get(i);
//            stocksToDb.add(stock);
//        }
//
//        ROSDbHelper dbHelper = new ROSDbHelper(context);
//        dbHelper.clearStockTable(context);
//        dbHelper.insertStocks(context, stocksToDb);

        ROSDbHelper dbHelper = new ROSDbHelper(context);
        dbHelper.clearStockTable(context);
        dbHelper.addStocks(context);

        stockListDownloaded = true;
        doDailyContentUpdate(requestTag, listener);
    }

    private void productsDownloaded(ArrayList<ROSProduct> products, final String requestTag, final DailyUpdateListener listener) {

        ROSDbHelper dbHelper = new ROSDbHelper(context);
        dbHelper.clearProductTable(context);
        dbHelper.addProducts(context);

        productListDownloaded = true;
        doDailyContentUpdate(requestTag, listener);
    }

    private void dailyUpdateFailed(final String requestTag, final DailyUpdateListener listener, VolleyError volleyError) {

        ROSDbHelper dbHelper = new ROSDbHelper(context);
        dbHelper.clearCustomerTable(context);
        dbHelper.clearStockTable(context);
        dbHelper.clearProductTable(context);

        this.context = null;
        listener.onDailyUpdateErrorResponse(volleyError);
    }

    private void downloadCustomerList(final String requestTag, final DailyUpdateListener listener) {

        customersDownloaded(null, requestTag, listener);

//        ROSUser user = ROSUser.getInstance();
//        //Authorization: Token <auth token>:<deviceId>
//        final String params = "Token " + user.getAccessToken() + ":" + user.getDeviceToken();
//        Log.d(TAG, "Customer Authorization: " + params);
//
//        JsonArrayRequest customerRequest = new JsonArrayRequest(AppURLs.CUSTOMER_LIST_ENDPOINT, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray jsonArray) {
//                Log.d(TAG, "Customer list success " + jsonArray.toString());
//
//                Type listType = new TypeToken<ArrayList<ROSCustomer>>(){}.getType();
//                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//                ArrayList<ROSCustomer> customers = gson.fromJson(jsonArray.toString(), listType);
//                Log.d(TAG, "Customer list size: " + customers.size());
//
//                customersDownloaded(customers, requestTag, listener);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.d(TAG, "Customer list error " + volleyError.toString());
//                if (volleyError.networkResponse.statusCode == 401) {
//                    Log.d(TAG, "Customer list failed ====== Unauthorized");
//                }else {
//                    Log.d(TAG, "Customer list failed ====== Server error");
//                }
//                dailyUpdateFailed(requestTag, listener, volleyError);
//            }
//        })
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", params);
//                return headers;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(customerRequest, requestTag);
    }

    private void downloadStocksList(final String requestTag, final DailyUpdateListener listener) {

        stocksDownloaded(null, requestTag, listener);

//        ROSUser user = ROSUser.getInstance();
//        //Authorization: Token <auth token>:<deviceId>
//        final String params = "Token " + user.getAccessToken() + ":" + user.getDeviceToken();
//        Log.d(TAG, "Stock Authorization: " + params);
//
//        JsonArrayRequest stockRequest = new JsonArrayRequest(AppURLs.STOCK_LIST_ENDPOINT, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray jsonArray) {
//                Log.d(TAG, "Stock list success " + jsonArray.toString());
//
//                Type listType = new TypeToken<ArrayList<ROSStock>>(){}.getType();
//                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//                ArrayList<ROSStock> stocks = gson.fromJson(jsonArray.toString(), listType);
//                Log.d(TAG, "Stock list size: " + stocks.size());
//
//                for (int i = 0; i < stocks.size(); i++) {
//                    ROSStock stock = stocks.get(i);
//                    stock.print();
//                }
//
//                stocksDownloaded(stocks, requestTag, listener);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.d(TAG, "Stock list error " + volleyError.toString());
//                if (volleyError.networkResponse.statusCode == 401) {
//                    Log.d(TAG, "Stock list failed ====== Unauthorized");
//                }else {
//                    Log.d(TAG, "Stock list failed ====== Server error");
//                }
//                dailyUpdateFailed(requestTag, listener, volleyError);
//            }
//        })
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", params);
//                return headers;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(stockRequest, requestTag);
    }

    private void downloadProductsList(final String requestTag, final DailyUpdateListener listener) {

        productsDownloaded(null, requestTag, listener);


//        ROSUser user = ROSUser.getInstance();
//        //Authorization: Token <auth token>:<deviceId>
//        final String params = "Token " + user.getAccessToken() + ":" + user.getDeviceToken();
//
//        JsonArrayRequest productRequest = new JsonArrayRequest(AppURLs.STOCK_LIST_ENDPOINT, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray jsonArray) {
//                Log.d(TAG, "Products list success " + jsonArray.toString());
//                stockListDownloaded = true;
//                doDailyContentUpdate(requestTag, listener);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.d(TAG, "Products list error " + volleyError.toString());
//                if (volleyError.networkResponse.statusCode == 401) {
//                    Log.d(TAG, "Products list failed ====== Unauthorized");
//                }else {
//                    Log.d(TAG, "Products list failed ====== Server error");
//                }
//                listener.onDailyUpdateErrorResponse(volleyError);
//            }
//        })
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", params);
//                return headers;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(productRequest, requestTag);
    }
}
