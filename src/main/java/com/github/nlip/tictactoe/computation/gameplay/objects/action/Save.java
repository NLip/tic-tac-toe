package com.github.nlip.tictactoe.computation.gameplay.objects.action;

import com.github.nlip.tictactoe.computation.persist.BoardPersister;
import com.github.nlip.tictactoe.values.Game;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class Save implements Action {
  BoardPersister boardPersister;
  Quit quit;

  @Override
  public Game handle(Game game) {
    boardPersister.store(game);
    return quit.handle(game);
  }
}
