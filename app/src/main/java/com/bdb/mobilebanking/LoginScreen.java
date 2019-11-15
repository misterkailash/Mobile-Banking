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
import android.support.v7.app.AppCompatDelegate;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.TRUE;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils coreUtils = new CoreUtils();
    @BindView(R.id.user_edittext)
    EditText username;
    @BindView(R.id.passwd_edittext)
    EditText password;
    @BindView(R.id.forgot_pass)
    TextView forgotPass;
    @BindView(R.id.login_button)
    Button login;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.login_title)
    TextView title;
    @BindView(R.id.bdb)
    ImageView bdb_logo;

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

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.login_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        progressBar.setVisibility(GONE);
        checkEmptyValues();
        login.setOnClickListener(this);

        final View view = findViewById(R.id.login_layout);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = view.getRootView().getHeight() - view.getHeight();
                if (height > dpToPx(LoginScreen.this, 50)) {
                    title.setVisibility(View.GONE);
                    bdb_logo.setVisibility(View.GONE);
                } else {
                    title.setVisibility(VISIBLE);
                    bdb_logo.setVisibility(View.VISIBLE);
                }
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginScreen.this, ForgotPassword.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            if (coreUtils.isConnected(this) != TRUE) {
                handler.errorDisplay(this, getResources().getString(R.string.not_connected));
            } else if (coreUtils.isInternetAvailable() != TRUE) {
                handler.errorDisplay(this, getResources().getString(R.string.no_internet));
            } else {
                login.setVisibility(GONE);
                progressBar.setVisibility(VISIBLE);
                Login();
            }
        }
    }

    public void checkEmptyValues() {
        if (username.getText().toString().trim().equals("") || password.getText().toString().equals("")) {
            login.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
            ShapeDrawable shapedrawable = new ShapeDrawable();
            shapedrawable.setShape(new RectShape());
            shapedrawable.getPaint().setColor(ContextCompat.getColor(LoginScreen.this, R.color.gray_btn_bg_pressed_color));
            shapedrawable.getPaint().setStrokeWidth(10f);
            shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
            login.setBackground(shapedrawable);
            login.setEnabled(false);
        } else {
            login.setVisibility(View.VISIBLE);
            login.setBackgroundResource(R.color.bootstrap_brand_danger);
            login.setTextColor(ContextCompat.getColor(LoginScreen.this, R.color.white));
            login.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public void Login() {
        final User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        TelephonyManager telephonyManager = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        user.setDeviceID(telephonyManager.getDeviceId());

        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.LOGIN_URL, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int success;
                String msg;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("user").getJSONObject(0);
                    success = array.getInt("success");
                    if (success == 1) {
                        Intent intent = new Intent(LoginScreen.this, OTPScreen.class);
                        intent.putExtra("username", array.getString("username"));
                        intent.putExtra("phone", array.getString("phone"));
                        startActivity(intent);
                        username.setText(null);
                        password.setText(null);
                        progressBar.setVisibility(GONE);
                        login.setVisibility(VISIBLE);
                    } else {
                        msg = array.getString("msg");
                        new TTFancyGifDialog.Builder(LoginScreen.this)
                                .setTitle("Error!")
                                .setMessage(msg)
                                .setPositiveBtnText("OK")
                                .setGifResource(R.drawable.error)
                                .OnPositiveClicked(new TTFancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
                                        progressBar.setVisibility(GONE);
                                        login.setVisibility(VISIBLE);
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
                login.setVisibility(VISIBLE);
                handler.errorDisplay(LoginScreen.this, message);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}