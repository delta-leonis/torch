package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;
import lombok.AllArgsConstructor;

/**
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public final class StopDragHandler implements MouseListener {

  private final Listener listener;

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final MouseAction action) {
    if (action.getActionType().equals(MouseActionType.CLICK_RELEASE)) {
      gui.removeListener(listener);
      gui.removeListener(this);
      return true;
    }
    return false;
  }
}
