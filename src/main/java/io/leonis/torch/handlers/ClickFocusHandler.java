package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.*;
import io.leonis.torch.ActiveWindowSelector;

/**
 * The Class ClickFocusHandler
 *
 * Sets the {@link Window} on which an {@link MouseActionType#CLICK_DOWN} is fired as the active
 * {@link Window}.
 *
 * @author Jeroen de Jong
 */
public class ClickFocusHandler implements Listener {

  @Override
  public boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    if(!(keyStroke instanceof MouseAction))
      return false;
    MouseAction action = ((MouseAction)keyStroke);
    if(!action.getActionType().equals(MouseActionType.CLICK_RELEASE))
      return false;
    WindowBasedTextGUI gui = ((WindowBasedTextGUI)textGUI);
    return new ActiveWindowSelector()
            .apply(gui, action.getPosition())
            .map(gui::setActiveWindow).isPresent();
  }
}
