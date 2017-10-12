package io.leonis.torch.window;

import com.googlecode.lanterna.gui2.Window;
import java.util.*;
import lombok.experimental.Delegate;

/**
 * The Class Modal.
 *
 * @author Jeroen de Jong
 */
public final class Modal implements Window {

  @Delegate
  private final Window target;

  public Modal(Window target) {
    Set<Hint> hints = new HashSet<>(target.getHints());
    hints.add(Hint.MODAL);
    hints.add(Hint.CENTERED);
    target.setHints(hints);
    this.target = target;
  }

  public boolean getCloseWindowWithEscape() {
    return true;
  }
}
