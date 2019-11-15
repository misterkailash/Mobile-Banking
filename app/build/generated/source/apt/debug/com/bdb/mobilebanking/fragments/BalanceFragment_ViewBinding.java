// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bdb.mobilebanking.R;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BalanceFragment_ViewBinding implements Unbinder {
  private BalanceFragment target;

  @UiThread
  public BalanceFragment_ViewBinding(BalanceFragment target, View source) {
    this.target = target;

    target.progressBar = Utils.findRequiredViewAsType(source, R.id.bal_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
    target.account = Utils.findRequiredViewAsType(source, R.id.edittext_balance, "field 'account'", EditText.class);
    target.error = Utils.findRequiredViewAsType(source, R.id.bal_error, "field 'error'", TextView.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.submit, "field 'submit'", BootstrapButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BalanceFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBar = null;
    target.account = null;
    target.error = null;
    target.submit = null;
  }
}
