package com.bdb.mobilebanking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bdb.mobilebanking.R;
import com.bdb.mobilebanking.utils.CoreUtils;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransAmtFragment extends Fragment {

    public ErrorHandler handler = new ErrorHandler();
    public CoreUtils utils = new CoreUtils();
    @BindView(R.id.ft_statement)
    TextView statement;
    @BindView(R.id.ft_amt)
    BootstrapEditText amount;
    @BindView(R.id.notice)
    TextView notice;
    @BindView(R.id.ft_amt_submit)
    BootstrapButton submit;
    @BindView(R.id.ft_amt_progressBar)
    ArchedImageProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.trans_amt_fragment, container, false);
        ButterKnife.bind(this, layout);
        progressBar.setVisibility(View.GONE);
        SharedPreferences pf = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE);
        statement.setText(getResources().getString(R.string.fund_transfer_statememt,
                pf.getString("from_name", null), pf.getString("to_name", null)));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().equals("")) {
                    notice.setText(getResources().getString(R.string.notice_empty));
                } else if (Integer.parseInt(amount.getText().toString()) < 1) {
                    notice.setText(getResources().getString(R.string.notice_amt));
                } else {
                    SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE).edit();
                    editor.putString("amount", amount.getText().toString());
                    editor.apply();
                    FragmentManager manager = getFragmentManager();
                    Objects.requireNonNull(manager).beginTransaction().replace(R.id.content_main, new TransferReview()).addToBackStack("Deposit").commit();
                }
            }
        });
        return layout;
    }
}