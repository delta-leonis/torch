package io.leonis.torch;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import io.leonis.torch.handlers.*;
import java.io.IOException;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;

/**
 * The Class Torch.
 *
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public final class Torch implements Runnable {

  private final Consumer<WindowBasedTextGUI> drawFunction;
  private final Component background;

  @Override
  public void run() {
    try {
      final Screen screen = new VirtualScreen(
          new TerminalScreen(
              new DefaultTerminalFactory()
                  .setMouseCaptureMode(MouseCaptureMode.CLICK_RELEASE_DRAG)
                  .createTerminal()));
      screen.startScreen();
      final WindowBasedTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
          this.background);
      gui.addListener(new MoveWindowHandler());
      gui.addListener(new CycleWindowHandler('[', false));
      gui.addListener(new CycleWindowHandler(']', true));
      gui.addListener(new ClickWindowHandler());
      gui.addListener(new StartDragHandler());
      this.drawFunction.accept(gui);
    } catch (final IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
