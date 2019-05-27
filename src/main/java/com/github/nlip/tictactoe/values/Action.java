package com.github.nlip.tictactoe.values;

import com.github.nlip.extensions.java.lang.object.Eq;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

public interface Action extends Eq<Action> {
  static Move move(Position p) {
    return new Move(p);
  }

  static Invalid invalid(String reason) {
    return new Invalid(reason);
  }

  static Quit quit() {
    return new Quit();
  }

  static Save save() {
    return new Save();
  }

  @Value
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  class Move implements Action {
    Position position;
  }

  @Value
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  class Invalid implements Action {
    String reason;
  }

  @Value
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  class Quit implements Action {}

  @Value
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  class Save implements Action {}
}
