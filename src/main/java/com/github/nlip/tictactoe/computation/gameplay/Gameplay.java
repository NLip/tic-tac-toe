package com.github.nlip.tictactoe.computation.gameplay;

import static com.github.nlip.tictactoe.values.Mark.X;
import static java.lang.String.format;
import static java.util.function.Predicate.not;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.computation.output.BoardPrinter;
import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Mark;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import java.util.HashMap;
import java.util.stream.Stream;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class Gameplay {
  private final UserInterface userInterface;
  private final BoardPrinter boardPrinter;
  private final WinnerDeterminator winnerDeterminator;
  private final GameAdvancer gameAdvancer;

  public void play(Integer size) {
    Game initialGame = Game.of(Board.of(size, new HashMap<>()), X);
    Stream.iterate(initialGame, gameAdvancer::advance)
        .dropWhile(not(this::isFinished))
        .limit(1)
        .map(Game::getBoard)
        .forEach(
            board -> {
              var winner =
                  winnerDeterminator.determineWinner(board).map(Mark::toString).orElse("Nobody");

              boardPrinter.print(board);
              userInterface.printToConsole(format("%s has won the game!", winner));
            });
  }

  private boolean isFinished(Game game) {
    var board = game.getBoard();
    return board.isFull() || winnerDeterminator.determineWinner(board).isPresent();
  }
}
