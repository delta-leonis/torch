package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;

import lombok.*;

/**
 * The Class ComponentBackground
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
public final class ComponentBackground extends AbstractComponent<ComponentBackground> {

  /**
   * Background component
   */
  private final Component component;
  private final TextColor backgroundColor;


  private TextColor getForegroundColor() {
    return  new TextColor.RGB(
        this.backgroundColor.toColor().brighter().getRed(),
        this.backgroundColor.toColor().brighter().getGreen(),
        this.backgroundColor.toColor().brighter().getBlue());
  }

  @Override
  protected ComponentRenderer<ComponentBackground> createDefaultRenderer() {
    return new ComponentRenderer<ComponentBackground>() {

      @Override
      public TerminalSize getPreferredSize(final ComponentBackground component) {
        return component.getParent().getSize();
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final ComponentBackground component) {
        graphics.applyThemeStyle(component.getThemeDefinition().getNormal());
        graphics.setBackgroundColor(component.getBackgroundColor());
        graphics.setForegroundColor(component.getForegroundColor());
        graphics.fill(' ');
        component.getComponent().draw(graphics);
      }
    };
  }
}
