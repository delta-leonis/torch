package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.MouseAction;
import io.leonis.torch.ActiveWindowSelector;

/**
 * @author jeroen.dejong.
 */
public class StartDragHandler implements MouseListener {

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final MouseAction action) {
    return new ActiveWindowSelector().apply(gui, action.getPosition())
              .map(window -> {
                    Listener dragHandler = new WindowDragHandler(window);
                    gui.addListener(new StopDragHandler(dragHandler));
                    gui.addListener(dragHandler);
                    return window;
                }).isPresent();
  }
}
