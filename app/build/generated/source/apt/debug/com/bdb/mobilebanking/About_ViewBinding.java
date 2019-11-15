// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class About_ViewBinding implements Unbinder {
  private About target;

  @UiThread
  public About_ViewBinding(About target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public About_ViewBinding(About target, View source) {
    this.target = target;

    target.copyright = Utils.findRequiredViewAsType(source, R.id.copyright, "field 'copyright'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    About target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.copyright = null;
  }
}
