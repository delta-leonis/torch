package io.leonis.torch.component.graph;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.awt.Color;
import java.util.List;

import java.util.function.Function;
import lombok.*;

/**
 * The {@link Component} LineGraph
 *
 * @author Jeroen de Jong
 * @author Thomas Hakkers
 */
@Value
@AllArgsConstructor
public class LineGraph extends AbstractComponent<LineGraph> {
  private final Function<Double, TextCharacter> lineType;

  private final List<? extends Number> data;

  private final Double min, max;

  public LineGraph(final List<? extends Number> data) {
    this(LineType.THICK, data);
  }

  public LineGraph(final Function<Double, TextCharacter> line, List<? extends Number> data) {
    this(line, data,
        data.stream().mapToDouble(Number::doubleValue).min().orElse(0),
        data.stream().mapToDouble(Number::doubleValue).max().orElse(1));
  }

  @Override
  protected ComponentRenderer<LineGraph> createDefaultRenderer() {
    return new ComponentRenderer<LineGraph>() {
      @Override
      public TerminalSize getPreferredSize(final LineGraph component) {
        return new TerminalSize(component.getData().size(), component.getData().size() / 2);
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final LineGraph component) {
        graphics.drawImage(new TerminalPosition(0, 0),
            new GraphImage(
              component.getPreferredSize().getColumns(),
              component.getPreferredSize().getRows(),
              component.getMin(),
              component.getMax(),
              lineType)
        .apply(component.getData()));
      }
    };
  }
}
