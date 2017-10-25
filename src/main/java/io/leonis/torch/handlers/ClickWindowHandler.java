package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.*;
import io.leonis.torch.ActiveWindowSelector;

/**
 * The Class ClickWindowHandler
 *
 * Sets the {@link Window} on which an {@link MouseActionType#CLICK_DOWN} is fired as the active
 * {@link Window}.
 *
 * @author Jeroen de Jong
 */
public final class ClickWindowHandler implements MouseListener {

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final MouseAction action) {
    return action.getActionType().equals(MouseActionType.CLICK_DOWN)
        && new ActiveWindowSelector().apply(gui, action.getPosition()).map(gui::setActiveWindow)
        .isPresent();
  }
}
