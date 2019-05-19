package com.github.nlip.tictactoe.computation.gameplay;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.nlip.tictactoe.computation.output.BoardPrinter;
import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Mark;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.UserInterface;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GameplayTest {
  @Mock UserInterface userInterface;
  @Mock BoardPrinter boardPrinter;
  @Mock WinnerDeterminator winnerDeterminator;
  @Mock GameAdvancer gameAdvancer;

  @InjectMocks Gameplay gameplay;

  Board emptyBoard = Board.of(1, Map.of());
  Game emptyGame = Game.of(emptyBoard, Mark.X);
  Board fullBoard = Board.of(1, Map.of(Position.fromInts(1, 1), Mark.X));
  Game fullGame = Game.of(fullBoard, Mark.O);

  @BeforeEach
  void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Nested
  class Play {

    @BeforeEach
    void setupPlay() {
      // Play 3 times, then board is full
      when(gameAdvancer.advance(any()))
          .thenReturn(emptyGame)
          .thenReturn(emptyGame)
          .thenReturn(fullGame);
    }

    @Test
    void shouldPrintFinalBoard() {
      gameplay.play(3);

      verify(boardPrinter).print(fullBoard);
    }

    @Nested
    class WithNoWinner {
      @Test
      void shouldPlayUntilBoardIsFull() {
        gameplay.play(3);

        verify(gameAdvancer, times(3)).advance(any());
      }

      @Test
      void shouldPrintNoWinner() {
        gameplay.play(3);

        verify(userInterface).printToConsole("Nobody has won the game!");
      }
    }

    @Nested
    class WithWinner {
      @BeforeEach
      void setup() {
        // After first advance, X is considered winner
        when(winnerDeterminator.determineWinner(any()))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(Mark.X));
      }

      @Test
      void shouldPlayUntilWinnerFound() {
        gameplay.play(3);

        verify(gameAdvancer, times(1)).advance(any());
      }

      @Test
      void shouldPrintNoWinner() {
        gameplay.play(3);

        verify(userInterface).printToConsole("X has won the game!");
      }
    }
  }
}
