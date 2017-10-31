package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.awt.Color;
import java.util.Arrays;

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
        graphics.applyThemeStyle(component.getThemeDefinition().getNormal());
        graphics.setBackgroundColor(component.getColor());
        graphics.fill(' ');
        final Label text = new Label(centerText(component.getParent().getSize(), component.getText()));
        text.setBackgroundColor(component.getColor());
        final Color brighterColor = component.getColor().toColor().brighter();
        text.setForegroundColor(
            new TextColor.RGB(brighterColor.getRed(), brighterColor.getGreen(),
                brighterColor.getBlue()));
        text.draw(graphics);
      }
    };
  }

  /**
   * Centers the given text by adding spaces and new lines based on the given size
   * @param size Size that the text needs to adjust to
   * @param text The text to be centered
   * @return Centered text based on the given size
   */
  private String centerText(TerminalSize size, String text) {
    // height = amount of '\n' + 1
    final int height = (int)text.chars().filter(i -> i == 10 /* 10 happens to be '\n'*/).count() + 1;
    // width = length of largest line
    final int width = Arrays.asList(text.split("\n")).stream()
        .map(String::length)
        .mapToInt(i -> i).max().orElse(0);
    final int x = Math.max(size.getColumns()/2 - height/2,0);
    final int y = Math.max(size.getRows()/2 - width/2, 0);
    // Move all lines x spaces to the right
    return // Move text y lines down
            new String(new char[y]).replace("\0", "\n") +
            // Move top line x spaces to the right
            new String(new char[x]).replace("\0", " ") +
        text.replace("\n", "\n" + new String(new char[x]).replace("\0", " "));
  }
}