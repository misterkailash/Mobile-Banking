package com.bdb.mobilebanking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class TransferFragment extends Fragment {

    public int state;
    public String from_sol, to_sol;
    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils utils = new CoreUtils();
    @BindView(R.id.ft_from_account)
    BootstrapEditText fromAccount;
    @BindView(R.id.ft_to_acc)
    BootstrapEditText toAccount;
    @BindView(R.id.ft_check)
    BootstrapButton check;
    @BindView(R.id.ft_submit)
    BootstrapButton submit;
    @BindView(R.id.ft_progressBar)
    ArchedImageProgressBar progressBar;
    @BindView(R.id.fromName)
    TextView fromName;
    @BindView(R.id.toName)
    TextView toName;
    @BindView(R.id.fromAcc_img)
    ImageView fromImg;
    @BindView(R.id.toAcc_img)
    ImageView toImg;
    TextWatcher fromWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new CoreUtils().checkEmptyValues(fromAccount, fromName);
        }
    };
    TextWatcher toWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new CoreUtils().checkEmptyValues(toAccount, toName);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.transfer_fragment, container, false);
        ButterKnife.bind(this, layout);
        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", MODE_PRIVATE).edit();
        editor.putInt("STATE", 2).apply();
        progressBar.setVisibility(View.GONE);
        submit.setVisibility(GONE);
        fromAccount.addTextChangedListener(fromWatcher);
        toAccount.addTextChangedListener(toWatcher);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int preState = 1;
                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toAccount.getWindowToken(), 0);
                if (fromAccount.getText().toString().equals("")) {
                    fromName.setText(getResources().getString(R.string.notice_empty));
                    preState = 0;
                }
                if (toAccount.getText().toString().equals("")) {
                    toName.setText(getResources().getString(R.string.notice_empty));
                    preState = 0;
                }
                if (preState == 1) {
                    check.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    utils.archedProgress(progressBar, getResources());
                    checkFromAcc();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE).edit();
                editor.putString("from_account", fromAccount.getText().toString());
                editor.putString("to_account", toAccount.getText().toString());
                editor.putString("from_name", fromName.getText().toString());
                editor.putString("to_name", toName.getText().toString());
                editor.putString("from_sol", from_sol);
                editor.putString("to_sol", to_sol);
                editor.apply();
                FragmentManager manager = getFragmentManager();
                Objects.requireNonNull(manager).beginTransaction().replace(R.id.content_main, new TransAmtFragment()).addToBackStack("Deposit").commit();
            }
        });

        return layout;
    }

    public void checkFromAcc() {
        state = 0;
        final User from = new User();
        from.setAccountNumber(fromAccount.getText().toString());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(from));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.SOL_ENQUIRY, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    if (success.equals("SUCCESS")) {
                        state = 1;
                        fromName.setText(getResources().getString(R.string.customer_name, array.getString("title"), array.getString("acct_name")));
                        fromName.setTextColor(Color.parseColor("#a9a9a9"));
                        from_sol = array.getString("sol");
                        fromImg.setImageResource(R.drawable.tick);
                        fromAccount.setEnabled(false);
                        checkToAcc();
                    } else {
                        fromName.setText(array.getString("error_msg"));
                        fromImg.setImageResource(R.drawable.cross);
                        checkToAcc();
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
                submit.setVisibility(VISIBLE);
                handler.errorDisplay(getActivity(), message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void checkToAcc() {
        User from = new User();
        from.setAccountNumber(toAccount.getText().toString());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(from));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.SOL_ENQUIRY, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    progressBar.setVisibility(GONE);
                    if (success.equals("SUCCESS")) {
                        toName.setText(getResources().getString(R.string.customer_name, array.getString("title"), array.getString("acct_name")));
                        toName.setTextColor(Color.parseColor("#a9a9a9"));
                        to_sol = array.getString("sol");
                        toAccount.setEnabled(false);
                        toImg.setImageResource(R.drawable.tick);
                        if (state == 1) {
                            submit.setVisibility(VISIBLE);
                        } else {
                            fromAccount.setImeOptions(EditorInfo.IME_ACTION_DONE);
                            check.setVisibility(VISIBLE);
                        }
                    } else {
                        toName.setText(array.getString("error_msg"));
                        toImg.setImageResource(R.drawable.cross);
                        check.setVisibility(VISIBLE);
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
                submit.setVisibility(VISIBLE);
                handler.errorDisplay(getActivity(), message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}