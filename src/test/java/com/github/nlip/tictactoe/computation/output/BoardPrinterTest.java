package com.github.nlip.tictactoe.computation.output;

import static org.mockito.Mockito.verify;

import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Mark;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BoardPrinterTest {
  @Mock UserInterface userInterface;
  @InjectMocks BoardPrinter boardPrinter;

  @BeforeEach
  void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Nested
  class Print {
    @Test
    void shouldForwardPrintedBoard() {
      var board =
          Board.of(
              3,
              Map.of(
                  Position.fromInts(1, 3), Mark.O,
                  Position.fromInts(2, 2), Mark.X,
                  Position.fromInts(3, 2), Mark.O));

      var expectedStrings =
          List.of(
              "   |   |   ", //
              "---+---+---",
              "   | X | O ",
              "---+---+---",
              " O |   |   ");

      boardPrinter.print(board);

      verify(userInterface).printToConsole(expectedStrings);
    }
  }
}
