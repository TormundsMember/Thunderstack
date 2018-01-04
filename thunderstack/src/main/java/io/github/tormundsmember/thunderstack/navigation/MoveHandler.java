package io.github.tormundsmember.thunderstack.navigation;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.view.View;
import android.view.ViewGroup;

public abstract class MoveHandler<OldView extends View, NewView extends View> implements Parcelable {

  @NonNull public abstract Transition getEnterTransition(@Nullable ViewGroup containerView, OldView oldView, NewView newView);

  @NonNull public abstract Transition getExitTransition(@Nullable ViewGroup containerView, NewView oldView, OldView newView);

  public boolean handleEnter() {
    return false;
  }

  public boolean handleExit() {
    return false;
  }
}
