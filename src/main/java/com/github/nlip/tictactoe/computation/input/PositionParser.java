package com.github.nlip.tictactoe.computation.input;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.values.Position;
import java.util.InputMismatchException;
import java.util.stream.Stream;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class PositionParser {
  public Position parse(String input) {
    var indices = Stream.of(input.split(",")).map(Integer::parseInt).collect(toList());
    if (indices.size() > 2) {
      throw new InputMismatchException();
    }
    return Position.fromInts(indices.get(0), indices.get(1));
  }
}
