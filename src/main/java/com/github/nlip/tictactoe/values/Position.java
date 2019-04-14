package com.github.nlip.tictactoe.values;

import com.github.nlip.extensions.java.lang.object.Eq;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Position implements Eq<Position> {
  Column column;
  Row row;
  Mark mark;

  public static Position fromInts(int column, int row, Mark mark) {
    return of(Column.of(column), Row.of(row), mark);
  }

  public Optional<Mark> getMark() {
    return Optional.ofNullable(mark);
  }

  public Position markX() {
    return Position.of(column, row, Mark.X);
  }

  public Position markO() {
    return Position.of(column, row, Mark.O);
  }
}
