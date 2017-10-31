package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import com.googlecode.lanterna.gui2.*;
import java.awt.Color;
import java.util.*;

import java.util.stream.Stream;
import lombok.*;

/**
 * The Class TextBackground
 *
 * This class is basically an {@link EmptySpace} with a text on top. The text is a tint brighter
 * than the background color and will be centered based on the current size of the {@link
 * com.googlecode.lanterna.screen.Screen}
 *
 * @author Jeroen de Jong
 * @author Thomas Hakkers
 */
@Value
@EqualsAndHashCode(callSuper = true)
public final class TextBackground extends AbstractComponent<TextBackground> {

  /**
   * Background label
   */
  private final String text;
  private final TextColor backgroundColor;


  private TextColor getTextColor() {
    return  new TextColor.RGB(
        this.backgroundColor.toColor().brighter().getRed(),
        this.backgroundColor.toColor().brighter().getGreen(),
        this.backgroundColor.toColor().brighter().getBlue());
  }

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
        graphics.setBackgroundColor(component.getBackgroundColor());
        graphics.fill(' ');
        new CenteredLabel(component.getText(), component.getTextColor()).draw(graphics);
      }
    };
  }
}
