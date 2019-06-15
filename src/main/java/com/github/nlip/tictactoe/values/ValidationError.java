package com.github.nlip.tictactoe.values;

import com.github.nlip.extensions.java.lang.object.Eq;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class ValidationError implements Eq<ValidationError> {
  String reason;
}
