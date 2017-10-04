package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.*;
import lombok.AllArgsConstructor;

/**
 * The Class WindowDragHandler
 *
 * Moves the provided {@link Window} to the position of the mouse on {@link MouseActionType#DRAG}
 * events.
 *
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public final class WindowDragHandler implements MouseListener {

  /**
   * Target window to move
   */
  private final Window window;

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final MouseAction action) {
    if (action.getActionType().equals(MouseActionType.DRAG)) {
      this.window.setPosition(action.getPosition());
      return true;
    }
    return false;
  }
}
