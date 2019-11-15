package com.bdb.mobilebanking.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basewin.aidl.OnPrinterListener;
import com.basewin.define.FontsType;
import com.basewin.services.PrinterBinder;
import com.basewin.services.ServiceManager;
import com.bdb.mobilebanking.R;
import com.bdb.mobilebanking.utils.CoreUtils;
import com.bdb.mobilebanking.utils.ErrorHandler;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceView extends Fragment {

    public SharedPreferences sp;
    public String title, accountName, accountNumber, balanceAmount;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.accountName)
    TextView name;
    @BindView(R.id.print_receipt)
    BootstrapButton printReceipt;
    @BindView(R.id.qr_code)
    ImageView QR;
    @BindView(R.id.bal_progressBar)
    ArchedImageProgressBar progressBar;
    JSONObject printJson = new JSONObject();
    ErrorHandler handler = new ErrorHandler();
    PrinterListener listener = new PrinterListener();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.balance_view, container, false);
        ButterKnife.bind(this, layout);
        progressBar.setVisibility(View.GONE);
        sp = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE);
        title = sp.getString("title", null);
        accountName = sp.getString("account_name", null);
        accountNumber = sp.getString("account", null);
        balanceAmount = sp.getString("balance", null);
        name.setText(getResources().getString(R.string.customer_name, title, accountName));
        account.setText(accountNumber);
        balance.setText(balanceAmount);
        //QR.setImageBitmap(createQR());
        printReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printReceipt.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                CoreUtils utils = new CoreUtils();
                utils.archedProgress(progressBar, getResources());
                printReceipt();
            }
        });
        return layout;
    }

    public Bitmap createQR() {
        Bitmap bitmap = null;
        String str = "Bhutan Development Bank\nBalance Enquiry Receipt\n" +
                "Printed on: " + getTime() +
                "\nAccount Holder: " + title + accountName +
                "\nAccount: " + accountNumber + "\nBalance: " + balanceAmount;
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public String getTime() {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm:ss a");
        return dateFormat.format(date);
    }

    private JSONObject getPrintObject(String test) {
        JSONObject json = new JSONObject();
        try {
            json.put("content-type", "txt");
            json.put("content", test);
            json.put("size", "2");
            json.put("position", "left");
            json.put("offset", "0");
            json.put("bold", "0");
            json.put("italic", "0");
            json.put("height", "-1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void printReceipt() {
        try {
            JSONObject slogan = new JSONObject();
            slogan.put("content-type", "txt");
            slogan.put("content", "Your Development Partner since 1988");
            slogan.put("size", 1);
            slogan.put("position", "center");
            slogan.put("offset", "0");
            slogan.put("bold", "bold");
            slogan.put("italic", "italic");
            slogan.put("height", "-1");

            JSONObject pic = new JSONObject();
            pic.put("content-type", "jpg");
            pic.put("position", "center");

            JSONObject footer = new JSONObject();
            footer.put("content-type", "txt");
            footer.put("content", "\n\nvisit us @ https://www.bdb.bt");
            footer.put("size", 1);
            footer.put("position", "center");
            footer.put("offset", "0");

            JSONArray printTest = new JSONArray();
            ServiceManager.getInstence().getPrinter().setPrintFont(FontsType.simsun);
            ServiceManager.getInstence().getPrinter().setPrintFontByAsserts("songti.ttf");

            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("BHUTAN DEVELOPMENT BANK"));
            printTest.put(slogan);
            printTest.put(pic);
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\nBalance Enquiry Receipt"));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\nPrinted: " + getTime()));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\n\nAccount Holder:\n" + title + accountName));
            printTest.put(getPrintObject("\n\nAccount Number: " + sp.getString("account", "null")));
            printTest.put(getPrintObject("\n\nBalance: " + sp.getString("balance", "null")));
            printTest.put(getPrintObject("\n\n------------------------------------------------"));
            printTest.put(footer);
            printTest.put(getPrintObject("\n* Thank you for banking with us. *\n\n\n"));

            printJson.put("spos", printTest);

            Bitmap qr = createQR();
            Bitmap[] bitmaps;
            bitmaps = new Bitmap[]{qr};

            ServiceManager.getInstence().getPrinter().printBottomFeedLine(2);
            ServiceManager.getInstence().getPrinter().print(printJson.toString(), bitmaps, listener);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PrinterListener implements OnPrinterListener {
        @Override
        public void onStart() {
        }

        @Override
        public void onFinish() {
            printReceipt.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            handler.successDisplay(getActivity());
        }

        @Override
        public void onError(int errorCode, String detail) {
            int printer = sp.getInt("printer_state", 0);
            if (printer == 1) {
                if (errorCode == PrinterBinder.PRINTER_ERROR_NO_PAPER) {
                    handler.errorDisplay(getActivity(), "Paper empty. Please check.");
                }
                if (errorCode == PrinterBinder.PRINTER_ERROR_OVER_HEAT) {
                    handler.errorDisplay(getActivity(), "Printer overheat. Please try again later.");
                }
                if (errorCode == PrinterBinder.PRINTER_ERROR_OTHER) {
                    handler.errorDisplay(getActivity(), "Printing error! Check printer hardware.");
                }
            }
            sp.edit().putInt("printer_state", 0).apply();
            printReceipt.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
