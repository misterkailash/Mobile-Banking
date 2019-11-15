// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bdb.mobilebanking.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TransAdapter$TransHolder_ViewBinding implements Unbinder {
  private TransAdapter.TransHolder target;

  @UiThread
  public TransAdapter$TransHolder_ViewBinding(TransAdapter.TransHolder target, View source) {
    this.target = target;

    target.layoutFrom = Utils.findRequiredView(source, R.id.layout_fromAcc, "field 'layoutFrom'");
    target.layoutTo = Utils.findRequiredView(source, R.id.layout_toAcc, "field 'layoutTo'");
    target.TransImage = Utils.findRequiredViewAsType(source, R.id.trans_type, "field 'TransImage'", ImageView.class);
    target.ref_no = Utils.findRequiredViewAsType(source, R.id.value_ref_no, "field 'ref_no'", TextView.class);
    target.date_time = Utils.findRequiredViewAsType(source, R.id.value_date, "field 'date_time'", TextView.class);
    target.mode = Utils.findRequiredViewAsType(source, R.id.value_mode, "field 'mode'", TextView.class);
    target.from_acc = Utils.findRequiredViewAsType(source, R.id.value_from_acc, "field 'from_acc'", TextView.class);
    target.to_acc = Utils.findRequiredViewAsType(source, R.id.value_to_acc, "field 'to_acc'", TextView.class);
    target.amount = Utils.findRequiredViewAsType(source, R.id.value_amount, "field 'amount'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransAdapter.TransHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layoutFrom = null;
    target.layoutTo = null;
    target.TransImage = null;
    target.ref_no = null;
    target.date_time = null;
    target.mode = null;
    target.from_acc = null;
    target.to_acc = null;
    target.amount = null;
  }
}
