package com.bdb.mobilebanking;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.bdb.mobilebanking.fragments.AppIntro1;
import com.bdb.mobilebanking.fragments.AppIntro2;
import com.bdb.mobilebanking.fragments.AppIntro3;
import com.bdb.mobilebanking.fragments.AppIntro4;
import com.bdb.mobilebanking.fragments.AppIntro5;
import com.github.paolorotolo.appintro.AppIntro;

import java.util.Objects;

public class IntroActivity extends AppIntro implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    Integer[] colors = {
            Color.argb(200, 11, 169, 244),
            Color.argb(220, 0, 188, 212),
            Color.argb(200, 232, 76, 61),
            Color.argb(210, 90, 100, 177),
            Color.argb(225, 210, 82, 127)
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addSlide(new AppIntro1());
        addSlide(new AppIntro2());
        addSlide(new AppIntro3());
        addSlide(new AppIntro4());
        addSlide(new AppIntro5());
        viewPager = getPager();
        viewPager.addOnPageChangeListener(this);

        setFlowAnimation();

        setSeparatorColor(ContextCompat.getColor(getBaseContext(), R.color.white));

        showSkipButton(true);
        setProgressButtonEnabled(true);

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        startActivity(new Intent(getBaseContext(), LoginScreen.class));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        startActivity(new Intent(getBaseContext(), LoginScreen.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position < (Objects.requireNonNull(viewPager.getAdapter()).getCount() - 1) && position < (colors.length - 1)) {
            viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                getWindow().setStatusBarColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
//            }
        } else {
            viewPager.setBackgroundColor(colors[colors.length - 1]);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {

    }
}
