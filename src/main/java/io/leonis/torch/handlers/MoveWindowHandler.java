package io.leonis.torch.handlers;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.KeyStroke;

/**
 * ss
 *
 * @author jeroen.dejong.
 */
public class MoveWindowHandler implements Listener {

  @Override
  public boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    Window window = ((MultiWindowTextGUI) textGUI).getActiveWindow();
    if(!keyStroke.isAltDown())
      return false;
    window.setPosition(window.getPosition().withRelative(this.relativeMove(keyStroke)));
    return true;
  }

  private TerminalPosition relativeMove(KeyStroke keyStroke){
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
