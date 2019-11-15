package com.bdb.mobilebanking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
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

public class LoanFragment extends Fragment {

    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils utils = new CoreUtils();
    @BindView(R.id.account)
    BootstrapEditText account;
    @BindView(R.id.amount)
    BootstrapEditText amount;
    @BindView(R.id.accError)
    TextView accError;
    @BindView(R.id.amtError)
    TextView amtError;
    @BindView(R.id.submit)
    BootstrapButton submit;
    @BindView(R.id.dep_progressBar)
    ArchedImageProgressBar progressBar;
    TextWatcher accountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new CoreUtils().checkEmptyValues(account, accError);
        }
    };
    TextWatcher amountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new CoreUtils().checkEmptyValues(amount, amtError);
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.loan_fragment, container, false);
        ButterKnife.bind(this, layout);
        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", MODE_PRIVATE).edit();
        editor.putInt("STATE", 2).apply();
        progressBar.setVisibility(View.GONE);
        account.addTextChangedListener(accountWatcher);
        amount.addTextChangedListener(amountWatcher);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = 1;
                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(account.getWindowToken(), 0);
                if (account.getText().toString().equals("")) {
                    accError.setText(getResources().getString(R.string.notice_empty));
                    state = 0;
                }
                if (amount.getText().toString().equals("")) {
                    amtError.setText(getResources().getString(R.string.notice_empty));
                    state = 0;
                } else if (Integer.parseInt(amount.getText().toString()) < 1) {
                    amtError.setText(getResources().getString(R.string.notice_amt));
                    state = 0;
                }
                if (state == 1) {
                    submit.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    utils.archedProgress(progressBar, getResources());
                    CheckAccount();
                }
            }
        });
        return layout;
    }

    public void CheckAccount() {
        User user = new User();
        user.setAccountNumber(account.getText().toString());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(user));
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
                        if (!array.getString("scheme").equals("LAA")) {
                            progressBar.setVisibility(GONE);
                            submit.setVisibility(VISIBLE);
                            handler.errorDisplay(getActivity(), "Not a loan account");
                        } else {
                            SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE).edit();
                            editor.putString("account", account.getText().toString());
                            editor.putString("title", array.getString("title"));
                            editor.putString("account_name", array.getString("acct_name"));
                            editor.putString("amount", amount.getText().toString());
                            editor.putString("sol", array.getString("sol"));
                            editor.apply();
                            FragmentManager manager = getFragmentManager();
                            Objects.requireNonNull(manager).beginTransaction().replace(R.id.content_main, new LoanReview()).addToBackStack("Deposit").commit();
                        }
                    } else {
                        progressBar.setVisibility(GONE);
                        submit.setVisibility(VISIBLE);
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
                submit.setVisibility(VISIBLE);
                handler.errorDisplay(getActivity(), message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}