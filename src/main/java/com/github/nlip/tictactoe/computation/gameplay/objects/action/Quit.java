package com.github.nlip.tictactoe.computation.gameplay.objects.action;

import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Game.Mode;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(staticName = "of")
@Value
public class Quit implements Action {

  @Override
  public Game handle(Game game) {
    return Game.of(game.getBoard(), game.getCurrentPlayer(), Mode.QUIT);
  }
}
