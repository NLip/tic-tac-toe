package com.github.nlip.tictactoe.computation.gameplay;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Column;
import com.github.nlip.tictactoe.values.Mark;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.values.Row;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class WinnerDeterminator {

  public Optional<Mark> determineWinner(Board board) {
    var rows = board.getRows().map(getRow(board));
    var columns = board.getColumns().map(getColumn(board));
    var diagonals = Stream.of(getLeftDiagonal(board), getRightDiagonal(board));
    var lines = Stream.of(rows, columns, diagonals).flatMap(Function.identity());
    var winners =
        lines
            .map(line -> determineLineWinner(board, line))
            .flatMap(Optional::stream)
            .distinct()
            .collect(toList());

    switch (winners.size()) {
      case 0:
        return empty();
      case 1:
        return Optional.of(winners.get(0));
      default:
        throw new RuntimeException(format("Invalid board: Multiple winners %s", board));
    }
  }

  private static Function<Row, Stream<Position>> getRow(Board board) {
    return row -> getLine(Position.of(board.getFirstColumn(), row), Offset.of(1, 0));
  }

  private static Function<Column, Stream<Position>> getColumn(Board board) {
    return column -> getLine(Position.of(column, board.getFirstRow()), Offset.of(0, 1));
  }

  private static Stream<Position> getLeftDiagonal(Board board) {
    return getLine(Position.of(board.getFirstColumn(), board.getFirstRow()), Offset.of(1, 1));
  }

  private static Stream<Position> getRightDiagonal(Board board) {
    return getLine(Position.of(board.getFirstColumn(), board.getLastRow()), Offset.of(1, -1));
  }

  private static Optional<Mark> determineLineWinner(Board board, Stream<Position> line) {
    var distinctMarks =
        line.takeWhile(board::isInBounds).map(board::getMark).distinct().collect(toList());
    return distinctMarks.size() == 1 ? distinctMarks.get(0) : empty();
  }

  private static Stream<Position> getLine(Position first, Offset offset) {
    return Stream.iterate(
        first,
        position ->
            Position.of(
                position.getColumn().addOffset(offset.getX()),
                position.getRow().addOffset(offset.getY())));
  }

  @Value
  @AllArgsConstructor(staticName = "of")
  private static class Offset {
    Integer x;
    Integer y;
  }
}
