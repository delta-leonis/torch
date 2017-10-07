package io.leonis.torch;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.*;

import io.leonis.torch.component.ZosmaBackground;

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
          new ZosmaBackground(TextColor.ANSI.BLUE, "Background text")));
    textGUI.addListener(new MoveWindowBehaviour());
    textGUI.addListener(new CycleWindowBehaviour());
    ((AsynchronousTextGUIThread)textGUI.getGUIThread()).start();
  }
}
