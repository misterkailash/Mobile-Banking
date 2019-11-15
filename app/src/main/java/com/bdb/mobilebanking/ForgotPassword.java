package com.bdb.mobilebanking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bdb.mobilebanking.models.User;
import com.bdb.mobilebanking.utils.CoreUtils;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.bdb.mobilebanking.utils.RestClient;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.TRUE;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils coreUtils = new CoreUtils();
    @BindView(R.id.ps_edittext)
    EditText username;

    @BindView(R.id.ps_button)
    Button submit;

    @BindView(R.id.ps_progress_bar)
    ProgressBar progressBar;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkEmptyValues();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar.setVisibility(View.GONE);
        username.addTextChangedListener(textWatcher);
        checkEmptyValues();

        submit.setOnClickListener(this);
    }

    public void checkEmptyValues() {
        if (username.getText().toString().trim().equals("")) {
            submit.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
            ShapeDrawable shapedrawable = new ShapeDrawable();
            shapedrawable.setShape(new RectShape());
            shapedrawable.getPaint().setColor(ContextCompat.getColor(ForgotPassword.this, R.color.gray_btn_bg_pressed_color));
            shapedrawable.getPaint().setStrokeWidth(10f);
            shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
            submit.setBackground(shapedrawable);
            submit.setEnabled(false);
        } else {
            submit.setVisibility(View.VISIBLE);
            submit.setBackgroundResource(R.color.bootstrap_brand_danger);
            submit.setTextColor(ContextCompat.getColor(ForgotPassword.this, R.color.white));
            submit.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == submit) {
            if (coreUtils.isConnected(this) != TRUE) {
                handler.errorDisplay(this, getResources().getString(R.string.not_connected));
            } else if (coreUtils.isInternetAvailable() != TRUE) {
                handler.errorDisplay(this, getResources().getString(R.string.no_internet));
            } else {
                submit.setVisibility(GONE);
                progressBar.setVisibility(VISIBLE);
                sendResetOTP();
            }
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public void sendResetOTP() {
        final User user = new User();
        user.setUsername(username.getText().toString());

        TelephonyManager telephonyManager = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        user.setDeviceID(telephonyManager.getDeviceId());

        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.FORGOT_PASSWORD, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int success;
                String msg;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("user").getJSONObject(0);
                    success = array.getInt("success");
                    if (success == 1) {
                        Intent intent = new Intent(ForgotPassword.this, OTPResetScreen.class);
                        intent.putExtra("username", array.getString("username"));
                        intent.putExtra("phone", array.getString("phone"));
                        startActivity(intent);
                        username.setText(null);
                        progressBar.setVisibility(GONE);
                        submit.setVisibility(VISIBLE);
                    } else {
                        msg = array.getString("msg");
                        new TTFancyGifDialog.Builder(ForgotPassword.this)
                                .setTitle("Error!")
                                .setMessage(msg)
                                .setPositiveBtnText("OK")
                                .setGifResource(R.drawable.error)
                                .OnPositiveClicked(new TTFancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
                                        progressBar.setVisibility(GONE);
                                        submit.setVisibility(VISIBLE);
                                    }
                                })
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
                submit.setVisibility(VISIBLE);
                handler.errorDisplay(ForgotPassword.this, message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}
