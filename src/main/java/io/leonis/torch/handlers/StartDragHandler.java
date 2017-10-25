package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;
import io.leonis.torch.ActiveWindowSelector;

/**
 * The Class StartDragHandler
 *
 * Contains behaviour to add two {@link Listener listeners} to the {@link WindowBasedTextGUI gui} if
 * a window has been clicked. The {@link WindowDragHandler} handles the dragging of the window,
 * whilst the {@link StopDragHandler} removes the added {@link Listener listeners} on {@link
 * MouseActionType#CLICK_RELEASE}.
 *
 * @author Jeroen de Jong
 */
public final class StartDragHandler implements MouseListener {

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final MouseAction action) {
    return action.getActionType().equals(MouseActionType.CLICK_DOWN) &&
        new ActiveWindowSelector().apply(gui, action.getPosition())
            .map(window -> {
              Listener dragHandler = new WindowDragHandler(window);
              gui.addListener(new StopDragHandler(dragHandler));
              gui.addListener(dragHandler);
              return window;
            }).isPresent();
  }
}
