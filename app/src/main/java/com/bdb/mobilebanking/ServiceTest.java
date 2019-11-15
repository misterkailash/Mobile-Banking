package com.bdb.mobilebanking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bdb.mobilebanking.utils.RestClient;
import com.beardedhen.androidbootstrap.BootstrapButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceTest extends AppCompatActivity {

    @BindView(R.id.fobCircle)
    CircularProgressButton fobCircle;

    @BindView(R.id.smsCircle)
    CircularProgressButton smsCircle;

    @BindView(R.id.service_btn)
    BootstrapButton serviceBtn;

    @BindView(R.id.test_result)
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceBtn.setAlpha(.5f);
                serviceBtn.setClickable(false);

                result.setText(null);
                fobCircle.revertAnimation();
                smsCircle.revertAnimation();

                fobCircle.startAnimation();
                smsCircle.startAnimation();
                checkService();
            }
        });
    }

    public void checkService() {
        fobCheck();
    }

    public void fobCheck() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RestClient.FOB_SERVICE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject array = jsonObject.getJSONArray("data").getJSONObject(0);
                    status = array.getString("status");
                    if (status.equals("SUCCESS")) {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tick);
                        fobCircle.doneLoadingAnimation(0, bitmap);
                        smsCheck();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
                fobCircle.doneLoadingAnimation(0, bitmap);
                smsCircle.revertAnimation();
                result.setText(getResources().getString(R.string.result_fail));
                serviceBtn.setAlpha(1f);
                serviceBtn.setClickable(true);
            }
        });
        AppController.getInstance().requestQueueWithoutRetryPolicy(request);
    }

    public void smsCheck() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RestClient.SMS_SERVICE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject array = jsonObject.getJSONArray("data").getJSONObject(0);
                    status = array.getString("status");
                    if (status.equals("OK")) {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tick);
                        smsCircle.doneLoadingAnimation(0, bitmap);
                        serviceBtn.setAlpha(1f);
                        serviceBtn.setClickable(true);
                        result.setText(getResources().getString(R.string.result_success));
                        result.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.green));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
                smsCircle.doneLoadingAnimation(0, bitmap);
                serviceBtn.setAlpha(1f);
                serviceBtn.setClickable(true);
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
