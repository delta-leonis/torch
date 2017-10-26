package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.awt.Color;
import lombok.*;

/**
 * The Class TextBackground
 *
 * This class is basically an {@link EmptySpace} with a text on top. The text is a tint brighter
 * than the background color and will be centered based on the current size of the {@link
 * com.googlecode.lanterna.screen.Screen}
 *
 * @author Jeroen de Jong
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
        // TODO this needs some cleanup; also the text should be centered
        graphics.applyThemeStyle(component.getThemeDefinition().getNormal());
        graphics.setBackgroundColor(component.getColor());
        graphics.fill(' ');

        final Label text = new Label(component.getText());
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
