package com.github.nlip.tictactoe.values;

import static com.github.nlip.extensions.java.lang.integer.IntegerUtil.inRangeClosed;
import static java.util.stream.IntStream.rangeClosed;

import com.github.nlip.extensions.java.lang.object.Eq;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Board implements Eq<Board> {
  Integer size;
  Map<Position, Mark> marks;

  public Optional<Mark> getMark(Position position) {
    return Optional.ofNullable(marks.get(position));
  }

  public Board putMark(Position position, Mark mark) {
    var newMarks = new HashMap<>(marks);
    newMarks.put(position, mark);
    return Board.of(size, newMarks);
  }

  public Stream<Row> getRows() {
    return rangeClosed(1, size).mapToObj(Row::of);
  }

  public Stream<Column> getColumns() {
    return rangeClosed(1, size).mapToObj(Column::of);
  }

  public Column getFirstColumn() {
    return Column.of(1);
  }

  public Row getFirstRow() {
    return Row.of(1);
  }

  public Row getLastRow() {
    return Row.of(size);
  }

  public boolean isInBounds(Position p) {
    return inRangeClosed(1, size, p.getColumn().getX())
        && inRangeClosed(1, size, p.getRow().getY());
  }

  public boolean isFull() {
    return marks.size() >= size * size;
  }
}
