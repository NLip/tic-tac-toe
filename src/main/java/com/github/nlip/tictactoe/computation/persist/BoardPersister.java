package com.github.nlip.tictactoe.computation.persist;

import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.values.Game;
import com.github.nlip.tictactoe.values.Position;
import com.github.nlip.tictactoe.wrappers.FilePersister;
import com.github.nlip.tictactoe.wrappers.SystemConfig;
import com.github.nlip.tictactoe.wrappers.Time;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class BoardPersister {
  private static final DateTimeFormatter FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss");

  private static final Gson gson =
      new GsonBuilder()
          .enableComplexMapKeySerialization()
          .registerTypeAdapter(Position.class, new PositionTypeAdapter())
          .create();

  private final FilePersister filePersister;
  private final Time time;
  private final SystemConfig config;

  public void store(Game game) {
    Path path = Path.of(config.getBasePath(), "savegames", createFileName());
    filePersister.write(path, gson.toJson(game));
  }

  public Game load(String fileName) {
    var json = filePersister.read(Path.of(config.getBasePath(), "savegames", fileName));
    return gson.fromJson(json, Game.class);
  }

  private String createFileName() {
    return "game_" + time.now().format(FORMAT) + ".json";
  }
}
