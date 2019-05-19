package com.github.nlip.extensions.java.lang.integer;

public class IntegerUtil {
  public static boolean inRange(Integer startInclusive, Integer endExclusive, Integer x) {
    return startInclusive <= x && x < endExclusive;
  }

  public static boolean inRangeClosed(Integer startInclusive, Integer endExclusive, Integer x) {
    return startInclusive <= x && x <= endExclusive;
  }
}
