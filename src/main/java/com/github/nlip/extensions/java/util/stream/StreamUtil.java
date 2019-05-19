package com.github.nlip.extensions.java.util.stream;

import java.util.stream.Stream;

public class StreamUtil {

  public static <T> Stream<T> intersperse(T t, Stream<T> s) {
    return s.flatMap(x -> Stream.of(t, x)).skip(1);
  }
}
