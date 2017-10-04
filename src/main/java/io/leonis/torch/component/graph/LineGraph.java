package io.leonis.torch.component.graph;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.awt.Color;
import java.util.List;

import io.leonis.torch.Gradient;
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
  private final LineType lineType;
  private final Gradient gradient;

  private final List<? extends Number> data;

  private final Double min, max;

  public LineGraph(final List<? extends Number> data) {
    this(LineType.THICK, new Gradient(Color.BLACK, Color.BLACK), data);
  }

  public LineGraph(final LineType line, final Gradient gradient, List<? extends Number> data) {
    this(line, gradient, data,
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
              component.getPreferredSize().getRows(), getMin(), getMax(), lineType, gradient)
        .apply(component.getData()));
      }
    };
  }
}
