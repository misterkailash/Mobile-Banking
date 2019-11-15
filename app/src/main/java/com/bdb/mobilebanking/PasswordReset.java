package com.bdb.mobilebanking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.TRUE;

public class PasswordReset extends AppCompatActivity implements View.OnClickListener {

    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils coreUtils = new CoreUtils();

    @BindView(R.id.ps_newpass)
    EditText newPass;

    @BindView(R.id.ps_confirm)
    EditText confirmPass;

    @BindView(R.id.ps_error)
    TextView error;

    @BindView(R.id.ps_change_pass)
    Button changePass;

    @BindView(R.id.reset_done_progressBar)
    ArchedImageProgressBar doneProgress;

    @BindView(R.id.logging)
    TextView logging;

    @BindView(R.id.ps_change_progress)
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
        setContentView(R.layout.activity_password_reset);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        doneProgress.setVisibility(GONE);
        progressBar.setVisibility(View.GONE);
        logging.setVisibility(GONE);
        checkEmptyValues();
        newPass.addTextChangedListener(textWatcher);
        confirmPass.addTextChangedListener(textWatcher);
        changePass.setOnClickListener(this);
    }

    public void checkEmptyValues() {
        if (newPass.getText().toString().trim().equals("") || confirmPass.getText().toString().equals("")) {
            changePass.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
            ShapeDrawable shapedrawable = new ShapeDrawable();
            shapedrawable.setShape(new RectShape());
            shapedrawable.getPaint().setColor(ContextCompat.getColor(PasswordReset.this, R.color.gray_btn_bg_pressed_color));
            shapedrawable.getPaint().setStrokeWidth(10f);
            shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
            changePass.setBackground(shapedrawable);
            changePass.setEnabled(false);
        } else {
            changePass.setVisibility(View.VISIBLE);
            changePass.setBackgroundResource(R.color.bootstrap_brand_danger);
            changePass.setTextColor(ContextCompat.getColor(PasswordReset.this, R.color.white));
            changePass.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == changePass) {
            if (!newPass.getText().toString().equals(confirmPass.getText().toString())) {
                error.setText(getResources().getString(R.string.no_match));
            } else if (coreUtils.isConnected(this) != TRUE) {
                handler.errorDisplay(this, getResources().getString(R.string.not_connected));
            } else if (coreUtils.isInternetAvailable() != TRUE) {
                handler.errorDisplay(this, getResources().getString(R.string.no_internet));
            } else {
                changePass.setVisibility(GONE);
                progressBar.setVisibility(VISIBLE);
                resetPassword();
            }
        }
    }

    public void resetPassword() {
        User user = new User();
        final SharedPreferences sp = getSharedPreferences("PREF", MODE_PRIVATE);
        user.setUsername(sp.getString("username", null));
        user.setNewPassword(newPass.getText().toString());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.PASSWORD_RESET, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success, msg;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    if (success.equals("SUCCESS")) {
                        msg = array.getString("msg");
                        new TTFancyGifDialog.Builder(PasswordReset.this)
                                .setTitle("Success!")
                                .setMessage(msg)
                                .setPositiveBtnText("OK")
                                .setGifResource(R.drawable.success)
                                .OnPositiveClicked(new TTFancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
                                        newPass.setVisibility(GONE);
                                        confirmPass.setVisibility(GONE);
                                        error.setVisibility(GONE);
                                        sp.edit().putInt("auth", 1).apply();
                                        sp.edit().putInt("printer_state", 1).apply();
                                        doneProgress.setVisibility(VISIBLE);
                                        logging.setVisibility(VISIBLE);
                                        changePass.setVisibility(GONE);
                                        coreUtils.loadProgress(doneProgress, getResources());
                                        doneProgress.setArchColor(Color.parseColor("#d9534f"));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(PasswordReset.this, MainActivity.class));
                                            }
                                        }, 1500);
                                    }
                                })
                                .build();
                        progressBar.setVisibility(GONE);
                        changePass.setVisibility(VISIBLE);
                    } else {
                        msg = array.getString("msg");
                        handler.errorDisplay(PasswordReset.this, msg);
                        progressBar.setVisibility(GONE);
                        changePass.setVisibility(VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = handler.onErrorResponse(error);
                progressBar.setVisibility(GONE);
                changePass.setVisibility(VISIBLE);
                handler.errorDisplay(getParent(), msg);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}