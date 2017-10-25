package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;

/**
 * @author Jeroen de Jong
 */
public class CycleWindowHandler implements MultiWindowListener {

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final KeyStroke keyStroke) {
    if (keyStroke.getKeyType().equals(KeyType.Character) && "[]"
        .contains(keyStroke.getCharacter().toString())) {
      textGUI.cycleActiveWindow(keyStroke.getCharacter().equals('['));
      return true;
    }
    return false;
  }
}
