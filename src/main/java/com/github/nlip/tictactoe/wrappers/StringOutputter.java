package com.github.nlip.tictactoe.wrappers;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class StringOutputter {
  public void printToConsole(List<String> s) {
    s.forEach(System.out::println);
  }
}
