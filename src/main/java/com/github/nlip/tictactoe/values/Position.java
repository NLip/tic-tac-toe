package com.github.nlip.tictactoe.values;

import com.github.nlip.extensions.java.lang.object.Eq;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Position implements Eq<Position> {
  Column column;
  Row row;

  public static Position fromInts(Integer column, Integer row) {
    return of(Column.of(column), Row.of(row));
  }
}
