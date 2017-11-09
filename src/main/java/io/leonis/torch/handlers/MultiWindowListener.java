package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.KeyStroke;

/**
 * The Interface MultiWindowListener.
 *
 * Extends the {@link Listener} interface to work for {@link WindowBasedTextGUI}.
 *
 * @author Jeroen de Jong
 */
public interface MultiWindowListener extends Listener {

  @Override
  default boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    return textGUI instanceof WindowBasedTextGUI &&
        this.onUnhandledKeyStroke((MultiWindowTextGUI) textGUI, keyStroke);
  }

  /**
   * This method should be called when there was user input that wasn't handled by the GUI. It will
   * fire the {@code onUnhandledKeyStroke(..)} method on any registered listener.
   *
   * @param textGUI The {@link WindowBasedTextGUI} that received the {@link WindowBasedTextGUI}
   * @param keyStroke The {@link KeyStroke} that wasn't handled by the GUI
   * @return {@code true} if at least one of the listeners handled the key stroke, this will signal
   * to the GUI that it needs to be redrawn again.
   */
  boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final KeyStroke keyStroke);
}
