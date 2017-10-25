package io.leonis.torch.handlers;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;

/**
 * The Class MoveWindowHandler
 *
 * Contains logic to move the active window with the arrow keys whilst holding alt.
 *
 * @author Jeroen de Jong
 */
public class MoveWindowHandler implements MultiWindowListener {

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final KeyStroke key) {
    if (key.isAltDown()) {
      gui.getActiveWindow().setPosition(
          gui.getActiveWindow().getPosition().withRelative(this.relativeMove(key)));
      return true;
    }
    return false;
  }

  /**
   * Determines the relative position based on a {@link KeyStroke}
   *
   * @param keyStroke key to determine change of position
   * @return the position containing the move
   */
  private TerminalPosition relativeMove(final KeyStroke keyStroke) {
    switch (keyStroke.getKeyType()) {
      case ArrowDown:
        return new TerminalPosition(0, 1);
      case ArrowUp:
        return new TerminalPosition(0, -1);
      case ArrowLeft:
        return new TerminalPosition(-1, 0);
      case ArrowRight:
        return new TerminalPosition(1, 0);
      default:
        return new TerminalPosition(0, 0);
    }
  }
}
