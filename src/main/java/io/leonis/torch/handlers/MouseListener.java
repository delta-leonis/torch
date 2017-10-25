package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;

/**
 * @author Jeroen de Jong
 */
public interface MouseListener extends MultiWindowListener {

  @Override
  default boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final KeyStroke keyStroke) {
    return keyStroke instanceof MouseAction && onUnhandledKeyStroke(gui,
        (MouseAction) keyStroke);
  }

  boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final MouseAction mouseAction);
}
