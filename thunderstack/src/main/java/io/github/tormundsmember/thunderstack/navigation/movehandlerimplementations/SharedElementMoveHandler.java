package io.github.tormundsmember.thunderstack.navigation.movehandlerimplementations;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import io.github.tormundsmember.thunderstack.navigation.MoveHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tormund Thunderfist on 31.12.2017. Copyright: tormundsmember.github.io
 */

public class SharedElementMoveHandler extends MoveHandler {

  private List<String> transitionNames;

  public SharedElementMoveHandler(List<String> transitionNames) {
    this.transitionNames = transitionNames;
  }

  @NonNull @Override public Transition getEnterTransition(ViewGroup containerView, View oldView, View newView) {
    final Transition changeBounds = new ChangeBounds();
    for (String transitionName : transitionNames) {
      changeBounds.addTarget(transitionName);
    }
    return changeBounds;
  }

  @NonNull @Override public Transition getExitTransition(ViewGroup containerView, View oldView, View newView) {
    final Transition changeBounds = new ChangeBounds();
    for (String transitionName : transitionNames) {
      changeBounds.addTarget(transitionName);
    }
    return changeBounds;
  }

  protected SharedElementMoveHandler(Parcel in) {
    if (in.readByte() == 0x01) {
      transitionNames = new ArrayList<String>();
      in.readList(transitionNames, String.class.getClassLoader());
    } else {
      transitionNames = null;
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    if (transitionNames == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(transitionNames);
    }
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<SharedElementMoveHandler> CREATOR = new Parcelable.Creator<SharedElementMoveHandler>() {
    @Override
    public SharedElementMoveHandler createFromParcel(Parcel in) {
      return new SharedElementMoveHandler(in);
    }

    @Override
    public SharedElementMoveHandler[] newArray(int size) {
      return new SharedElementMoveHandler[size];
    }
  };
}