package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import lombok.Value;

import java.awt.Color;

/**
 *
 * @author jeroen.dejong.
 */
@Value
public class ZosmaBackground extends EmptySpace {

  private Label text;

  public ZosmaBackground(String text){
    this(TextColor.ANSI.BLUE, text);
  }

  public ZosmaBackground(TextColor color, String text) {
    super(color);
    this.text = new Label(text);
  }

  @Override
  protected ComponentRenderer<EmptySpace> createDefaultRenderer() {
    return new ComponentRenderer<EmptySpace>() {

      @Override
      public TerminalSize getPreferredSize(EmptySpace component) {
        return component.getParent().getSize();
      }

      @Override
      public void drawComponent(TextGUIGraphics graphics, EmptySpace component) {
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
