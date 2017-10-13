package io.leonis.torch;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import io.leonis.torch.component.TextBackground;
import io.leonis.torch.component.graph.*;
import io.leonis.torch.handlers.*;
import java.awt.Color;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.*;
import lombok.AllArgsConstructor;

/**
 * The Class Torch.
 *
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public final class Torch implements Runnable {

  private final Consumer<WindowBasedTextGUI> drawUnit;
  private final EmptySpace background;

  // TODO Remove. Currently here for debugging LineGraph
  public static void main(String[] args) throws IOException {
    new Thread(new Torch(
        (gui) -> {
          BasicWindow wat = new BasicWindow("wat");
          wat.setComponent(new LineGraph(LineType.THIN, new Gradient(Color.RED, Color.BLUE),
              IntStream.rangeClosed(1, 20).mapToDouble(i -> (double) i)
                  .map(d -> Math.sin(d * 2 * Math.PI / 20d)).boxed().collect(Collectors.toList())));
          gui.addWindowAndWait(wat);
        },
        new TextBackground(TextColor.ANSI.BLUE, "Background text")
    )).start();
  }

  @Override
  public void run() {
    try {
      Screen screen = new VirtualScreen(
                        new TerminalScreen(
                            new DefaultTerminalFactory()
                                .setMouseCaptureMode(MouseCaptureMode.CLICK_RELEASE)
                                .createTerminal()));
      screen.startScreen();
      WindowBasedTextGUI gui = new MultiWindowTextGUI(screen,new DefaultWindowManager(),background);
      gui.addListener(new MoveWindowHandler());
      gui.addListener(new CycleWindowHandler());
      drawUnit.accept(gui);
    } catch (IOException ioe) {
      throw new RuntimeException("Couldn't start " + ioe);
    }
  }
}
