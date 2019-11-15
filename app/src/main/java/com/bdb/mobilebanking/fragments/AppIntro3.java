package com.bdb.mobilebanking.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bdb.mobilebanking.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppIntro3 extends Fragment {

    @BindView(R.id.slogan1)
    TextView description;

    @BindView(R.id.slogan1_label)
    TextView label;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carousel_three, container, false);
        ButterKnife.bind(this, view);

        Typeface label_font = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), "Lobster 1.4.otf");
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "Moon Light.otf");
        label.setTypeface(label_font);
        description.setTypeface(custom_font);
        return view;
    }
}