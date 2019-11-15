// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bdb.mobilebanking.R;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DepositResult_ViewBinding implements Unbinder {
  private DepositResult target;

  @UiThread
  public DepositResult_ViewBinding(DepositResult target, View source) {
    this.target = target;

    target.amount = Utils.findRequiredViewAsType(source, R.id.dep_amount, "field 'amount'", TextView.class);
    target.balance = Utils.findRequiredViewAsType(source, R.id.available_bal, "field 'balance'", TextView.class);
    target.printReceipt = Utils.findRequiredViewAsType(source, R.id.dep_print, "field 'printReceipt'", BootstrapButton.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.dep_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DepositResult target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.amount = null;
    target.balance = null;
    target.printReceipt = null;
    target.progressBar = null;
  }
}
