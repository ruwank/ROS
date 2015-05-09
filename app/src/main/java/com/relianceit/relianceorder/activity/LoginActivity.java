package com.relianceit.relianceorder.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.relianceit.relianceorder.AppController;
import com.relianceit.relianceorder.R;
import com.relianceit.relianceorder.db.ROSDbHelper;
import com.relianceit.relianceorder.models.ROSCustomer;
import com.relianceit.relianceorder.models.ROSStock;
import com.relianceit.relianceorder.models.ROSUser;
import com.relianceit.relianceorder.services.GeneralServiceHandler;
import com.relianceit.relianceorder.util.AppDataManager;
import com.relianceit.relianceorder.util.AppURLs;
import com.relianceit.relianceorder.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ActionBarActivity {
///
    public static final String TAG = LoginActivity.class.getSimpleName();

    private String username = "Reliance";
    private String password = "10";

    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=(Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                loadLoginScreen();

            }

        });
        customizeActionBar();
    }
    private void customizeActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_custom_layout, null);
        TextView textViewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);

        String titleText=getString(R.string.app_name);

        textViewTitle.setText(titleText);

        actionBar.setCustomView(viewActionBar,params);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }
    private  void loadLoginScreen(){
//        Intent intent = new Intent(getApplicationContext(),
//                HomeActivity.class);
//        startActivity(intent);

//        sendLoginRequest();
//        loginCompleted("F240484F-7565-4CC4-BC93-98699791FBE9");

        GeneralServiceHandler generalServiceHandler = new GeneralServiceHandler(this);
        generalServiceHandler.doDailyContentUpdate(TAG, new GeneralServiceHandler.DailyUpdateListener() {
            @Override
            public void onDailyUpdateSuccess() {
                tempShowData();
            }

            @Override
            public void onDailyUpdateErrorResponse(VolleyError error) {

            }
        });
    }

    private void tempShowData() {
        ROSDbHelper dbHelper = new ROSDbHelper(this);

        ArrayList<ROSCustomer> customers = dbHelper.getCustomers(this);
        ArrayList<ROSStock> stocks = dbHelper.getStocks(this);

        for (int i = 0; i < customers.size(); i++) {
            ROSCustomer customer = customers.get(i);
            customer.print();
        }

        for (int i = 0; i < stocks.size(); i++) {
            ROSStock stock = stocks.get(i);
            stock.print();
        }
    }

    private void sendLoginRequest() {
        ROSUser user = ROSUser.getInstance();

        //Authorization: Basic <username>:<password>:<deviceId>
        final String params = "Basic " + username + ":" + password + ":" + "18388499282";

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, AppURLs.LOGIN_ENDPOINT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "Login success " + jsonObject.toString());
                        String token = "";
                        try {
                            token = jsonObject.getString("AuthToken");
                            Log.d(TAG, "Access token " + token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        loginCompleted(token);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //TODO 401 if unauthorized
                        Log.d(TAG, "Login error " + volleyError.toString());
                        if (volleyError.networkResponse.statusCode == 401) {
                            Log.d(TAG, "Login failed ====== Unauthorized");
                        }else {
                            Log.d(TAG, "Login failed ====== Server error");
                        }
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", params);
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(loginRequest, TAG);
        //TODO show indicator
    }

    private void loginCompleted(String accessToken) {
        ROSUser user = ROSUser.getInstance();
        user.setUsername(username);
        user.setPassword(password);
        user.setAccessToken(accessToken);
        user.setDeviceToken("18388499282");

        String encodedToken = accessToken;
        byte[] data = null;
        try {
            data = accessToken.getBytes("UTF-8");
            encodedToken = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AppDataManager.saveData(getApplicationContext(), Constants.DM_ACCESS_TOKEN_KEY, encodedToken);
        AppDataManager.saveData(getApplicationContext(), Constants.DM_USERNAME_KEY, username);
        AppDataManager.saveData(getApplicationContext(), Constants.DM_LOGGED_KEY, "yes");
    }

}
