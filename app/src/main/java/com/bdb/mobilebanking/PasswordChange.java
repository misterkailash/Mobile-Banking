package com.bdb.mobilebanking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bdb.mobilebanking.models.User;
import com.bdb.mobilebanking.utils.CoreUtils;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.bdb.mobilebanking.utils.RestClient;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.gson.Gson;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PasswordChange extends AppCompatActivity {

    public int status = 1;
    @BindView(R.id.current_pass)
    EditText currentPass;
    @BindView(R.id.current_pass_error)
    TextView currentError;
    @BindView(R.id.new_pass)
    EditText newPass;
    @BindView(R.id.new_pass_error)
    TextView newError;
    @BindView(R.id.confirm_pass)
    EditText confirmPass;
    @BindView(R.id.confirm_pass_error)
    TextView confirmError;
    @BindView(R.id.pc_submit)
    BootstrapButton submit;
    @BindView(R.id.pc_progressBar)
    ArchedImageProgressBar progressBar;
    ErrorHandler handler = new ErrorHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressBar.setVisibility(View.GONE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPass.getText().toString().equals("")) {
                    currentError.setText(getResources().getString(R.string.notice_empty));
                    status = 0;
                }
                if (newPass.getText().toString().equals("")) {
                    newError.setText(getResources().getString(R.string.notice_empty));
                    status = 0;
                }
                if (confirmPass.getText().toString().equals("")) {
                    confirmError.setText(getResources().getString(R.string.notice_empty));
                    status = 0;
                }

                if (status == 1) {
                    if (!newPass.getText().toString().equals(confirmPass.getText().toString())) {
                        confirmError.setText(getResources().getString(R.string.no_match));
                    } else {
                        submit.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        new CoreUtils().archedProgress(progressBar, getResources());
                        changePassword();
                    }
                }
            }
        });
    }

    public void changePassword() {
        User user = new User();
        SharedPreferences sp = getSharedPreferences("PREF", MODE_PRIVATE);
        user.setUsername(sp.getString("username", null));
        user.setPassword(currentPass.getText().toString());
        user.setNewPassword(newPass.getText().toString());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.PASSWORD_CHANGE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success, msg;
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject array = obj.getJSONArray("data").getJSONObject(0);
                    success = array.getString("status");
                    if (success.equals("SUCCESS")) {
                        msg = array.getString("msg");
                        new TTFancyGifDialog.Builder(PasswordChange.this)
                                .setTitle("Success!")
                                .setMessage(msg)
                                .setPositiveBtnText("OK")
                                .setGifResource(R.drawable.success)
                                .OnPositiveClicked(new TTFancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
                                        finish();
                                    }
                                })
                                .build();
                        progressBar.setVisibility(GONE);
                        submit.setVisibility(VISIBLE);
                    } else {
                        msg = array.getString("msg");
                        handler.errorDisplay(PasswordChange.this, msg);
                        progressBar.setVisibility(GONE);
                        submit.setVisibility(VISIBLE);
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
                submit.setVisibility(VISIBLE);
                handler.errorDisplay(getParent(), msg);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
