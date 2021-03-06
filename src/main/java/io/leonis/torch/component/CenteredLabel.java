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
@AllArgsConstructor
public final class CenteredLabel extends AbstractComponent<CenteredLabel> {
  private final String text;

  @Override
  protected synchronized TerminalSize calculatePreferredSize() {
    return new TerminalSize(
        this.getLines().stream().mapToInt(String::length).max().orElse(0),
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
        IntStream.range(0, component.getLines().size())
            .forEach(index -> graphics.putString(
                (graphics.getSize().getColumns() - component.getPreferredSize().getColumns()) / 2,
                (graphics.getSize().getRows() - component.getPreferredSize().getRows()) / 2 + index,
                component.getLines().get(index)
            ));
      }
    };
  }
}
