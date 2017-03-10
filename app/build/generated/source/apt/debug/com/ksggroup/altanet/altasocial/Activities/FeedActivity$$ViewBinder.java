// Generated code from Butter Knife. Do not modify!
package com.ksggroup.altanet.altasocial.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

      target.feed = finder.findRequiredViewAsType(source, 2131558523, "field 'feed'", ListView.class);
      target.textPost = finder.findRequiredViewAsType(source, 2131558567, "field 'textPost'", EditText.class);
      target.btnPost = finder.findRequiredViewAsType(source, 2131558568, "field 'btnPost'", Button.class);
      target.mySwipeRefreshLayout = finder.findRequiredViewAsType(source, 2131558522, "field 'mySwipeRefreshLayout'", SwipeRefreshLayout.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.feed = null;
      target.textPost = null;
      target.btnPost = null;
      target.mySwipeRefreshLayout = null;

      this.target = null;
    }
  }
}
