package io.github.tormundsmember.thunderstack.navigation.movehandlerimplementations;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import io.github.tormundsmember.thunderstack.navigation.MoveHandler;

/**
 * Created by Dominik Gudic on 04.01.2018.
 */

public class FadeChangeHandler extends MoveHandler {

  @NonNull @Override public Transition getEnterTransition(@Nullable ViewGroup containerView, View oldView, View newView) {
    TransitionSet set = new TransitionSet();
    Transition fade = new Fade();
    fade.addTarget(oldView);
    fade.addTarget(newView);
    set.addTransition(fade);
    return set;
  }

  @NonNull @Override public Transition getExitTransition(@Nullable ViewGroup containerView, View oldView, View newView) {
    TransitionSet set = new TransitionSet();
    Transition fade = new Fade();
    fade.addTarget(oldView);
    fade.addTarget(newView);
    set.addTransition(fade);
    return set;
  }

  protected FadeChangeHandler(Parcel in) {

  }

  public FadeChangeHandler() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<FadeChangeHandler> CREATOR = new Parcelable.Creator<FadeChangeHandler>() {
    @Override
    public FadeChangeHandler createFromParcel(Parcel in) {
      return new FadeChangeHandler(in);
    }

    @Override
    public FadeChangeHandler[] newArray(int size) {
      return new FadeChangeHandler[size];
    }
  };
}
