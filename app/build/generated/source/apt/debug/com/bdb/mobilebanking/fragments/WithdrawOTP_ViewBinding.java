// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bdb.mobilebanking.R;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import in.aabhasjindal.otptextview.OtpTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WithdrawOTP_ViewBinding implements Unbinder {
  private WithdrawOTP target;

  @UiThread
  public WithdrawOTP_ViewBinding(WithdrawOTP target, View source) {
    this.target = target;

    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.withdraw_otp_layout, "field 'linearLayout'", LinearLayout.class);
    target.otp = Utils.findRequiredViewAsType(source, R.id.wd_otptext, "field 'otp'", OtpTextView.class);
    target.waiting_text = Utils.findRequiredViewAsType(source, R.id.wd_waiting_otp, "field 'waiting_text'", TextView.class);
    target.error = Utils.findRequiredViewAsType(source, R.id.otp_error, "field 'error'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.wd_otp_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
    target.resend = Utils.findRequiredViewAsType(source, R.id.wd_resend_otp, "field 'resend'", TextView.class);
    target.otpCountdown = Utils.findRequiredViewAsType(source, R.id.wd_otp_countdown, "field 'otpCountdown'", TextView.class);
    target.confirm = Utils.findRequiredViewAsType(source, R.id.wd_otp_confirm, "field 'confirm'", BootstrapButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WithdrawOTP target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linearLayout = null;
    target.otp = null;
    target.waiting_text = null;
    target.error = null;
    target.progressBar = null;
    target.resend = null;
    target.otpCountdown = null;
    target.confirm = null;
  }
}
