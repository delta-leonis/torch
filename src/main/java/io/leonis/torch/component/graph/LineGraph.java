package io.leonis.torch.component.graph;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;
import java.util.function.Function;

/**
 * The {@link Component} LineGraph
 *
 * @author Jeroen de Jong
 * @author Thomas Hakkers
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LineGraph extends AbstractComponent<LineGraph> {
  private final Function<Double, TextCharacter> lineType;
  private final Function<Double, TextColor.RGB> lineColor;

  private final List<Double> data;

  private final Double min, max;

  public LineGraph(final List<Double> data) {
    this(LineType.THICK, ignored -> new TextColor.RGB(0,0,255), data);
  }

  public LineGraph(final Function<Double, TextCharacter> line, final Function<Double, TextColor.RGB> lineColor, List<Double> data) {
    this(line, lineColor, data,
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
              component.getData(),
              lineType, lineColor).get());
      }
    };
  }
}
