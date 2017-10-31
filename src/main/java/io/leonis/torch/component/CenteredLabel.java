package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.util.*;
import java.util.stream.IntStream;
import lombok.*;

/**
 * The Class CenteredLabel.
 *
 * Contains the behaviour to place a multiline string in the center of a {@link TextGUIGraphics}.
 *
 * @author Jeroen de Jong
 */
@Value
public class CenteredLabel extends AbstractComponent<CenteredLabel> {
  private final String text;
  private final TextColor textColor;

  @Override
  protected synchronized TerminalSize calculatePreferredSize() {
    return new TerminalSize(
        Collections.max(this.getLines(), Comparator.comparing(String::length)).length(),
        this.getLines().size()
    );
  }

  private List<String> getLines() {
    return Arrays.asList(this.text.split("\r\n|\r|\n"));
  }

  @Override
  protected ComponentRenderer<CenteredLabel> createDefaultRenderer() {
    return new ComponentRenderer<CenteredLabel>() {
      @Override
      public TerminalSize getPreferredSize(final CenteredLabel component) {
        return component.getPreferredSize();
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final CenteredLabel component) {
        graphics.setForegroundColor(component.getTextColor());
        IntStream.range(0, component.getLines().size())
            .forEach(index -> {
              graphics.putString(
                  graphics.getSize().getColumns() / 2 - component.getPreferredSize().getColumns()/2,
                  graphics.getSize().getRows() / 2 - component.getPreferredSize().getRows()/2 + index,
                  component.getLines().get(index)
              );
            });
      }
    };
  }
}
