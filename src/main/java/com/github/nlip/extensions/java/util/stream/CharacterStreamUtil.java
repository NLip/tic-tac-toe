package com.github.nlip.extensions.java.util.stream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CharacterStreamUtil {
  public static Stream<Character> rangeClosed(Character start, Character endInclusive) {
    return IntStream.rangeClosed(start, endInclusive).mapToObj(x -> (char) x);
  }

  public static Stream<Character> range(Character start, Character endExclusive) {
    return IntStream.range(start, endExclusive).mapToObj(x -> (char) x);
  }
}
