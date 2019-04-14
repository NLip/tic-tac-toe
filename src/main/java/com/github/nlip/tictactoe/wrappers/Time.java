package com.github.nlip.tictactoe.wrappers;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class Time {
  public LocalDateTime now() {
    return LocalDateTime.now();
  }
}
