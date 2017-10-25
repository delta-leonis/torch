package io.leonis.torch;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * The Class ActiveWindowSelector
 *
 * This class contains the ability to find a {@link Window} at a provided {@link TerminalPosition}
 * and {@link WindowBasedTextGUI}.
 *
 * @author Jeroen de Jong
 */
public class ActiveWindowSelector implements
    BiFunction<WindowBasedTextGUI, TerminalPosition, Optional<Window>> {

  @Override
  public Optional<Window> apply(final WindowBasedTextGUI gui,
      final TerminalPosition terminalPosition) {
    final List<Window> windows = new ArrayList<>(gui.getWindows());
    final int activeWindow = windows.indexOf(gui.getActiveWindow());
    // The windows are reordered in the same order as they are stacked on screen.
    return Stream.concat(
        windows.subList(activeWindow, windows.size()).stream(),
        windows.subList(0, activeWindow).stream())
        .filter(window -> intersecting(window, terminalPosition))
        .findFirst();
  }

  /**
   * Helper method to determine if an position is intersecting with a window.
   *
   * @param window window to check intersection on.
   * @param position location to check.
   * @return true if position is within the boundaries of the window.
   */
  private boolean intersecting(final Window window, final TerminalPosition position) {
    return window.getPosition().getColumn() <= position.getColumn()
        && window.getPosition().getColumn() + window.getSize().getColumns() >= position.getColumn()
        && window.getPosition().getRow() <= position.getRow()
        && window.getPosition().getRow() + window.getSize().getRows() >= position.getRow();
  }
}
