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

public class TransAmtFragment_ViewBinding implements Unbinder {
  private TransAmtFragment target;

  @UiThread
  public TransAmtFragment_ViewBinding(TransAmtFragment target, View source) {
    this.target = target;

    target.statement = Utils.findRequiredViewAsType(source, R.id.ft_statement, "field 'statement'", TextView.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.ft_amt, "field 'amount'", BootstrapEditText.class);
    target.notice = Utils.findRequiredViewAsType(source, R.id.notice, "field 'notice'", TextView.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.ft_amt_submit, "field 'submit'", BootstrapButton.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.ft_amt_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransAmtFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.statement = null;
    target.amount = null;
    target.notice = null;
    target.submit = null;
    target.progressBar = null;
  }
}
