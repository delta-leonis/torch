package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.*;
import io.leonis.torch.ActiveWindowSelector;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Class DragWindowHandler
 *
 * This class contains the logic for dragging windows with the cursor.
 *
 * @author Jeroen de Jong
 */
public class DragWindowHandler implements Listener {
  private AtomicReference<Window> target = new AtomicReference<>();

  @Override
  public boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    if(!(keyStroke instanceof MouseAction))
      return false;
    MouseAction action = ((MouseAction)keyStroke);
    switch(action.getActionType()) {
      case CLICK_RELEASE:
        // it is only `handled` if the previous value != null
        return target.getAndSet(null) != null;
      case CLICK_DOWN:
        return new ActiveWindowSelector()
            .apply((WindowBasedTextGUI) textGUI, action.getPosition())
            .map(target::getAndSet)
            .isPresent();
      case DRAG:
        return Optional.ofNullable(target.get())
            .map(window -> { window.setPosition(action.getPosition()); return true; })
            .orElse(false);
      default:
        return false;
    }
  }
}
