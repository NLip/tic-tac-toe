package com.github.nlip.tictactoe.values;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Board {
  Set<Position> squares;
}
