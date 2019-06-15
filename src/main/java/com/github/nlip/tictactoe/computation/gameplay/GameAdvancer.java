package com.github.nlip.tictactoe.computation.gameplay;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.computation.output.BoardPrinter;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.ValidationError;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class GameAdvancer {
  private final UserInterface userInterface;
  private final ActionAsker actionAsker;
  private final BoardPrinter boardPrinter;

  public Game advance(Game game) {
    boardPrinter.print(game.getBoard());
    userInterface.printToConsole(format("Player %s.", game.getCurrentPlayer()));

    return innerAdvance(game);
  }

  private Game innerAdvance(Game game) {
    return actionAsker
        .askForAction(game)
        .ifLeftOrIfRight(
            action -> action.handle(game),
            validationError -> {
              userInterface.printToConsole(validationError.getReason());
              userInterface.printToConsole("Try again!");
              return innerAdvance(game);
            });

  }
}
