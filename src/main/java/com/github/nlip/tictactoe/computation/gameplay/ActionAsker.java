package com.github.nlip.tictactoe.computation.gameplay;

import static com.github.nlip.extensions.java.lang.object.Either.left;
import static com.github.nlip.extensions.java.lang.object.Either.right;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.extensions.java.lang.object.Either;
import com.github.nlip.tictactoe.computation.gameplay.objects.action.Action;
import com.github.nlip.tictactoe.computation.input.PositionParser;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.values.ValidationError;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import java.util.InputMismatchException;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class ActionAsker {
  private final UserInterface userInterface;
  private final PositionParser positionParser;
  private final ActionFactory actionFactory;

  public Either<Action, ValidationError> askForAction(Game game) {
    userInterface.printToConsole(
        "Enter unoccupied position (x,y) or type 'quit' or 'save' to abort the current game");
    var input = userInterface.readFromConsole();
    if (input.toLowerCase().equals("quit")) {
      return left(actionFactory.quit());
    }
    if (input.toLowerCase().equals("save")) {
      return left(actionFactory.save());
    }

    Position position;
    try {
      position = positionParser.parse(input);
    } catch (InputMismatchException | NumberFormatException e) {
      return right(ValidationError.of(format("Invalid input %s.", input)));
    }

    if (!game.getBoard().isInBounds(position)) {
      return right(ValidationError.of(format("Position %s is outside the board.", input)));
    }
    if (game.getBoard().getMark(position).isPresent()) {
      return right(
          ValidationError.of(
              format(
                  "Cheater! Position (%s,%s) is already occupied!",
                  position.getColumn().getX(), position.getRow().getY())));
    }
    return left(actionFactory.move(position));
  }
}
