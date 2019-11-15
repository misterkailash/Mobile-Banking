// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bdb.mobilebanking.R;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BalanceView_ViewBinding implements Unbinder {
  private BalanceView target;

  @UiThread
  public BalanceView_ViewBinding(BalanceView target, View source) {
    this.target = target;

    target.account = Utils.findRequiredViewAsType(source, R.id.account, "field 'account'", TextView.class);
    target.balance = Utils.findRequiredViewAsType(source, R.id.balance, "field 'balance'", TextView.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.accountName, "field 'name'", TextView.class);
    target.printReceipt = Utils.findRequiredViewAsType(source, R.id.print_receipt, "field 'printReceipt'", BootstrapButton.class);
    target.QR = Utils.findRequiredViewAsType(source, R.id.qr_code, "field 'QR'", ImageView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.bal_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BalanceView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.account = null;
    target.balance = null;
    target.name = null;
    target.printReceipt = null;
    target.QR = null;
    target.progressBar = null;
  }
}
