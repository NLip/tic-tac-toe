package com.github.nlip.tictactoe.wrappers;

import static lombok.AccessLevel.PRIVATE;

import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class SystemConfig {
  public String getBasePath() {
    return System.getProperty("user.dir");
  }
}
