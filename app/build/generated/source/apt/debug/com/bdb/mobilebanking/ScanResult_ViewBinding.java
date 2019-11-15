// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.beardedhen.androidbootstrap.BootstrapButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScanResult_ViewBinding implements Unbinder {
  private ScanResult target;

  @UiThread
  public ScanResult_ViewBinding(ScanResult target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ScanResult_ViewBinding(ScanResult target, View source) {
    this.target = target;

    target.scanQR = Utils.findRequiredViewAsType(source, R.id.scanQR, "field 'scanQR'", BootstrapButton.class);
    target.scan_result = Utils.findRequiredViewAsType(source, R.id.tv_scan_result, "field 'scan_result'", TextView.class);
    target.tips = Utils.findRequiredViewAsType(source, R.id.tips, "field 'tips'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ScanResult target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.scanQR = null;
    target.scan_result = null;
    target.tips = null;
  }
}
