package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import lombok.*;

/**
 * The Class TextBackground
 *
 * This class is basically an {@link EmptySpace} with a text on top. The text is a tint brighter
 * than the background color and will be centered based on the current size of the {@link
 * com.googlecode.lanterna.screen.Screen}
 *
 * @author Jeroen de Jong & Thomas Hakkers
 */
@Value
@EqualsAndHashCode(callSuper = true)
public final class TextBackground extends AbstractComponent<TextBackground> {

  /**
   * Background label
   */
  private final String text;
  private final TextColor color;

  @Override
  protected ComponentRenderer<TextBackground> createDefaultRenderer() {
    return new ComponentRenderer<TextBackground>() {

      @Override
      public TerminalSize getPreferredSize(final TextBackground component) {
        return component.getParent().getSize();
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final TextBackground component) {
        // TODO this needs some cleanup
        graphics.applyThemeStyle(component.getThemeDefinition().getNormal());
        graphics.setBackgroundColor(component.getColor());
        graphics.fill(' ');
        final int height = (int)component.getText().chars().filter(i -> i == 10 /* 10 happens to be '\n'*/).count() + 1;
        final int width = Arrays.asList(component.getText().split("\n")).stream()
            .map(String::length)
            .mapToInt(i -> i).max().orElse(0);
        TerminalSize size = component.getParent().getSize();
        final int x = Math.max(size.getColumns()/2 - height/2,0);
        final int y = Math.max(size.getRows()/2 - width/2, 0);
        final String txt = new String(component.getText())
            .replace("\n", "\n" + new String(new char[x]).replace("\0", " "));
        final Label text = new Label(
            new String(new char[y]).replace("\0", "\n") +
                new String(new char[x]).replace("\0", " ") +
                txt);
        text.setBackgroundColor(component.getColor());
        final Color brighterColor = component.getColor().toColor().brighter();
        text.setForegroundColor(
            new TextColor.RGB(brighterColor.getRed(), brighterColor.getGreen(),
                brighterColor.getBlue()));
        text.draw(graphics);
      }
    };
  }
}