package io.github.tormundsmember.thunderstack.navigation;

import android.content.Context;
import android.os.Parcelable;
import android.view.ViewGroup;

public abstract class BaseKey implements Parcelable {

  public abstract String getKey();

  public abstract ViewGroup getView(Context context);
}
