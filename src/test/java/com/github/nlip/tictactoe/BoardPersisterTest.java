package com.github.nlip.tictactoe;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.nlip.tictactoe.computation.persist.BoardPersister;
import com.github.nlip.tictactoe.values.Board;
import com.github.nlip.tictactoe.values.Mark;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.FilePersister;
import com.github.nlip.tictactoe.wrappers.SystemConfig;
import com.github.nlip.tictactoe.wrappers.Time;
import com.google.gson.JsonParser;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BoardPersisterTest {
  @Mock Time time;
  @Mock FilePersister filePersister;
  @Mock SystemConfig config;

  @InjectMocks BoardPersister boardPersister;

  LocalDateTime timestamp = LocalDateTime.of(2018, 1, 5, 21, 12, 0);
  String basePath = "foo";
  String fileName = "board_2018-01-05--21-12-00.json";
  Path path = Path.of(basePath, "savegames", fileName);
  Board board =
      Board.of(
          3,
          Map.of(
              Position.fromInts(1, 3), Mark.O,
              Position.fromInts(2, 2), Mark.X,
              Position.fromInts(3, 2), Mark.O));

  String json =
      "{"
          + "\"marks\":{"
          + "\"1,3\":\"O\","
          + "\"2,2\":\"X\","
          + "\"3,2\":\"O\""
          + "},"
          + "\"size\":3"
          + "}";

  @BeforeEach
  void initMocks() {
    MockitoAnnotations.initMocks(this);
    when(time.now()).thenReturn(timestamp);
    when(config.getBasePath()).thenReturn("foo");
  }

  @Nested
  class Store {
    @Test
    void shouldPassJsonToFilePersister() {
      boardPersister.store(board);

      verify(filePersister).write(eq(path), argThat(matchesJson(json)));
    }
  }

  @Nested
  class Load {
    @BeforeEach
    void init() {
      when(filePersister.read(path)).thenReturn(json);
    }

    @Test
    void shouldLoadBoardFromFilePersister() {
      var result = boardPersister.load(fileName);

      AssertionsForClassTypes.assertThat(result).isEqualTo(board);
    }
  }

  ArgumentMatcher<String> matchesJson(String expectedJson) {
    var parser = new JsonParser();
    return argument -> parser.parse(argument).equals(parser.parse(expectedJson));
  }
}
