// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.comix.overwatch.HiveProgressView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyStatement_ViewBinding implements Unbinder {
  private MyStatement target;

  @UiThread
  public MyStatement_ViewBinding(MyStatement target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyStatement_ViewBinding(MyStatement target, View source) {
    this.target = target;

    target.openingAmt = Utils.findRequiredViewAsType(source, R.id.opening_amount, "field 'openingAmt'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.balance_progress, "field 'progressBar'", HiveProgressView.class);
    target.myBalance = Utils.findRequiredViewAsType(source, R.id.current_balance, "field 'myBalance'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.history_view, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyStatement target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.openingAmt = null;
    target.progressBar = null;
    target.myBalance = null;
    target.recyclerView = null;
  }
}
