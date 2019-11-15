// Generated code from Butter Knife. Do not modify!
package com.bdb.mobilebanking;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mrtyvz.archedimageprogress.ArchedImageProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecentTrans_ViewBinding implements Unbinder {
  private RecentTrans target;

  @UiThread
  public RecentTrans_ViewBinding(RecentTrans target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RecentTrans_ViewBinding(RecentTrans target, View source) {
    this.target = target;

    target.emptyImage = Utils.findRequiredViewAsType(source, R.id.empty_image, "field 'emptyImage'", ImageView.class);
    target.errorImage = Utils.findRequiredViewAsType(source, R.id.error_image, "field 'errorImage'", ImageView.class);
    target.errorDesc = Utils.findRequiredViewAsType(source, R.id.error_desc, "field 'errorDesc'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.trans_view, "field 'recyclerView'", RecyclerView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.trans_progress, "field 'progressBar'", ArchedImageProgressBar.class);
    target.fetching = Utils.findRequiredViewAsType(source, R.id.fetching, "field 'fetching'", TextView.class);
    target.try_again = Utils.findRequiredViewAsType(source, R.id.try_again, "field 'try_again'", BootstrapButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecentTrans target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.emptyImage = null;
    target.errorImage = null;
    target.errorDesc = null;
    target.recyclerView = null;
    target.progressBar = null;
    target.fetching = null;
    target.try_again = null;
  }
}
