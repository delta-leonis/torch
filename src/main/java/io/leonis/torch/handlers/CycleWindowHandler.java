package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.TextGUI.Listener;
import com.googlecode.lanterna.input.*;

/**
 * @author jeroen.dejong.
 */
public class CycleWindowHandler implements Listener {

  @Override
  public boolean onUnhandledKeyStroke(final TextGUI textGUI, final KeyStroke keyStroke) {
    if (textGUI instanceof WindowBasedTextGUI) {
      if (keyStroke.getKeyType().equals(KeyType.Character) && "[]"
          .contains(keyStroke.getCharacter().toString())) {
        ((WindowBasedTextGUI)textGUI).cycleActiveWindow(keyStroke.getCharacter().equals('['));
        return true;
      }
    }
    return false;
  }
}
