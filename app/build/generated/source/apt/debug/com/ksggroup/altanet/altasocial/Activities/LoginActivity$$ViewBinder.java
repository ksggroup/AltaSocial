// Generated code from Butter Knife. Do not modify!
package com.ksggroup.altanet.altasocial.Activities;

import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class LoginActivity$$ViewBinder<T extends LoginActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends LoginActivity> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.emailText = finder.findRequiredViewAsType(source, 2131558541, "field 'emailText'", EditText.class);
      target.passText = finder.findRequiredViewAsType(source, 2131558542, "field 'passText'", EditText.class);
      target.loginBtn = finder.findRequiredViewAsType(source, 2131558543, "field 'loginBtn'", Button.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.emailText = null;
      target.passText = null;
      target.loginBtn = null;

      this.target = null;
    }
  }
}
