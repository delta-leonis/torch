package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;

/**
 * The Class CycleWindowHandler
 *
 * Contains logic to cycle the active window by pressing {@code [} or {@code ]}
 *
 * @author Jeroen de Jong
 */
public final class CycleWindowHandler implements MultiWindowListener {

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final KeyStroke keyStroke) {
    if (keyStroke.getKeyType().equals(KeyType.Character) &&
        "[]".contains(keyStroke.getCharacter().toString())) {
      textGUI.cycleActiveWindow(keyStroke.getCharacter().equals('['));
      return true;
    }
    return false;
  }
}
