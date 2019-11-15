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

public class TransferReview_ViewBinding implements Unbinder {
  private TransferReview target;

  @UiThread
  public TransferReview_ViewBinding(TransferReview target, View source) {
    this.target = target;

    target.statement = Utils.findRequiredViewAsType(source, R.id.tfr_statement, "field 'statement'", TextView.class);
    target.confirm = Utils.findRequiredViewAsType(source, R.id.tfr_confirm, "field 'confirm'", BootstrapButton.class);
    target.cancel = Utils.findRequiredViewAsType(source, R.id.tfr_cancel, "field 'cancel'", BootstrapButton.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.tfr_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransferReview target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.statement = null;
    target.confirm = null;
    target.cancel = null;
    target.progressBar = null;
  }
}
