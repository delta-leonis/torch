package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.awt.Color;

/**
 * The Class TextBackground
 *
 * This class is basically an {@link EmptySpace} with a text on top. It will center the text based
 * on the current size of the {@link com.googlecode.lanterna.screen.Screen}.
 *
 * @author Jeroen de Jong
 */
public class TextBackground extends EmptySpace {

  private final Label text;

  public TextBackground(final TextColor background, final String text) {
    super(background);
    this.text = new Label(text);
  }

  @Override
  protected ComponentRenderer<EmptySpace> createDefaultRenderer() {
    return new ComponentRenderer<EmptySpace>() {

      @Override
      public TerminalSize getPreferredSize(final EmptySpace component) {
        return component.getParent().getSize();
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final EmptySpace component) {
        graphics.applyThemeStyle(component.getThemeDefinition().getNormal());
        if(getColor() != null) {
          graphics.setBackgroundColor(getColor());
          text.setBackgroundColor(getColor());
          Color brighterColor = getColor().toColor().brighter();
          text.setForegroundColor(new TextColor.RGB(brighterColor.getRed(), brighterColor.getGreen(), brighterColor.getBlue()));
        }
        graphics.fill(' ');
        // TODO this should be centered
        text.draw(graphics);
      }
    };
  }
}
