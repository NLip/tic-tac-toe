package com.github.nlip.tictactoe;

import static com.google.inject.Guice.createInjector;

import com.github.nlip.tictactoe.computation.gameplay.Gameplay;

public class Main {
  public static void main(String[] args) {
    createInjector().getInstance(Gameplay.class).play(3);
  }
}
