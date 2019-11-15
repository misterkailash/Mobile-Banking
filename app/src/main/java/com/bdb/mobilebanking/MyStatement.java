package com.bdb.mobilebanking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bdb.mobilebanking.adapters.SmtAdapter;
import com.bdb.mobilebanking.models.Trans;
import com.bdb.mobilebanking.models.User;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.bdb.mobilebanking.utils.RestClient;
import com.comix.overwatch.HiveProgressView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyStatement extends AppCompatActivity {

    ErrorHandler handler = new ErrorHandler();

    @BindView(R.id.opening_amount)
    TextView openingAmt;

    @BindView(R.id.balance_progress)
    HiveProgressView progressBar;

    @BindView(R.id.current_balance)
    TextView myBalance;

    @BindView(R.id.history_view)
    RecyclerView recyclerView;

    private List<Trans> transList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_balance);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
        myBalance.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getBalance();
                historyView();
            }
        }, 1000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getBalance() {
        User user = new User();
        SharedPreferences sp = getSharedPreferences("PREF", MODE_PRIVATE);
        user.setUsername(sp.getString("username", null));

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.GET_BALANCE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    if (success.equals("SUCCESS")) {
                        progressBar.setVisibility(View.GONE);
                        myBalance.setVisibility(View.VISIBLE);
                        myBalance.setText(array.getString("balance"));
                    } else {
                        handler.errorDisplay(MyStatement.this, array.getString("error_msg"));
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = handler.onErrorResponse(error);
                handler.errorDisplay(MyStatement.this, message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void historyView() {
        SharedPreferences pf = getSharedPreferences("PREF", MODE_PRIVATE);
        User user = new User();
        user.setUsername(pf.getString("username", null));

        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.RECENT_TRANSACTIONS, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Trans[] trans = new Gson().fromJson(response.getString("data"), Trans[].class);
                    transList = Arrays.asList(trans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SmtAdapter adapter = new SmtAdapter(getBaseContext(), transList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = handler.onErrorResponse(error);
                progressBar.setVisibility(View.GONE);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}