package com.github.nlip.tictactoe.computation.gameplay;

import static lombok.AccessLevel.PRIVATE;

import com.github.nlip.tictactoe.computation.gameplay.objects.action.Action;
import com.github.nlip.tictactoe.computation.gameplay.objects.action.Move;
import com.github.nlip.tictactoe.computation.gameplay.objects.action.Quit;
import com.github.nlip.tictactoe.computation.gameplay.objects.action.Save;
import com.github.nlip.tictactoe.computation.persist.BoardPersister;
import com.github.nlip.tictactoe.values.Position;
import javax.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject), access = PRIVATE)
public class ActionFactory {
  private final BoardPersister boardPersister;

  public Action quit() {
    return Quit.of();
  }

  public Action save() {
    return Save.of(boardPersister, Quit.of());
  }

  public Action move(Position position) {
    return Move.of(position);
  }
}
