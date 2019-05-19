package com.github.nlip.tictactoe.wrappers;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Scanner;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class UserInterface {
  public void printToConsole(List<String> s) {
    s.forEach(this::printToConsole);
  }

  public void printToConsole(String s) {
    System.out.println(s);
  }

  public String readFromConsole() {
    return new Scanner(System.in).next();
  }
}
