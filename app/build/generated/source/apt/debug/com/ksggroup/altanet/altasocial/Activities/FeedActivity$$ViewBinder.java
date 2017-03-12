// Generated code from Butter Knife. Do not modify!
package com.ksggroup.altanet.altasocial.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class FeedActivity$$ViewBinder<T extends FeedActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends FeedActivity> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.feed = finder.findRequiredViewAsType(source, 2131558545, "field 'feed'", ListView.class);
      target.textPost = finder.findRequiredViewAsType(source, 2131558589, "field 'textPost'", EditText.class);
      target.btnPost = finder.findRequiredViewAsType(source, 2131558590, "field 'btnPost'", Button.class);
      target.mySwipeRefreshLayout = finder.findRequiredViewAsType(source, 2131558544, "field 'mySwipeRefreshLayout'", SwipeRefreshLayout.class);
      target.userId = finder.findRequiredViewAsType(source, 2131558588, "field 'userId'", TextView.class);
      target.emptyTxt = finder.findRequiredViewAsType(source, 2131558541, "field 'emptyTxt'", TextView.class);
      target.profilePic = finder.findRequiredViewAsType(source, 2131558533, "field 'profilePic'", ImageView.class);
      target.addBtn = finder.findRequiredViewAsType(source, 2131558546, "field 'addBtn'", ImageView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.feed = null;
      target.textPost = null;
      target.btnPost = null;
      target.mySwipeRefreshLayout = null;
      target.userId = null;
      target.emptyTxt = null;
      target.profilePic = null;
      target.addBtn = null;

      this.target = null;
    }
  }
}
