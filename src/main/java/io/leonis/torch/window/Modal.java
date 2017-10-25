package io.leonis.torch.window;

import com.googlecode.lanterna.gui2.*;
import java.util.*;
import lombok.experimental.Delegate;

/**
 * The Class Modal
 *
 * A decorator for a {@link Window} to make it behave as an modal. Simply adding {@link Hint#MODAL}
 * and {@link Hint#CENTERED} as hints and enabling closing with escape.
 *
 * @author Jeroen de Jong
 */
public final class Modal implements Window {

  @Delegate
  private final Window target;

  /**
   * Decorate {@code target} as a Window
   * @param target decoration target
   */
  public Modal(final AbstractWindow target) {
    final Set<Hint> hints = new HashSet<>(target.getHints());
    hints.add(Hint.MODAL);
    hints.add(Hint.CENTERED);
    target.setHints(hints);
    target.setCloseWindowWithEscape(true);
    this.target = target;
  }
}
