// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PasswordChange_ViewBinding implements Unbinder {
  private PasswordChange target;

  @UiThread
  public PasswordChange_ViewBinding(PasswordChange target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PasswordChange_ViewBinding(PasswordChange target, View source) {
    this.target = target;

    target.currentPass = Utils.findRequiredViewAsType(source, R.id.current_pass, "field 'currentPass'", EditText.class);
    target.currentError = Utils.findRequiredViewAsType(source, R.id.current_pass_error, "field 'currentError'", TextView.class);
    target.newPass = Utils.findRequiredViewAsType(source, R.id.new_pass, "field 'newPass'", EditText.class);
    target.newError = Utils.findRequiredViewAsType(source, R.id.new_pass_error, "field 'newError'", TextView.class);
    target.confirmPass = Utils.findRequiredViewAsType(source, R.id.confirm_pass, "field 'confirmPass'", EditText.class);
    target.confirmError = Utils.findRequiredViewAsType(source, R.id.confirm_pass_error, "field 'confirmError'", TextView.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.pc_submit, "field 'submit'", BootstrapButton.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.pc_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PasswordChange target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.currentPass = null;
    target.currentError = null;
    target.newPass = null;
    target.newError = null;
    target.confirmPass = null;
    target.confirmError = null;
    target.submit = null;
    target.progressBar = null;
  }
}
