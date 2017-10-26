package io.leonis.torch;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import io.leonis.torch.component.TextBackground;
import io.leonis.torch.component.graph.*;
import io.leonis.torch.handlers.*;
import io.leonis.torch.window.RxWindow;
import java.awt.Color;
import java.io.IOException;
import java.time.Duration;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * The Class Torch.
 *
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public final class Torch implements Runnable {

  private final Consumer<WindowBasedTextGUI> drawUnit;
  private final Component background;

  // TODO Remove. Currently here for debugging LineGraph
  public static void main(final String[] args) throws IOException {
    new Thread(new Torch(
        (gui) -> {
          gui.addWindow(new RxWindow("Wat", Flux.interval(Duration.ofMillis(100))
              .map(d -> Math.sin(d * 2 * Math.PI / 20d))
              .buffer(50, 1)
              .map(data -> new LineGraph(LineType.THIN, new Gradient(Color.RED, Color.BLUE),
                  data))));
          gui.addWindow(new BasicWindow("Test 1"));
          gui.addWindow(new BasicWindow("Test 2"));
          gui.addWindow(new BasicWindow("Test 3"));
          gui.addWindowAndWait(new BasicWindow("Test 4"));
        },
        new TextBackground("Background text", TextColor.ANSI.BLUE)
    )).start();
  }

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
      gui.addListener(new CycleWindowHandler());
      gui.addListener(new ClickWindowHandler());
      gui.addListener(new StartDragHandler());
      this.drawUnit.accept(gui);
    } catch (final IOException ioe) {
      throw new RuntimeException("Couldn't start " + ioe);
    }
  }
}
