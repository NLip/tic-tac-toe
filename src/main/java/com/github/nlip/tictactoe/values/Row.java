package com.github.nlip.tictactoe.values;

import com.github.nlip.extensions.java.lang.object.Eq;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Row implements Eq<Row>, Comparable<Row>{
    Integer y;

    @Override
    public int compareTo(Row o) {
        return y.compareTo(o.y);
    }
}
