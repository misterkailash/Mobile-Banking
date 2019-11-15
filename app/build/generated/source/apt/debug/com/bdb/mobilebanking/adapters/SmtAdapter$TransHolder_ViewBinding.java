// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bdb.mobilebanking.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SmtAdapter$TransHolder_ViewBinding implements Unbinder {
  private SmtAdapter.TransHolder target;

  @UiThread
  public SmtAdapter$TransHolder_ViewBinding(SmtAdapter.TransHolder target, View source) {
    this.target = target;

    target.TransTag = Utils.findRequiredViewAsType(source, R.id.hist_trans_type, "field 'TransTag'", TextView.class);
    target.mode = Utils.findRequiredViewAsType(source, R.id.hist_value_type, "field 'mode'", TextView.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.hist_value_amount, "field 'amount'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SmtAdapter.TransHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.TransTag = null;
    target.mode = null;
    target.amount = null;
  }
}
