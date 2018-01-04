package io.github.tormundsmember.thunderstack;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.view.ViewGroup;
import io.github.tormundsmember.thunderstack.navigation.MoveHandler;
import io.github.tormundsmember.thunderstack.navigation.MoveTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class NavigationStack {

  private static final String STACK_KEY = "STACK_KEY";

  private final ViewGroup rootView;
  private Stack<MoveTransaction> keyStack;
  private boolean areAnimationsEnabled = true;

  public NavigationStack(@NonNull ViewGroup rootView, @NonNull MoveTransaction defaultKey, @Nullable Bundle savedInstanceState) {
    this.rootView = rootView;
    keyStack = new Stack<>();
    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(STACK_KEY)) {
        final ArrayList<MoveTransaction> parcelableArrayList = savedInstanceState.getParcelableArrayList(STACK_KEY);
        keyStack.addAll(parcelableArrayList);
        goTo(keyStack.pop());
      }
    } else {
      goTo(defaultKey);
    }
  }

  /**
   * Go to a new screen.
   */
  public void goTo(@NonNull MoveTransaction transaction) {
    if (!keyStack.isEmpty() && keyStack.peek().getKey().getKey().equals(transaction.getKey().getKey())) {
      return;
    }
    if (keyStack.contains(transaction)) {
      MoveTransaction currentKey = null;
      while (currentKey == null || !currentKey.getKey().getKey().equals(transaction.getKey().getKey())) {
        currentKey = keyStack.pop();
      }
    }
    keyStack.push(transaction);
    final ViewGroup newView = transaction.getKey().getView(rootView.getContext());
    if (rootView.getChildCount() != 0) {
      final ViewGroup oldView = (ViewGroup) rootView.getChildAt(0);
      TransitionSet transition = new TransitionSet();
      final MoveHandler moveHandler = transaction.getMoveHandler();
      transition.addTransition(moveHandler.getEnterTransition(null, oldView, newView));
      if (areAnimationsEnabled) {
        TransitionManager.beginDelayedTransition(rootView, transition);
      }
      rootView.removeView(oldView);
      rootView.addView(newView);
    } else {
      rootView.addView(newView);
    }
  }

  /**
   * Overwrite the current stack.
   *
   * @param transactions the new Stack in Order of earliest screen to last screen
   */
  public void setHistory(@NonNull List<MoveTransaction> transactions) {
    this.keyStack = new Stack<>();
    keyStack.addAll(transactions);
    goTo(keyStack.pop());
  }

  /**
   * @return a copy of the current stack of MoveTransactions.
   */
  public Stack<MoveTransaction> getBackStack() {
    Stack<MoveTransaction> stack = new Stack<>();
    final MoveTransaction[] moveTransactions = keyStack.toArray(new MoveTransaction[0]);
    stack.addAll(Arrays.asList(moveTransactions));
    return stack;
  }

  /**
   * Clear the stack and set a new Screen as base.
   */
  public void setRoot(@NonNull MoveTransaction transaction) {
    setHistory(Collections.singletonList(transaction));
  }

  /**
   * @return true, if the caller should call their own onBack()-method, because there was nothing to go back to
   */
  public boolean handleBack() {
    if (keyStack.size() < 2) {
      return true;
    } else {
      final MoveHandler handler = keyStack.pop().getMoveHandler();
      final MoveTransaction transaction = keyStack.peek();
      final ViewGroup newView = transaction.getKey().getView(rootView.getContext());
      if (rootView.getChildCount() != 0) {
        TransitionSet transition = new TransitionSet();
        final ViewGroup oldView = (ViewGroup) rootView.getChildAt(0);
        ViewGroup container = null;
        boolean handleChange;
        if (handleChange = handler.handleExit()) {
          container = rootView;
        }
        //noinspection unchecked
        transition.addTransition(handler.getExitTransition(container, oldView, newView));
        if (areAnimationsEnabled) {
          TransitionManager.beginDelayedTransition(rootView, transition);
        }
        if (handleChange) {
          rootView.removeView(oldView);
          rootView.addView(newView);
        }
      } else {
        rootView.addView(newView);
      }
      return false;
    }
  }

  public void setAnimationsEnabled(boolean areAnimationsEnabled) {
    this.areAnimationsEnabled = areAnimationsEnabled;
  }

  /**
   * call to enable recreation of the authstate (in case of process death, configuration-change etc.)
   *
   * @param outState the callers outState
   */
  public void onSaveInstanceState(@NonNull Bundle outState) {
    ArrayList<Parcelable> keys = new ArrayList<>();
    keys.addAll(Arrays.asList(keyStack.toArray(new MoveTransaction[0])));
    outState.putParcelableArrayList(STACK_KEY, keys);
  }
}
