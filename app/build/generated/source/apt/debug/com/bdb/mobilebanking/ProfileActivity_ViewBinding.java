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

public class ProfileActivity_ViewBinding implements Unbinder {
  private ProfileActivity target;

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileActivity_ViewBinding(ProfileActivity target, View source) {
    this.target = target;

    target.username = Utils.findRequiredViewAsType(source, R.id.profile_username, "field 'username'", TextView.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.profile_name, "field 'name'", TextView.class);
    target.empID = Utils.findRequiredViewAsType(source, R.id.profile_empid, "field 'empID'", TextView.class);
    target.gender = Utils.findRequiredViewAsType(source, R.id.profile_gender, "field 'gender'", TextView.class);
    target.area = Utils.findRequiredViewAsType(source, R.id.profile_area, "field 'area'", TextView.class);
    target.branch = Utils.findRequiredViewAsType(source, R.id.profile_branch, "field 'branch'", TextView.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.profile_phone, "field 'phone'", TextView.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.profile_email, "field 'email'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.username = null;
    target.name = null;
    target.empID = null;
    target.gender = null;
    target.area = null;
    target.branch = null;
    target.phone = null;
    target.email = null;
  }
}
