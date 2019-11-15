// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PasswordReset_ViewBinding implements Unbinder {
  private PasswordReset target;

  @UiThread
  public PasswordReset_ViewBinding(PasswordReset target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PasswordReset_ViewBinding(PasswordReset target, View source) {
    this.target = target;

    target.newPass = Utils.findRequiredViewAsType(source, R.id.ps_newpass, "field 'newPass'", EditText.class);
    target.confirmPass = Utils.findRequiredViewAsType(source, R.id.ps_confirm, "field 'confirmPass'", EditText.class);
    target.error = Utils.findRequiredViewAsType(source, R.id.ps_error, "field 'error'", TextView.class);
    target.changePass = Utils.findRequiredViewAsType(source, R.id.ps_change_pass, "field 'changePass'", Button.class);
    target.doneProgress = Utils.findRequiredViewAsType(source, R.id.reset_done_progressBar, "field 'doneProgress'", ArchedImageProgressBar.class);
    target.logging = Utils.findRequiredViewAsType(source, R.id.logging, "field 'logging'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.ps_change_progress, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PasswordReset target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.newPass = null;
    target.confirmPass = null;
    target.error = null;
    target.changePass = null;
    target.doneProgress = null;
    target.logging = null;
    target.progressBar = null;
  }
}
