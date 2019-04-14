package com.github.nlip.java.extensions.java.util.stream;

import static java.util.stream.Stream.concat;

import java.util.stream.Stream;

public class StreamUtil {
  public static <T> Stream<T> append(T t, Stream<T> s) {
    return concat(s, Stream.of(t));
  }

  public static <T> Stream<T> surround(T t, Stream<T> s) {
    return concat(Stream.of(t), concat(s, Stream.of(t)));
  }

  public static <T> Stream<T> intersperse(T t, Stream<T> s) {
    return s.flatMap(x -> Stream.of(t, x)).skip(1);
  }
}
