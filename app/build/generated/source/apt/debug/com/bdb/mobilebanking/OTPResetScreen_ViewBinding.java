// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import in.aabhasjindal.otptextview.OtpTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OTPResetScreen_ViewBinding implements Unbinder {
  private OTPResetScreen target;

  @UiThread
  public OTPResetScreen_ViewBinding(OTPResetScreen target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OTPResetScreen_ViewBinding(OTPResetScreen target, View source) {
    this.target = target;

    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.ps_otp_layout, "field 'linearLayout'", LinearLayout.class);
    target.waiting = Utils.findRequiredViewAsType(source, R.id.ps_waiting_otp, "field 'waiting'", TextView.class);
    target.otpfield = Utils.findRequiredViewAsType(source, R.id.ps_otptext, "field 'otpfield'", OtpTextView.class);
    target.resendOTP = Utils.findRequiredViewAsType(source, R.id.ps_resend_otp, "field 'resendOTP'", TextView.class);
    target.otpCountdown = Utils.findRequiredViewAsType(source, R.id.ps_otp_countdown, "field 'otpCountdown'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.ps_otp_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OTPResetScreen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linearLayout = null;
    target.waiting = null;
    target.otpfield = null;
    target.resendOTP = null;
    target.otpCountdown = null;
    target.progressBar = null;
  }
}
