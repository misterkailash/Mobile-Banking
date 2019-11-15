// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ForgotPassword_ViewBinding implements Unbinder {
  private ForgotPassword target;

  @UiThread
  public ForgotPassword_ViewBinding(ForgotPassword target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ForgotPassword_ViewBinding(ForgotPassword target, View source) {
    this.target = target;

    target.username = Utils.findRequiredViewAsType(source, R.id.ps_edittext, "field 'username'", EditText.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.ps_button, "field 'submit'", Button.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.ps_progress_bar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ForgotPassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.username = null;
    target.submit = null;
    target.progressBar = null;
  }
}
