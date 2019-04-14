package com.github.nlip.extensions.java.lang.object;

public interface Eq<T> {
  default boolean eq(T other) {
    return equals(other);
  }
}
