package com.bdb.mobilebanking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.basewin.services.ServiceManager;
import com.bdb.mobilebanking.fragments.BalanceFragment;
import com.bdb.mobilebanking.fragments.DepositFragment;
import com.bdb.mobilebanking.fragments.LoanFragment;
import com.bdb.mobilebanking.fragments.MenuFragment;
import com.bdb.mobilebanking.fragments.RegistrationFragment;
import com.bdb.mobilebanking.fragments.TransferFragment;
import com.bdb.mobilebanking.fragments.WithdrawFragment;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fab_menu)
    FloatingActionMenu fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab_menu_wrapper)
    View view;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceManager.getInstence().init(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) view.setVisibility(View.VISIBLE);
                else view.setVisibility(View.GONE);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.close(true);
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new MenuFragment(), "MENU_FRAGMENT").commit();

        SharedPreferences.Editor editor = getSharedPreferences("PREF", MODE_PRIVATE).edit();
        editor.putInt("STATE", 0).apply();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sp = getSharedPreferences("PREF", MODE_PRIVATE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (sp.getInt("STATE", 0) == 1) {
            Toast.makeText(this, "Cannot Go Back", Toast.LENGTH_SHORT).show();
        } else if (sp.getInt("STATE", 0) == 2) {
            super.onBackPressed();
            checkFragment();
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.account) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            navigationView.getMenu().getItem(0).setChecked(true);
            FragmentManager manager = getSupportFragmentManager();
            for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
                manager.popBackStack();
            }
            manager.beginTransaction().replace(R.id.content_main, new MenuFragment()).commit();
        } else if (id == R.id.my_statement) {
            navigationView.getMenu().getItem(1).setCheckable(false);
            startActivity(new Intent(this, MyStatement.class));
        } else if (id == R.id.calculator) {
            navigationView.getMenu().getItem(2).setCheckable(false);
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.password) {
            navigationView.getMenu().getItem(3).setCheckable(false);
            startActivity(new Intent(this, PasswordChange.class));
        } else if (id == R.id.recent_trans) {
            navigationView.getMenu().getItem(4).setCheckable(false);
            startActivity(new Intent(this, RecentTrans.class));
        } else if (id == R.id.contact_us) {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.about_us) {
            startActivity(new Intent(this, About.class));
        } else if (id == R.id.logout) {
            new TTFancyGifDialog.Builder(this)
                    .setTitle("Confirmation Alert!")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveBtnText("Yes")
                    .setPositiveBtnBackground("#22b573")
                    .setNegativeBtnText("Cancel")
                    .setNegativeBtnBackground("#c1272d")
                    .setGifResource(R.drawable.exit)
                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            Logout();
                        }
                    })
                    .OnNegativeClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .build();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Logout() {
        SharedPreferences.Editor editor = getSharedPreferences("PREF", MODE_PRIVATE).edit();
        editor.clear().apply();
        startActivity(new Intent(this, LoginScreen.class));
    }

    public void checkFragment() {
        MenuFragment fragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag("MENU_FRAGMENT");
        if (fragment != null && fragment.isVisible()) {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    public void CustomerRegistration(View view) {
        navigationView.getMenu().getItem(0).setChecked(false);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new RegistrationFragment(), "REGISTRATION_FRAGMENT").addToBackStack("Main").commit();
    }

    public void Deposit(View view) {
        navigationView.getMenu().getItem(0).setChecked(false);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new DepositFragment(), "DEPOSIT_FRAGMENT").addToBackStack("Main").commit();
    }

    public void Withdrawal(View view) {
        navigationView.getMenu().getItem(0).setChecked(false);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new WithdrawFragment(), "WITHDRAW_FRAGMENT").addToBackStack("Main").commit();
    }

    public void FundTransfer(View view) {
        navigationView.getMenu().getItem(0).setChecked(false);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new TransferFragment(), "TRANSFER_FRAGMENT").addToBackStack("Main").commit();
    }

    public void LoanRepayment(View view) {
        navigationView.getMenu().getItem(0).setChecked(false);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new LoanFragment(), "LOAN_FRAGMENT").addToBackStack("Main").commit();
    }

    public void BalanceStatement(View view) {
        navigationView.getMenu().getItem(0).setChecked(false);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, new BalanceFragment(), "BALANCE_FRAGMENT").addToBackStack("Main").commit();
    }

    public void ServiceCheck(View view) {
        startActivity(new Intent(this, ServiceTest.class));
    }

    public void QR_Scan(View view) {
        startActivity(new Intent(this, ScanResult.class));
    }
}
