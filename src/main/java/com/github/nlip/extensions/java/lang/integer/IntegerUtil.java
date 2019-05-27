package com.github.nlip.extensions.java.lang.integer;

public class IntegerUtil {
  public static boolean inRangeClosed(Integer startInclusive, Integer endInclusive, Integer x) {
    return startInclusive <= x && x <= endInclusive;
  }
}
