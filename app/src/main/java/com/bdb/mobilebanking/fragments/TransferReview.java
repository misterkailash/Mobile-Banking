package com.bdb.mobilebanking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class TransferReview extends Fragment {

    public static String fromName, toName, fromAccount, amount;
    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils utils = new CoreUtils();
    SharedPreferences sp;

    @BindView(R.id.tfr_statement)
    TextView statement;
    @BindView(R.id.tfr_confirm)
    BootstrapButton confirm;
    @BindView(R.id.tfr_cancel)
    BootstrapButton cancel;
    @BindView(R.id.tfr_progressBar)
    ArchedImageProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.transfer_review, container, false);
        ButterKnife.bind(this, layout);
        progressBar.setVisibility(GONE);

        sp = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE);
        fromAccount = sp.getString("from_account", null);
        fromName = sp.getString("from_name", null);
        toName = sp.getString("to_name", null);
        amount = sp.getString("amount", null);
        statement.setText(getResources().getString(R.string.transfer_statement, fromName, amount, toName));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setVisibility(GONE);
                cancel.setVisibility(GONE);
                progressBar.setVisibility(View.VISIBLE);
                utils.archedProgress(progressBar, getResources());
                Transfer();
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

    public void Transfer() {
        User user = new User();
        user.setAccountNumber(fromAccount);
        user.setDeviceID(sp.getString("device_id", null));

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.TRANSFER_OTP, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    if (success.equals("SUCCESS")) {
                        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE).edit();
                        editor.putString("phone", array.getString("phone"));
                        editor.apply();
                        FragmentManager manager = getFragmentManager();
                        Objects.requireNonNull(manager).beginTransaction().replace(R.id.content_main, new TransferOTP()).addToBackStack("Transfer").commit();
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