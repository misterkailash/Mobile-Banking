// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.beardedhen.androidbootstrap.BootstrapButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ServiceTest_ViewBinding implements Unbinder {
  private ServiceTest target;

  @UiThread
  public ServiceTest_ViewBinding(ServiceTest target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ServiceTest_ViewBinding(ServiceTest target, View source) {
    this.target = target;

    target.fobCircle = Utils.findRequiredViewAsType(source, R.id.fobCircle, "field 'fobCircle'", CircularProgressButton.class);
    target.smsCircle = Utils.findRequiredViewAsType(source, R.id.smsCircle, "field 'smsCircle'", CircularProgressButton.class);
    target.serviceBtn = Utils.findRequiredViewAsType(source, R.id.service_btn, "field 'serviceBtn'", BootstrapButton.class);
    target.result = Utils.findRequiredViewAsType(source, R.id.test_result, "field 'result'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ServiceTest target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fobCircle = null;
    target.smsCircle = null;
    target.serviceBtn = null;
    target.result = null;
  }
}
