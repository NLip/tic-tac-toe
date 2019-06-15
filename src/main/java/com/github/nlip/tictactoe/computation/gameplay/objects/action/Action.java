package com.github.nlip.tictactoe.computation.gameplay.objects.action;

import com.github.nlip.extensions.java.lang.object.Eq;
import com.github.nlip.tictactoe.values.Game;

public interface Action extends Eq<Action> {
  Game handle(Game game);
}
