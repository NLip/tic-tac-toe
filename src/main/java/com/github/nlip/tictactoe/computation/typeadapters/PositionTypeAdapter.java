package com.github.nlip.tictactoe.computation.typeadapters;

import static java.util.stream.Collectors.toList;

import com.github.nlip.tictactoe.values.Position;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.stream.Stream;

public class PositionTypeAdapter implements JsonSerializer<Position>, JsonDeserializer<Position> {

  @Override
  public JsonElement serialize(
      Position position, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(position.getColumn().getX() + "," + position.getRow().getY());
  }

  @Override
  public Position deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    var indices = Stream.of(json.getAsString().split(",")).map(Integer::parseInt).collect(toList());
    return Position.fromInts(indices.get(0), indices.get(1));
  }
}
