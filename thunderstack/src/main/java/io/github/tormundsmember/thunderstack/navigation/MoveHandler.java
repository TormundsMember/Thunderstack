package io.github.tormundsmember.thunderstack.navigation;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.view.ViewGroup;

public interface MoveHandler<OldView extends ViewGroup, NewView extends ViewGroup> extends Parcelable {

  @NonNull Transition handleEnter(OldView oldView, NewView newView);

  @NonNull Transition handleExit(NewView oldView, OldView newView);
}
