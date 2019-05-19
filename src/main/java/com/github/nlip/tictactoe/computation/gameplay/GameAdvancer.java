package com.github.nlip.tictactoe.computation.gameplay;

import static com.github.nlip.tictactoe.values.Mark.O;
import static com.github.nlip.tictactoe.values.Mark.X;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.computation.input.PositionParser;
import com.github.nlip.tictactoe.computation.output.BoardPrinter;
import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class GameAdvancer {
  private final UserInterface userInterface;
  private final PositionParser positionParser;
  private final BoardPrinter boardPrinter;

  public Game advance(Game game) {
    var board = game.getBoard();
    var currentPlayer = game.getCurrentPlayer();

    boardPrinter.print(board);
    userInterface.printToConsole(format("Player %s. Enter your position.", currentPlayer));

    var position = getPosition(board);
    return Game.of(board.putMark(position, currentPlayer), currentPlayer.eq(X) ? O : X);
  }

  private Position getPosition(Board board) {
    while (true) {
      var input = userInterface.readFromConsole();
      try {
        var position = positionParser.parse(input);
        if (board.getMark(position).isEmpty()) {
          return position;
        }
        moveNotAllowed(position);
      } catch (Exception e) {
        invalidInput(input);
      }
    }
  }

  private void invalidInput(String input) {
    userInterface.printToConsole(
        format("Invalid input %s. Try again. Enter unoccupied position (x,y)", input));
  }

  private void moveNotAllowed(Position position) {
    userInterface.printToConsole(
        format(
            "Cheater! Position (%s,%s) is already occupied! Try again!",
            position.getColumn().getX(), position.getRow().getY()));
  }
}
