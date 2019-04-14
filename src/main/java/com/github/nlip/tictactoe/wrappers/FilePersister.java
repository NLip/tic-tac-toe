package com.github.nlip.tictactoe.wrappers;

import static lombok.AccessLevel.PRIVATE;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class FilePersister {
  @SneakyThrows({IOException.class})
  public void write(Path path, String content) {
    try (var writer = new FileWriter(path.toFile())) {
      writer.write(content);
    }
  }

  @SneakyThrows({IOException.class})
  public String read(Path path) {
    try (var reader = new FileReader(path.toFile())) {
      var buf = new char[] {};
      //noinspection ResultOfMethodCallIgnored
      reader.read(buf);
      return String.valueOf(buf);
    }
  }
}
