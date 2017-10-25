package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.KeyStroke;

/**
 * @author jeroen.dejong.
 */
public interface MultiWindowListener extends Listener {

  @Override
  default boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    return textGUI instanceof WindowBasedTextGUI && onUnhandledKeyStroke(
        (MultiWindowTextGUI) textGUI, keyStroke);
  }

  boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final KeyStroke keyStroke);
}
