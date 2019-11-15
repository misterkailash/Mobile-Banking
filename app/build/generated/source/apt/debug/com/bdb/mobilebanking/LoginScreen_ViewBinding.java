// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginScreen_ViewBinding implements Unbinder {
  private LoginScreen target;

  @UiThread
  public LoginScreen_ViewBinding(LoginScreen target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginScreen_ViewBinding(LoginScreen target, View source) {
    this.target = target;

    target.username = Utils.findRequiredViewAsType(source, R.id.user_edittext, "field 'username'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.passwd_edittext, "field 'password'", EditText.class);
    target.forgotPass = Utils.findRequiredViewAsType(source, R.id.forgot_pass, "field 'forgotPass'", TextView.class);
    target.login = Utils.findRequiredViewAsType(source, R.id.login_button, "field 'login'", Button.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress_bar, "field 'progressBar'", ProgressBar.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.login_title, "field 'title'", TextView.class);
    target.bdb_logo = Utils.findRequiredViewAsType(source, R.id.bdb, "field 'bdb_logo'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginScreen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.username = null;
    target.password = null;
    target.forgotPass = null;
    target.login = null;
    target.progressBar = null;
    target.title = null;
    target.bdb_logo = null;
  }
}
