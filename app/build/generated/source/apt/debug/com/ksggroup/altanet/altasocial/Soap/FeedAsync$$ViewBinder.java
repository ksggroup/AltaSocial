// Generated code from Butter Knife. Do not modify!
package com.ksggroup.altanet.altasocial.Soap;

import android.content.res.Resources;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;

public class FeedAsync$$ViewBinder<T extends FeedAsync> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    Resources res = finder.getContext(source).getResources();
    bindToTarget(target, res);
    return Unbinder.EMPTY;
  }

  @SuppressWarnings("ResourceType")
  protected static void bindToTarget(FeedAsync target, Resources res) {
    target.NAMESPACE = res.getString(2131165205);
    target.URL = res.getString(2131165206);
  }
}
