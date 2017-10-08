package io.leonis.torch.handlers;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.*;

/**ss
 * @author jeroen.dejong.
 */
public class MoveWindowHandler implements Listener {
  @Override
  public boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    if (keyStroke.isAltDown()) {
      final Interactable target = textGUI.getFocusedInteractable();
      final TerminalPosition position = target.getPosition();
      switch(keyStroke.getKeyType()) {
        case ArrowDown:
          target.setPosition(position.withRelative(new TerminalPosition(0, 1)));
          return true;
        case ArrowUp:
          target.setPosition(position.withRelative(new TerminalPosition(0, -1)));
          return true;
        case ArrowLeft:
          target.setPosition(position.withRelative(new TerminalPosition(-1, 0)));
          return true;
        case ArrowRight:
          target.setPosition(position.withRelative(new TerminalPosition(1, 0)));
          return true;
      }
    }
    return false;
  }
}
