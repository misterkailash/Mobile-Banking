package com.bdb.mobilebanking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bdb.mobilebanking.models.User;
import com.bdb.mobilebanking.utils.CoreUtils;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.bdb.mobilebanking.utils.RestClient;
import com.bdb.mobilebanking.utils.SMSListerner;
import com.bdb.mobilebanking.utils.SMSReceiver;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.google.gson.Gson;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class OTPScreen extends AppCompatActivity {

    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils utils = new CoreUtils();
    @BindView(R.id.otp_login_layout)
    LinearLayout linearLayout;

    @BindView(R.id.otptext)
    OtpTextView otpfield;

    @BindView(R.id.otp_progressBar)
    ArchedImageProgressBar progressBar;

    @BindView(R.id.resend_otp)
    TextView resendOTP;

    @BindView(R.id.otp_countdown)
    TextView otpCountdown;

    @BindView(R.id.waiting_otp)
    TextView waiting;

    String OTP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        String phone = getIntent().getStringExtra("phone");
        for (int i = 3; i < 6; i++) {
            phone = utils.replaceChar(phone, 'X', i);
        }
        waiting.setText(getResources().getString(R.string.otp_waiting, phone));
        progressBar.setVisibility(View.GONE);
        otpCountdown.setVisibility(GONE);

        SMSReceiver.bindListener(new SMSListerner() {
            @Override
            public void messageReceived(String messageText) {
                OTP = messageText;
                otpfield.setOTP(messageText);
            }
        });

        otpfield.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
                OTP = otpfield.getOTP();
                utils.loadProgress(progressBar, getResources());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Login();
                    }
                }, 2500);
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                new CoreUtils().archedProgress(progressBar, getResources());
                progressBar.setArchColor(Color.parseColor("#FFFFFF"));
                resendOTP();
            }
        });
    }

    public void Login() {
        User user = new User();
        user.setUsername(getIntent().getStringExtra("username"));
        user.setOtp(Integer.parseInt(OTP));
        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.OTP_VERIFY, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int success;
                String msg;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("user").getJSONObject(0);
                    success = array.getInt("success");
                    if (success == 1) {
                        SharedPreferences.Editor editor = getSharedPreferences("PREF", MODE_PRIVATE).edit();
                        editor.putInt("auth", 1);
                        editor.putInt("printer_state", 1);
                        editor.putString("username", array.getString("username"));
                        editor.putString("empid", array.getString("empid"));
                        editor.putString("name", array.getString("name"));
                        editor.putString("gender", array.getString("gender"));
                        editor.putString("area", array.getString("area"));
                        editor.putString("sol", array.getString("sol"));
                        editor.putString("phone", array.getString("phone"));
                        editor.putString("email", array.getString("email"));
                        editor.putString("device_id", array.getString("device_id"));
                        editor.apply();
                        Intent intent = new Intent(OTPScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        otpfield.setOTP(null);
                        progressBar.setVisibility(View.GONE);
                        msg = array.getString("msg");
                        new TTFancyGifDialog.Builder(OTPScreen.this)
                                .setTitle("Error!")
                                .setMessage(msg)
                                .setPositiveBtnText("OK")
                                .setGifResource(R.drawable.error)
                                .build();
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
                resendOTP.setVisibility(VISIBLE);
                handler.errorDisplay(OTPScreen.this, message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void resendOTP() {
        User requestOTP = new User();
        requestOTP.setUsername(getIntent().getStringExtra("username"));
        requestOTP.setPhone(getIntent().getStringExtra("phone"));
        requestOTP.setType("login");

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
                        otpCountdown.setVisibility(VISIBLE);
                        progressBar.setVisibility(GONE);
                        otpCountdown.setText("OTP Sent. Resend again in: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        otpCountdown.setVisibility(GONE);
                        resendOTP.setVisibility(VISIBLE);
                    }
                }.start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OTPScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}