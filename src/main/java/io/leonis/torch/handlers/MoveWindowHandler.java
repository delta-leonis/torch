package io.leonis.torch.handlers;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;

/**
 * The Class MoveWindowHandler.
 *
 * @author Jeroen de Jong
 */
public class MoveWindowHandler implements MultiWindowListener {

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final KeyStroke keyStroke) {
    if (keyStroke.isAltDown()) {
      textGUI.getActiveWindow().setPosition(
          textGUI.getActiveWindow().getPosition().withRelative(this.relativeMove(keyStroke)));
      return true;
    }
    return false;
  }

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
