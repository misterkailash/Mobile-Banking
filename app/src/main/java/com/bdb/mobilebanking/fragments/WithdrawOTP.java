package com.bdb.mobilebanking.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WithdrawOTP extends Fragment {

    public String account, amount, sol, phone;
    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils utils = new CoreUtils();
    SharedPreferences sd;
    @BindView(R.id.withdraw_otp_layout)
    LinearLayout linearLayout;
    @BindView(R.id.wd_otptext)
    OtpTextView otp;
    @BindView(R.id.wd_waiting_otp)
    TextView waiting_text;
    @BindView(R.id.otp_error)
    TextView error;
    @BindView(R.id.wd_otp_progressBar)
    ArchedImageProgressBar progressBar;
    @BindView(R.id.wd_resend_otp)
    TextView resend;
    @BindView(R.id.wd_otp_countdown)
    TextView otpCountdown;
    @BindView(R.id.wd_otp_confirm)
    BootstrapButton confirm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.withdraw_otp, container, false);
        ButterKnife.bind(this, layout);
        progressBar.setVisibility(GONE);
        otpCountdown.setVisibility(GONE);

        otp.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                new CoreUtils().checkEmptyValues(otp, error);
            }

            @Override
            public void onOTPComplete(String otp) {
                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            }
        });

        sd = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE);
        account = sd.getString("account", null);
        amount = String.valueOf(Integer.parseInt(Objects.requireNonNull(sd.getString("amount", null))));
        sol = sd.getString("sol", null);

        phone = sd.getString("phone", null);
        for (int i = 3; i < 6; i++) {
            phone = utils.replaceChar(phone, 'X', i);
        }
        waiting_text.setText(getResources().getString(R.string.trans_otp_waiting, phone));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getOTP().equals("")) {
                    error.setText(getResources().getString(R.string.notice_empty));
                } else {
                    confirm.setVisibility(GONE);
                    resend.setVisibility(GONE);
                    otpCountdown.setVisibility(GONE);
                    //cancel.setVisibility(GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    utils.archedProgress(progressBar, getResources());
                    Withdraw();
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend.setVisibility(View.GONE);
                otpCountdown.setVisibility(GONE);
                progressBar.setVisibility(View.VISIBLE);
                new CoreUtils().archedProgress(progressBar, getResources());
                resendOTP();
            }
        });

        return layout;
    }

    public void Withdraw() {
        User user = new User();
        user.setUsername(sd.getString("username", null));
        user.setAccountNumber(account);
        user.setAmount(amount);
        user.setSol(sol);
        user.setOtp(Integer.parseInt(otp.getOTP()));
        user.setDeviceID(sd.getString("device_id", null));

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.WITHDRAW, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    if (success.equals("SUCCESS")) {
                        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE).edit();
                        editor.putString("available_balance", array.getString("available_balance"));
                        editor.putString("reference_no", array.getString("ref_no"));
                        editor.putString("time", array.getString("tran_date"));
                        editor.apply();
                        FragmentManager manager = getFragmentManager();
                        Objects.requireNonNull(manager).beginTransaction().replace(R.id.content_main, new WithdrawResult()).addToBackStack("Deposit").commit();
                    } else {
                        progressBar.setVisibility(GONE);
                        confirm.setVisibility(VISIBLE);
                        resend.setVisibility(VISIBLE);
                        otp.setOTP(null);
                        //cancel.setVisibility(VISIBLE);
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
                resend.setVisibility(View.GONE);
                progressBar.setVisibility(GONE);
                otpCountdown.setVisibility(GONE);
                confirm.setVisibility(VISIBLE);
                handler.errorDisplay(getActivity(), message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void resendOTP() {
        User requestOTP = new User();
        requestOTP.setAccountNumber(account);
        requestOTP.setType("withdraw");

        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(requestOTP));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.OTP_RESEND, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                new CountDownTimer(20000, 1000) {
                    @SuppressLint("SetTextI18n")
                    public void onTick(long millisUntilFinished) {
                        resend.setVisibility(View.GONE);
                        otpCountdown.setVisibility(VISIBLE);
                        progressBar.setVisibility(GONE);
                        otpCountdown.setText("OTP Sent. Resend again in: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        otpCountdown.setVisibility(GONE);
                        resend.setVisibility(VISIBLE);
                    }
                }.start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}