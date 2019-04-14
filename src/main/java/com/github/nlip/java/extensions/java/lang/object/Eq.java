package com.github.nlip.java.extensions.java.lang.object;

public interface Eq<T> {
  default boolean eq(T other) {
    return equals(other);
  }
}
