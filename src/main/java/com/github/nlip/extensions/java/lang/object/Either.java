package com.github.nlip.extensions.java.lang.object;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Either<L, R> {
  L left;
  R right;

  public static <L, R> Either<L, R> left(@NonNull L l) {
    return new Either<>(l, null);
  }

  public static <L, R> Either<L, R> right(@NonNull R r) {
    return new Either<>(null, r);
  }

  public <S> Either<S, R> map(Function<L, S> f) {
    return left != null ? left(f.apply(left)) : right(right);
  }

  public <S> Either<S, R> flatMap(Function<L, Either<S, R>> f) {
    return left != null ? f.apply(left) : right(right);
  }

  public <S> S ifLeftOrIfRight(Function<L, S> ifLeft, Function<R, S> ifRight) {
    return left != null ? ifLeft.apply(left) : ifRight.apply(right);
  }
}
