package io.github.tormundsmember.thunderstack.navigation.movehandlerimplementations;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.support.transition.TransitionSet;
import android.view.Gravity;
import android.view.ViewGroup;
import io.github.tormundsmember.thunderstack.navigation.MoveHandler;

/**
 * Created by Tormund Thunderfist on 31.12.2017. Copyright: tormundsmember.github.io
 */

public class SlideMoveHandler implements MoveHandler {

  @NonNull @Override public Transition handleEnter(ViewGroup viewToRemove, ViewGroup viewToAdd) {
    TransitionSet transition = new TransitionSet();
    transition.addTransition(new Slide(Gravity.START)
        .addTarget(viewToRemove)
        .setInterpolator(new FastOutSlowInInterpolator())
    ).addTransition(new Slide(Gravity.END)
        .addTarget(viewToAdd)
        .setInterpolator(new FastOutSlowInInterpolator())
    );
    return transition;
  }

  @NonNull @Override public Transition handleExit(ViewGroup viewToRemove, ViewGroup viewToAdd) {
    TransitionSet transition = new TransitionSet();
    transition.addTransition(new Slide(Gravity.END)
        .addTarget(viewToRemove)
        .setInterpolator(new FastOutSlowInInterpolator())
    ).addTransition(new Slide(Gravity.START)
        .addTarget(viewToAdd)
        .setInterpolator(new FastOutSlowInInterpolator())
    );
    return transition;
  }

  protected SlideMoveHandler(Parcel in) {

  }

  public SlideMoveHandler() {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<SlideMoveHandler> CREATOR = new Parcelable.Creator<SlideMoveHandler>() {
    @Override
    public SlideMoveHandler createFromParcel(Parcel in) {
      return new SlideMoveHandler(in);
    }

    @Override
    public SlideMoveHandler[] newArray(int size) {
      return new SlideMoveHandler[size];
    }
  };
}