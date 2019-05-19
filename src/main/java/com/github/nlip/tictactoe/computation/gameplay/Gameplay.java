package com.github.nlip.tictactoe.computation.gameplay;

import static com.github.nlip.tictactoe.values.Mark.O;
import static com.github.nlip.tictactoe.values.Mark.X;
import static java.lang.String.format;
import static java.util.function.Predicate.not;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.computation.input.PositionParser;
import com.github.nlip.tictactoe.computation.output.BoardPrinter;
import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Mark;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import java.util.HashMap;
import java.util.stream.Stream;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class Gameplay {
  private final UserInterface userInterface;
  private final WinnerDeterminator winnerDeterminator;
  private final PositionParser positionParser;
  private final BoardPrinter boardPrinter;

  public void play(Integer size) {
    Game initialGame = Game.of(Board.of(size, new HashMap<>()), X);
    Stream.iterate(initialGame, this::advance)
        .dropWhile(not(this::isFinished))
        .map(Game::getBoard)
        .map(winnerDeterminator::determineWinner)
        .limit(1)
        .forEach(
            winner ->
                userInterface.printToConsole(
                    format("%s has won the game!", winner.map(Mark::toString).orElse("Nobody"))));
  }

  public Game advance(Game game) {
    var board = game.getBoard();
    boardPrinter.print(board);
    userInterface.printToConsole(
        format("Player %s. Enter your position.", game.getCurrentPlayer()));
    var position = getPosition(board);

    var newBoard = board.putMark(position, game.getCurrentPlayer());

    return Game.of(newBoard, game.getCurrentPlayer().eq(X) ? O : X);
  }

  private boolean isFinished(Game game) {
    var board = game.getBoard();
    return board.isFull() || winnerDeterminator.determineWinner(board).isPresent();
  }

  private Position getPosition(Board board) {
    while (true) {
      var input = userInterface.readFromConsole();
      try {
        var position = positionParser.parse(input);
        if (board.contains(position)) {
          userInterface.printToConsole(
              format("Cheater! Position %s is already occupied! Try again!", position));
        } else {
          return position;
        }
      } catch (Exception e) {
        userInterface.printToConsole(
            format("Invalid input %s. Try again. Enter unoccupied position (x,y)", input));
      }
    }
  }
}
