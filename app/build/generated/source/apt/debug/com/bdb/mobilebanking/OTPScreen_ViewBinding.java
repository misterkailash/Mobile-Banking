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

public class OTPScreen_ViewBinding implements Unbinder {
  private OTPScreen target;

  @UiThread
  public OTPScreen_ViewBinding(OTPScreen target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OTPScreen_ViewBinding(OTPScreen target, View source) {
    this.target = target;

    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.otp_login_layout, "field 'linearLayout'", LinearLayout.class);
    target.otpfield = Utils.findRequiredViewAsType(source, R.id.otptext, "field 'otpfield'", OtpTextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.otp_progressBar, "field 'progressBar'", ArchedImageProgressBar.class);
    target.resendOTP = Utils.findRequiredViewAsType(source, R.id.resend_otp, "field 'resendOTP'", TextView.class);
    target.otpCountdown = Utils.findRequiredViewAsType(source, R.id.otp_countdown, "field 'otpCountdown'", TextView.class);
    target.waiting = Utils.findRequiredViewAsType(source, R.id.waiting_otp, "field 'waiting'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OTPScreen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linearLayout = null;
    target.otpfield = null;
    target.progressBar = null;
    target.resendOTP = null;
    target.otpCountdown = null;
    target.waiting = null;
  }
}
