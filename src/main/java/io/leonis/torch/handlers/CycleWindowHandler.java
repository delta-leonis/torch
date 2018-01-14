package io.leonis.torch.handlers;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.*;
import lombok.AllArgsConstructor;

/**
 * The Class CycleWindowHandler.
 *
 * Contains logic to cycle the active window by pressing a provided {@link Character}. If reverse is
 * {@code true} the windows will cycle in reverse order.
 *
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public final class CycleWindowHandler implements MultiWindowListener {
  private final Character character;
  private final boolean reverse;

  @Override
  public boolean onUnhandledKeyStroke(final WindowBasedTextGUI textGUI, final KeyStroke keyStroke) {
    if (keyStroke.getKeyType().equals(KeyType.Character) &&
        keyStroke.getCharacter().equals(this.character)) {
      textGUI.cycleActiveWindow(this.reverse);
      return true;
    }
    return false;
  }
}
