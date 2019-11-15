package com.bdb.mobilebanking.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawResult extends Fragment {

    public SharedPreferences pref;
    public JSONObject printJson = new JSONObject();
    public WithdrawResult.FirstPrintListener first_listener = new WithdrawResult.FirstPrintListener();
    public WithdrawResult.SecondPrintListener second_listener = new WithdrawResult.SecondPrintListener();
    @BindView(R.id.wd_amount)
    TextView amount;
    @BindView(R.id.wd_available_bal)
    TextView balance;
    @BindView(R.id.wd_printReceipt)
    BootstrapButton printReceipt;
    @BindView(R.id.wd_progressBar)
    ArchedImageProgressBar progressBar;
    private ErrorHandler handler = new ErrorHandler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.withdraw_result, container, false);
        ButterKnife.bind(this, layout);
        progressBar.setVisibility(View.GONE);

        SharedPreferences.Editor ed = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE).edit();
        ed.putInt("STATE", 1).apply();

        pref = Objects.requireNonNull(getActivity()).getSharedPreferences("PREF", Context.MODE_PRIVATE);
        amount.setText(String.valueOf(Integer.parseInt(Objects.requireNonNull(pref.getString("amount", null)))));
        balance.setText(pref.getString("available_balance", null));

        printReceipt();

        printReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printReceipt.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                CoreUtils utils = new CoreUtils();
                utils.archedProgress(progressBar, getResources());
                printReceiptCopy();
            }
        });

        return layout;
    }

    public Bitmap createCustomerQR() {
        Bitmap bitmap = null;
        String str = "Bhutan Development Bank\nCash Withdrawal Receipt (Customer)\n" +
                "Ref. No: " + pref.getString("reference_no", null) +
                "\nPrinted on: " + pref.getString("time", null) +
                "\nWithdrawn by: " + pref.getString("username", null) +
                "\nAccount: " + pref.getString("account", null) +
                "\nAmount: " + pref.getString("amount", null) +
                "\nBalance: " + pref.getString("available_balance", null);
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

    public Bitmap createPsoQR() {
        Bitmap bitmap = null;
        String str = "Bhutan Development Bank\nCash Withdrawal Receipt (PSO)\n" +
                "Ref. No: " + pref.getString("reference_no", null) +
                "\nPrinted on: " + pref.getString("time", null) +
                "\nWithdrawn by: " + pref.getString("username", null) +
                "\nAccount: " + pref.getString("account", null) +
                "\nAmount: " + pref.getString("amount", null);
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
            printTest.put(getPrintObject("\nREF. No: " + pref.getString("reference_no", null)));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\nCash Withdrawal Receipt"));
            printTest.put(getPrintObject("\n(Customer Copy)"));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\nPrinted: " + pref.getString("time", null)));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\n\nAccount Holder:\n" + pref.getString("title", null)
                    + pref.getString("account_name", null)));
            printTest.put(getPrintObject("\n\nAccount Number: " + pref.getString("account", "null")));
            printTest.put(getPrintObject("\n\nAmount Withdrawn: " + pref.getString("amount", "null")));
            printTest.put(getPrintObject("\n\nBalance: " + pref.getString("available_balance", "null")));
            printTest.put(getPrintObject("\n\n------------------------------------------------"));
            printTest.put(footer);
            printTest.put(getPrintObject("\n* Thank you for banking with us. *\n\n\n"));

            printJson.put("spos", printTest);

            Bitmap qr = createCustomerQR();
            Bitmap[] bitmaps;
            bitmaps = new Bitmap[]{qr};

            ServiceManager.getInstence().getPrinter().printBottomFeedLine(2);
            ServiceManager.getInstence().getPrinter().print(printJson.toString(), bitmaps, first_listener);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printReceiptCopy() {
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
            printTest.put(getPrintObject("\nREF. No: " + pref.getString("reference_no", null)));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\nCash Withdrawal Receipt"));
            printTest.put(getPrintObject("\n(PSO Copy)"));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\nPrinted: " + pref.getString("time", null)));
            printTest.put(getPrintObject("------------------------------------------------"));
            printTest.put(getPrintObject("\n\nAccount Number: " + pref.getString("account", "null")));
            printTest.put(getPrintObject("\n\nAmount Withdrawn: " + pref.getString("amount", "null")));
            printTest.put(getPrintObject("\n\n------------------------------------------------"));
            printTest.put(getPrintObject("\n\nCustomer Signature: _______________\n"));
            printTest.put(footer);
            printTest.put(getPrintObject("\n* Thank you for banking with us. *\n\n\n"));

            printJson.put("spos", printTest);

            Bitmap qr = createPsoQR();
            Bitmap[] bitmaps;
            bitmaps = new Bitmap[]{qr};

            ServiceManager.getInstence().getPrinter().printBottomFeedLine(2);
            ServiceManager.getInstence().getPrinter().print(printJson.toString(), bitmaps, second_listener);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FirstPrintListener implements OnPrinterListener {
        @Override
        public void onStart() {
        }

        @Override
        public void onFinish() {
        }

        @Override
        public void onError(int errorCode, String detail) {
            int printer = pref.getInt("printer_state", 0);
            if (printer == 1) {
                if (errorCode == PrinterBinder.PRINTER_ERROR_NO_PAPER) {
                    handler.errorDisplay(getActivity(), "Receipt printing failed. Paper empty. Please refill.");
                }
                if (errorCode == PrinterBinder.PRINTER_ERROR_OVER_HEAT) {
                    handler.errorDisplay(getActivity(), "Receipt printing failed. Printer overheat. Please try again later.");
                }
                if (errorCode == PrinterBinder.PRINTER_ERROR_OTHER) {
                    handler.errorDisplay(getActivity(), "Printing error! Check printer hardware.");
                }
                pref.edit().putInt("printer_state", 0).apply();
                printReceipt.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class SecondPrintListener implements OnPrinterListener {
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
            int printer = pref.getInt("printer_state", 0);
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
            pref.edit().putInt("printer_state", 0).apply();
            printReceipt.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
