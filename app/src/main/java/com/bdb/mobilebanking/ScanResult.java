package com.bdb.mobilebanking;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.basewin.aidl.OnBarcodeCallBack;
import com.basewin.services.ServiceManager;
import com.beardedhen.androidbootstrap.BootstrapButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanResult extends AppCompatActivity {

    @BindView(R.id.scanQR)
    BootstrapButton scanQR;

    @BindView(R.id.tv_scan_result)
    TextView scan_result;

    @BindView(R.id.tips)
    TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        ButterKnife.bind(this);

        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openZxing(v);
            }
        });
    }

    public void openZxing(View v) {
        try {
            ServiceManager.getInstence().getScan().startScan(10, new OnBarcodeCallBack() {
                @Override
                public void onScanResult(String s) throws RemoteException {
                    tips.setVisibility(View.GONE);
                    setShow("Result: \n-----------------------------------------------------------\n" + s);
                }

                @Override
                public void onFinish() throws RemoteException {
                    tips.setVisibility(View.GONE);
                    setShow("Scan code failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setShow(String msg) {
        scan_result.setText(msg);
    }
}
