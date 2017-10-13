package io.leonis.torch.handlers;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * The Class ClickFocusHandler
 *
 * Sets the {@link Window} on which an {@link MouseActionType#CLICK_DOWN} is fired as the active
 * {@link Window}.
 *
 * @author Jeroen de Jong
 */
public class ClickFocusHandler implements Listener {

  @Override
  public boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    if(!(keyStroke instanceof MouseAction))
      return false;
    MouseAction action = ((MouseAction)keyStroke);
    if(!action.getActionType().equals(MouseActionType.CLICK_DOWN))
      return false;
    WindowBasedTextGUI gui = ((WindowBasedTextGUI)textGUI);

    List<Window> windows = new ArrayList<>(gui.getWindows());
    int activeWindow = windows.indexOf(gui.getActiveWindow());
    return
        Stream.concat(
            windows.subList(activeWindow, windows.size()).stream(),
            windows.subList(0, activeWindow).stream())
            .filter(window -> intersecting(window, action.getPosition()))
            .findFirst()
            .map(gui::setActiveWindow).isPresent();
  }

  /**
   * Helper method to determine if an position is intersecting with a window.
   *
   * @param window   window to check intersection on.
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
