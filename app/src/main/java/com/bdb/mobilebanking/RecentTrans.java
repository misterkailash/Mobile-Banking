package com.bdb.mobilebanking;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bdb.mobilebanking.adapters.TransAdapter;
import com.bdb.mobilebanking.models.Trans;
import com.bdb.mobilebanking.models.User;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.bdb.mobilebanking.utils.RestClient;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentTrans extends AppCompatActivity {

    public int listType;
    public ErrorHandler handler = new ErrorHandler();
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.error_desc)
    TextView errorDesc;
    @BindView(R.id.trans_view)
    RecyclerView recyclerView;
    @BindView(R.id.trans_progress)
    ArchedImageProgressBar progressBar;
    @BindView(R.id.fetching)
    TextView fetching;
    @BindView(R.id.try_again)
    BootstrapButton try_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_trans);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        listType = 1;
        itemsGoneExceptProgress();
        bdbProgress();

        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try_again.setVisibility(View.GONE);
                errorImage.setVisibility(View.GONE);
                errorDesc.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                fetching.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (listType) {
                            case 1:
                                recentTrans();
                                break;
                            case 2:
                                recentTransThisMonth();
                                break;
                            case 3:
                                recentTransLastMonth();
                                break;
                        }
                    }
                }, 1000);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recentTrans();
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recent_trans, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.item1) {
            listType = 1;
            itemsGoneExceptProgress();
            progressBar.setVisibility(View.VISIBLE);
            fetching.setVisibility(View.VISIBLE);
            bdbProgress();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Today's Transactions");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recentTrans();
                }
            }, 500);
        } else if (item.getItemId() == R.id.item2) {
            listType = 2;
            itemsGoneExceptProgress();
            progressBar.setVisibility(View.VISIBLE);
            fetching.setVisibility(View.VISIBLE);
            bdbProgress();
            Objects.requireNonNull(getSupportActionBar()).setTitle("This Month's Trans");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recentTransThisMonth();
                }
            }, 500);
        } else if (item.getItemId() == R.id.item3) {
            listType = 3;
            itemsGoneExceptProgress();
            progressBar.setVisibility(View.VISIBLE);
            fetching.setVisibility(View.VISIBLE);
            bdbProgress();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Last Month's Trans");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recentTransLastMonth();
                }
            }, 500);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void itemsGone() {
        emptyImage.setVisibility(View.GONE);
        errorDesc.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);
        fetching.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        try_again.setVisibility(View.GONE);
    }

    public void itemsGoneExceptProgress() {
        emptyImage.setVisibility(View.GONE);
        errorDesc.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        try_again.setVisibility(View.GONE);
    }

    public void itemsGoneExceptError() {
        emptyImage.setVisibility(View.GONE);
        errorDesc.setVisibility(View.GONE);
        fetching.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void itemsGoneExceptEmpty() {
        fetching.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void bdbProgress() {
        Bitmap Icon = BitmapFactory.decodeResource(getResources(), R.drawable.bdbl);
        progressBar.setProgressImage(Icon, 31.0f);
        progressBar.setCircleSize(30.0f);
        progressBar.setArchSize(32.75f);
        progressBar.setArchColor(Color.parseColor("#4cb050"));
        progressBar.setArchLength(210);
        progressBar.setArchStroke(8.85f);
        progressBar.setArchSpeed(5);
    }

    public void recentTrans() {
        SharedPreferences pf = getSharedPreferences("PREF", MODE_PRIVATE);
        User user = new User();
        user.setUsername(pf.getString("username", null));

        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.RECENT_TRANSACTIONS, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Trans> transList = new ArrayList<>();
                try {
                    Trans[] trans = new Gson().fromJson(response.getString("data"), Trans[].class);
                    transList = Arrays.asList(trans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (transList.isEmpty()) {
                    itemsGoneExceptEmpty();
                    emptyImage.setVisibility(View.VISIBLE);
                    errorDesc.setVisibility(View.VISIBLE);
                    errorDesc.setText(getResources().getString(R.string.no_trans));
                } else {
                    itemsGone();
                    recyclerView.setVisibility(View.VISIBLE);
                    TransAdapter adapter = new TransAdapter(getBaseContext(), transList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = handler.onErrorResponse(error);
                itemsGoneExceptError();
                errorImage.setVisibility(View.VISIBLE);
                errorDesc.setVisibility(View.VISIBLE);
                errorDesc.setText(message);
                try_again.setVisibility(View.VISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void recentTransThisMonth() {
        SharedPreferences pf = getSharedPreferences("PREF", MODE_PRIVATE);
        User user = new User();
        user.setUsername(pf.getString("username", null));

        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.RECENT_TRANSACTIONS_THIS_MONTH, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Trans> transList = new ArrayList<>();
                try {
                    Trans[] trans = new Gson().fromJson(response.getString("data"), Trans[].class);
                    transList = Arrays.asList(trans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (transList.isEmpty()) {
                    itemsGoneExceptEmpty();
                    emptyImage.setVisibility(View.VISIBLE);
                    errorDesc.setVisibility(View.VISIBLE);
                    errorDesc.setText(getResources().getString(R.string.no_trans_this_month));
                } else {
                    itemsGone();
                    recyclerView.setVisibility(View.VISIBLE);
                    TransAdapter adapter = new TransAdapter(getBaseContext(), transList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = handler.onErrorResponse(error);
                itemsGoneExceptError();
                errorImage.setVisibility(View.VISIBLE);
                errorDesc.setVisibility(View.VISIBLE);
                errorDesc.setText(message);
                try_again.setVisibility(View.VISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void recentTransLastMonth() {
        SharedPreferences pf = getSharedPreferences("PREF", MODE_PRIVATE);
        User user = new User();
        user.setUsername(pf.getString("username", null));

        JSONObject object = null;
        try {
            object = new JSONObject(new Gson().toJson(user));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RestClient.RECENT_TRANSACTIONS_LAST_MONTH, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Trans> transList = new ArrayList<>();
                try {
                    Trans[] trans = new Gson().fromJson(response.getString("data"), Trans[].class);
                    transList = Arrays.asList(trans);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (transList.isEmpty()) {
                    itemsGoneExceptEmpty();
                    emptyImage.setVisibility(View.VISIBLE);
                    errorDesc.setVisibility(View.VISIBLE);
                    errorDesc.setText(getResources().getString(R.string.no_trans_last_month));
                } else {
                    itemsGone();
                    recyclerView.setVisibility(View.VISIBLE);
                    TransAdapter adapter = new TransAdapter(getBaseContext(), transList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = handler.onErrorResponse(error);
                itemsGoneExceptError();
                errorImage.setVisibility(View.VISIBLE);
                errorDesc.setVisibility(View.VISIBLE);
                errorDesc.setText(message);
                try_again.setVisibility(View.VISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}