package io.leonis.torch;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import io.leonis.torch.component.TextBackground;
import io.leonis.torch.component.graph.Gradient;
import io.leonis.torch.component.graph.LineGraph;
import io.leonis.torch.component.graph.LineType;
import io.leonis.torch.handlers.CycleWindowHandler;
import io.leonis.torch.handlers.MoveWindowHandler;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The Class Torch.
 *
 * @author Jeroen de Jong
 */
public final class Torch {
  /**
   * Constructs a new window manager.
   */
  public Torch(final Function<WindowBasedTextGUI, WindowBasedTextGUI> GUI) throws IOException {
    final Terminal terminal = new DefaultTerminalFactory(System.out, System.in, Charset.forName("UTF8")).createTerminal();
    Screen screen = new VirtualScreen(new TerminalScreen(terminal));
    screen.startScreen();

    WindowBasedTextGUI textGUI = GUI.apply(
        new MultiWindowTextGUI(
          new SeparateTextGUIThread.Factory(),
          screen,
          new DefaultWindowManager(),
          null,
          new TextBackground(TextColor.ANSI.BLUE, "Background text")));
    textGUI.addListener(new MoveWindowHandler());
    textGUI.addListener(new CycleWindowHandler());
    ((AsynchronousTextGUIThread)textGUI.getGUIThread()).start();
  }

  // TODO Remove. Currently here for debugging LineGraph
  public static void main(String[] args) throws IOException {
    new Torch(
        (gui) -> {
          BasicWindow wat = new BasicWindow("wat");
          wat.setComponent(new LineGraph(LineType.THIN, new Gradient(Color.RED, Color.BLUE),
              IntStream.rangeClosed(1,20).mapToDouble(i -> (double)i)
                  .map(d -> Math.sin(d * 2 * Math.PI / 20d)).boxed().collect(Collectors.toList())));
          gui.addWindow(wat);
          return gui;
        }
    );
  }
}
