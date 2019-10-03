package com.github.nlip.tictactoe.computation.gameplay;

import static com.github.nlip.tictactoe.values.Mark.O;
import static com.github.nlip.tictactoe.values.Mark.X;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.computation.input.PositionParser;
import com.github.nlip.tictactoe.computation.output.BoardPrinter;
import com.github.nlip.tictactoe.computation.persist.BoardPersister;
import com.github.nlip.tictactoe.values.Action;
import com.github.nlip.tictactoe.values.Action.Invalid;
import com.github.nlip.tictactoe.values.Action.Move;
import com.github.nlip.tictactoe.values.Action.Quit;
import com.github.nlip.tictactoe.values.Action.Save;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Game.Mode;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import java.util.InputMismatchException;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class GameAdvancer {
  private final UserInterface userInterface;
  private final PositionParser positionParser;
  private final BoardPrinter boardPrinter;
  private final BoardPersister boardPersister;

  public Game advance(Game game) {
    boardPrinter.print(game.getBoard());
    userInterface.printToConsole(format("Player %s.", game.getCurrentPlayer()));

    var action = askForAction(game);
    return handleAction(game, action);
  }

  private Action askForAction(Game game) {
    userInterface.printToConsole(
        "Enter unoccupied position (x,y) or type 'quit' or 'save' to abort the current game");
    var input = userInterface.readFromConsole();
    if (input.toLowerCase().equals("quit")) {
      return Action.quit();
    }
    if (input.toLowerCase().equals("save")) {
      return Action.save();
    }

    Position position;
    try {
      position = positionParser.parse(input);
    } catch (InputMismatchException | NumberFormatException e) {
      return Action.invalid(format("Invalid input %s.", input));
    }

    if (!game.getBoard().isInBounds(position)) {
      return Action.invalid(format("Position %s is outside the board.", input));
    }
    if (game.getBoard().getMark(position).isPresent()) {
      return Action.invalid(
          format(
              "Cheater! Position (%s,%s) is already occupied!",
              position.getColumn().getX(), position.getRow().getY()));
    }
    return Action.move(position);
  }

  private Game handleAction(Game game, Action action) {
    var board = game.getBoard();
    var currentPlayer = game.getCurrentPlayer();

    if (action instanceof Quit) {
      return Game.of(board, currentPlayer, Mode.QUIT);
    }
    if (action instanceof Save) {
      boardPersister.store(game);
      return handleAction(game, Action.quit());
    }
    if (action instanceof Move) {
      return Game.of(
          board.putMark(((Move) action).getPosition(), currentPlayer),
          currentPlayer.eq(X) ? O : X,
          game.getMode());
    }
    if (action instanceof Invalid) {
      userInterface.printToConsole(((Invalid) action).getReason());
      userInterface.printToConsole("Try again!");
      var nextAction = askForAction(game);
      return handleAction(game, nextAction);
    }

    throw new IllegalStateException(format("Unexpected Action %s", action));
  }
}
