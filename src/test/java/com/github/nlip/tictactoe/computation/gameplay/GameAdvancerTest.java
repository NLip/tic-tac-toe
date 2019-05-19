package com.github.nlip.tictactoe.computation.gameplay;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.nlip.tictactoe.computation.input.PositionParser;
import com.github.nlip.tictactoe.computation.output.BoardPrinter;
import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Mark;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GameAdvancerTest {
  @Mock UserInterface userInterface;
  @Mock BoardPrinter boardPrinter;
  @Mock PositionParser positionParser;

  @InjectMocks GameAdvancer gameAdvancer;

  Position initialPosition = Position.fromInts(2, 2);
  Board initialBoard = Board.of(2, Map.of(initialPosition, Mark.O));
  Game initialGame = Game.of(initialBoard, Mark.X);
  Position position = Position.fromInts(1, 1);
  Game advancedGame = Game.of(Board.of(2, Map.of(position, Mark.X)), Mark.O);
  String input = "1,1";
  String cheat = "2,2";

  @BeforeEach
  void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Nested
  class Advance {

    @Nested
    class WithValidInput {
      @BeforeEach
      void setup() {
        when(userInterface.readFromConsole()).thenReturn(input);
        when(positionParser.parse(input)).thenReturn(position);
      }

      @Test
      void shouldPrintState() {
        gameAdvancer.advance(initialGame);

        verify(boardPrinter).print(initialBoard);
        verify(userInterface).printToConsole("Player X. Enter your position.");
      }

      @Test
      void shouldReturnAdvancedGame() {
        var result = gameAdvancer.advance(initialGame);

        assertThat(result).isEqualTo(advancedGame);
      }
    }

    @Nested
    class WithInvalidInput {
      @BeforeEach
      void setup() {
        when(userInterface.readFromConsole()).thenReturn("no").thenReturn(input);
        when(positionParser.parse(input)).thenReturn(position);
      }

      @Test
      void shouldPrintError() {
        gameAdvancer.advance(initialGame);

        verify(userInterface)
            .printToConsole("Invalid input no. Try again. Enter unoccupied position (x,y)");
      }

      @Test
      void shouldPromptAgain() {
        gameAdvancer.advance(initialGame);

        verify(userInterface, times(2)).readFromConsole();
      }
    }

    @Nested
    class WithCheatingInput {
      @BeforeEach
      void setup() {
        when(userInterface.readFromConsole()).thenReturn(cheat).thenReturn(input);
        when(positionParser.parse(cheat)).thenReturn(initialPosition);
        when(positionParser.parse(input)).thenReturn(position);
      }

      @Test
      void shouldPrintError() {
        gameAdvancer.advance(initialGame);

        verify(userInterface)
            .printToConsole("Cheater! Position (2,2) is already occupied! Try again!");
      }

      @Test
      void shouldPromptAgain() {
        gameAdvancer.advance(initialGame);

        verify(userInterface, times(2)).readFromConsole();
      }
    }
  }
}
