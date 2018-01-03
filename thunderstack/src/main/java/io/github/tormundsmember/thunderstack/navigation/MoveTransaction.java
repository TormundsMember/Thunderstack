package io.github.tormundsmember.thunderstack.navigation;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class MoveTransaction implements Parcelable {

  @NonNull
  private final BaseKey baseKey;

  @NonNull
  private final MoveHandler moveHandler;

  /**
   * uses the default {@link SlideMoveHandler}
   */
  public MoveTransaction(@NonNull BaseKey baseKey) {
    this(baseKey, new SlideMoveHandler());
  }

  /**
   * uses a custom {@link MoveHandler}
   */
  public MoveTransaction(@NonNull BaseKey baseKey, @NonNull MoveHandler moveHandler) {
    this.baseKey = baseKey;
    this.moveHandler = moveHandler;
  }

  @NonNull
  public BaseKey getKey() {
    return baseKey;
  }

  @NonNull
  public MoveHandler getMoveHandler() {
    return moveHandler;
  }

  protected MoveTransaction(Parcel in) {
    baseKey = (BaseKey) in.readValue(BaseKey.class.getClassLoader());
    moveHandler = (MoveHandler) in.readValue(MoveHandler.class.getClassLoader());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(baseKey);
    dest.writeValue(moveHandler);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<MoveTransaction> CREATOR = new Parcelable.Creator<MoveTransaction>() {
    @Override
    public MoveTransaction createFromParcel(Parcel in) {
      return new MoveTransaction(in);
    }

    @Override
    public MoveTransaction[] newArray(int size) {
      return new MoveTransaction[size];
    }
  };
}