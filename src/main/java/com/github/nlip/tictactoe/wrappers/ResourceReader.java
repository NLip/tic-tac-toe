package com.github.nlip.tictactoe.wrappers;

import static lombok.AccessLevel.PRIVATE;

import java.io.InputStream;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class ResourceReader {
  public InputStream readResource(String fileName) {
    return getClass().getResourceAsStream(fileName);
  }
}
