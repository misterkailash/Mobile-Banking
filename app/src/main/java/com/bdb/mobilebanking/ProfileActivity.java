package com.bdb.mobilebanking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.profile_username)
    TextView username;

    @BindView(R.id.profile_name)
    TextView name;

    @BindView(R.id.profile_empid)
    TextView empID;

    @BindView(R.id.profile_gender)
    TextView gender;

    @BindView(R.id.profile_area)
    TextView area;

    @BindView(R.id.profile_branch)
    TextView branch;

    @BindView(R.id.profile_phone)
    TextView phone;

    @BindView(R.id.profile_email)
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences pf = getSharedPreferences("PREF", MODE_PRIVATE);
        username.setText(pf.getString("username", null));
        name.setText(pf.getString("name", null));
        empID.setText(pf.getString("empid", null));
        gender.setText(pf.getString("gender", null));
        area.setText(pf.getString("area", null));
        branch.setText(pf.getString("sol", null));
        phone.setText(pf.getString("phone", null));
        email.setText(pf.getString("email", null));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
