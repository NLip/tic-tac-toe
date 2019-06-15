package com.github.nlip.tictactoe.computation.gameplay.objects.action;

import static com.github.nlip.tictactoe.values.Mark.O;
import static com.github.nlip.tictactoe.values.Mark.X;

import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Position;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Move implements Action {
  Position position;

  @Override
  public Game handle(Game game) {
    var currentPlayer = game.getCurrentPlayer();
    return Game.of(
        game.getBoard().putMark(position, currentPlayer),
        currentPlayer.eq(X) ? O : X,
        game.getMode());
  }
}
