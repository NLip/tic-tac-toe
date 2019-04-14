package com.github.nlip.tictactoe.values;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Board {
    Map<Position, Figure> figures;

}
