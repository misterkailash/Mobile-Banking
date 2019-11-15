package com.bdb.mobilebanking.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdb.mobilebanking.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", MODE_PRIVATE).edit();
        editor.putInt("STATE", 0).apply();
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }
}
