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
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WithdrawFragment_ViewBinding implements Unbinder {
  private WithdrawFragment target;

  @UiThread
  public WithdrawFragment_ViewBinding(WithdrawFragment target, View source) {
    this.target = target;

    target.account = Utils.findRequiredViewAsType(source, R.id.wd_account, "field 'account'", BootstrapEditText.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.wd_amount, "field 'amount'", BootstrapEditText.class);
    target.accError = Utils.findRequiredViewAsType(source, R.id.wd_accError, "field 'accError'", TextView.class);
    target.amtError = Utils.findRequiredViewAsType(source, R.id.wd_amtError, "field 'amtError'", TextView.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.wd_submit, "field 'submit'", BootstrapButton.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.wd_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WithdrawFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.account = null;
    target.amount = null;
    target.accError = null;
    target.amtError = null;
    target.submit = null;
    target.progressBar = null;
  }
}
