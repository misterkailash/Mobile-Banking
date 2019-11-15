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
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TransferFragment_ViewBinding implements Unbinder {
  private TransferFragment target;

  @UiThread
  public TransferFragment_ViewBinding(TransferFragment target, View source) {
    this.target = target;

    target.fromAccount = Utils.findRequiredViewAsType(source, R.id.ft_from_account, "field 'fromAccount'", BootstrapEditText.class);
    target.toAccount = Utils.findRequiredViewAsType(source, R.id.ft_to_acc, "field 'toAccount'", BootstrapEditText.class);
    target.check = Utils.findRequiredViewAsType(source, R.id.ft_check, "field 'check'", BootstrapButton.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.ft_submit, "field 'submit'", BootstrapButton.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.ft_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
    target.fromName = Utils.findRequiredViewAsType(source, R.id.fromName, "field 'fromName'", TextView.class);
    target.toName = Utils.findRequiredViewAsType(source, R.id.toName, "field 'toName'", TextView.class);
    target.fromImg = Utils.findRequiredViewAsType(source, R.id.fromAcc_img, "field 'fromImg'", ImageView.class);
    target.toImg = Utils.findRequiredViewAsType(source, R.id.toAcc_img, "field 'toImg'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransferFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fromAccount = null;
    target.toAccount = null;
    target.check = null;
    target.submit = null;
    target.progressBar = null;
    target.fromName = null;
    target.toName = null;
    target.fromImg = null;
    target.toImg = null;
  }
}
