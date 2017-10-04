package io.leonis.torch.window;

import com.googlecode.lanterna.gui2.*;
import java.util.Arrays;

/**
 * The Class Modal.
 *
 * @author Jeroen de Jong
 */
public final class Modal extends BasicWindow {

  /**
   * Instantiates a new Basic modal.
   *
   * @param title the title
   */
  public Modal(final String title) {
    this(title, null);
  }

  /**
   * Instantiates a new Basic modal.
   *
   * @param title     the title
   * @param component the component
   */
  public Modal(final String title, final Component component) {
    super(title);
    this.setHints(Arrays.asList(Hint.MODAL, Hint.CENTERED));
    this.setCloseWindowWithEscape(true);
    this.setComponent(component);
  }
}
