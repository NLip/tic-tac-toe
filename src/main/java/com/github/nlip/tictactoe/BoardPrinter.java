package com.github.nlip.tictactoe;

import static com.github.nlip.extensions.java.util.stream.StreamUtil.intersperse;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.values.Row;
import com.github.nlip.tictactoe.wrappers.StringOutputter;
import java.util.List;
import java.util.function.Function;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class BoardPrinter {
  private final StringOutputter stringOutputter;

  public void print(Board b) {
    stringOutputter.printToConsole(asString(b));
  }

  private static List<String> asString(Board board) {
    var spacer = board.getColumns().map(column -> "---").collect(joining("+"));
    return intersperse(spacer, board.getRows().map(rowAsString(board))).collect(toList());
  }

  private static Function<Row, String> rowAsString(Board board) {
    return row ->
        board
            .getColumns()
            .map(column -> Position.of(column, row))
            .map(board::getMark)
            .map(mark -> mark.map(Enum::toString).orElse(" "))
            .collect(joining(" | ", " ", " "));
  }
}
