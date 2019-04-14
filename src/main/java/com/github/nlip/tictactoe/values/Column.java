package com.github.nlip.tictactoe.values;

import com.github.nlip.extensions.java.lang.object.Eq;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Column implements Eq<Column>, Comparable<Column>{
    Integer x;

    @Override
    public int compareTo(Column o) {
        return x.compareTo(o.x);
    }
}
