package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;

/**
 * The Interface MultiWindowListener
 *
 * Extends the {@link MultiWindowListener} interface to work for {@link MouseAction}.
 *
 * @author Jeroen de Jong
 */
public interface MouseListener extends MultiWindowListener {

  @Override
  default boolean onUnhandledKeyStroke(final WindowBasedTextGUI gui, final KeyStroke keyStroke) {
    return keyStroke instanceof MouseAction && onUnhandledKeyStroke(gui,
        (MouseAction) keyStroke);
  }

  /**
   * This method should be called when there was user input that wasn't handled by the GUI. It will
   * fire the {@code onUnhandledKeyStroke(..)} method on any registered listener.
   *
   * @param mouseAction The {@code MouseAction} that wasn't handled by the GUI
   * @return {@code true} if at least one of the listeners handled the key stroke, this will signal
   * to the GUI that it needs to be redrawn again.
   */
  boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final MouseAction mouseAction);
}
