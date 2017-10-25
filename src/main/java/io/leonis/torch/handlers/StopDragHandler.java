package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;
import lombok.AllArgsConstructor;

/**
 * @author jeroen.dejong.
 */
@AllArgsConstructor
public final class StopDragHandler implements MouseListener {

  private final Listener listener;

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI,
      final MouseAction mouseAction) {
    if (mouseAction.getActionType().equals(MouseActionType.CLICK_RELEASE)) {
      textGUI.removeListener(listener);
      return true;
    }
    return false;
  }
}
