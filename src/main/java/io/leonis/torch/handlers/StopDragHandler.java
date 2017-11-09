package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;
import lombok.AllArgsConstructor;

/**
 * The Class StopDragHandler.
 *
 * Contains the logic to remove the provided {@link Listener} and itself on {@link
 * MouseActionType#CLICK_RELEASE}.
 *
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public final class StopDragHandler implements MouseListener {

  /**
   * Listener to remove on {@link MouseActionType#CLICK_RELEASE}
   */
  private final Listener listener;

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final MouseAction action) {
    if (action.getActionType().equals(MouseActionType.CLICK_RELEASE)) {
      gui.removeListener(this.listener);
      gui.removeListener(this);
      return true;
    }
    return false;
  }
}
