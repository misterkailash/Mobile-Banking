package com.bdb.mobilebanking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bdb.mobilebanking.AppController;
import com.bdb.mobilebanking.R;
import com.bdb.mobilebanking.models.User;
import com.bdb.mobilebanking.utils.CoreUtils;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.bdb.mobilebanking.utils.RestClient;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DepositReview extends Fragment {

    public static String title, name, account, amount, sol;
    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils utils = new CoreUtils();
    SharedPreferences sp;
    @BindView(R.id.dep_statement)
    TextView statement;
    @BindView(R.id.dep_confirm)
    BootstrapButton confirm;
    @BindView(R.id.dep_cancel)
    BootstrapButton cancel;
    @BindView(R.id.dep_progressBar)
    ArchedImageProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.deposit_review, container, false);
        ButterKnife.bind(this, layout);
        progressBar.setVisibility(GONE);

        sp = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE);
        title = sp.getString("title", null);
        name = sp.getString("account_name", null);
        account = sp.getString("account", null);
        amount = String.valueOf(Integer.parseInt(Objects.requireNonNull(sp.getString("amount", null))));
        sol = sp.getString("sol", null);
        statement.setText(getResources().getString(R.string.deposit_statement, String.valueOf(Integer.parseInt(amount)), account, title, name));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setVisibility(GONE);
                cancel.setVisibility(GONE);
                progressBar.setVisibility(View.VISIBLE);
                utils.archedProgress(progressBar, getResources());
                Deposit();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        return layout;
    }

    public void Deposit() {
        User user = new User();
        user.setUsername(sp.getString("username", null));
        user.setAccountNumber(account);
        user.setAmount(amount);
        user.setSol(sol);
        user.setDeviceID(sp.getString("device_id", null));

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.DEPOSIT, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    if (success.equals("SUCCESS")) {
                        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE).edit();
                        editor.putString("account", account);
                        //editor.putString("amount", amount);
                        editor.putString("available_balance", array.getString("available_balance"));
                        editor.putString("reference_no", array.getString("ref_no"));
                        editor.putString("time", array.getString("tran_date"));
                        editor.apply();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                FragmentManager manager = getFragmentManager();
                                Objects.requireNonNull(manager).beginTransaction().replace(R.id.content_main, new DepositResult()).addToBackStack("Deposit").commit();
                            }
                        }, 2000);
                    } else {
                        progressBar.setVisibility(GONE);
                        confirm.setVisibility(VISIBLE);
                        cancel.setVisibility(VISIBLE);
                        handler.errorDisplay(getActivity(), array.getString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = handler.onErrorResponse(error);
                progressBar.setVisibility(GONE);
                confirm.setVisibility(VISIBLE);
                cancel.setVisibility(VISIBLE);
                handler.errorDisplay(getActivity(), message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}