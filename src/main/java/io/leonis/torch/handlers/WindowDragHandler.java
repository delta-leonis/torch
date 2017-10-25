package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.*;
import lombok.AllArgsConstructor;

/**
 * @author jeroen.dejong.
 */
@AllArgsConstructor
public final class WindowDragHandler implements MouseListener {

  private final Window window;

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final MouseAction action) {
    if (action.getActionType().equals(MouseActionType.DRAG)) {
      window.setPosition(action.getPosition());
      return true;
    }
    return false;
  }
}
