// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bdb.mobilebanking.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppIntro2_ViewBinding implements Unbinder {
  private AppIntro2 target;

  @UiThread
  public AppIntro2_ViewBinding(AppIntro2 target, View source) {
    this.target = target;

    target.description = Utils.findRequiredViewAsType(source, R.id.slogan1, "field 'description'", TextView.class);
    target.label = Utils.findRequiredViewAsType(source, R.id.slogan1_label, "field 'label'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AppIntro2 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.description = null;
    target.label = null;
  }
}
