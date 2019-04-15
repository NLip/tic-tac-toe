package com.github.nlip.tictactoe.values;

import static java.util.stream.IntStream.rangeClosed;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Board {
  Integer size;
  Map<Position, Mark> marks;

  public Optional<Mark> getMark(Position position) {
    return Optional.ofNullable(marks.get(position));
  }

  public Board withMark(Position position, Mark mark) {
    var newSquares = new HashMap<>(marks);
    newSquares.put(position, mark);
    return Board.of(size, newSquares);
  }

  public Stream<Row> getRows() {
    return rangeClosed(1, size).mapToObj(Row::of);
  }

  public Stream<Column> getColumns() {
    return rangeClosed(1, size).mapToObj(Column::of);
  }
}
