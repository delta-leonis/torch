package io.leonis.torch.component.graph;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.gui2.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import lombok.*;

/**
 * The {@link Component} LineGraph
 *
 * @author Jeroen de Jong
 * @author Thomas Hakkers
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LineGraph extends AbstractComponent<LineGraph> {

  private final Function<Double, TextCharacter> line;
  private final Function<Double, TextColor.RGB> lineColor;
  private final List<Double> data;

  public LineGraph(final List<Double> data) {
    this(LineType.THICK, ignored -> new TextColor.RGB(0, 0, 255), data);
  }

  // TODO might need to be moved to a data container or smth?
  private double getMax() {
    return data.stream().mapToDouble(Number::doubleValue).max().orElse(1);
  }

  private double getMin() {
    return data.stream().mapToDouble(Number::doubleValue).min().orElse(0);
  }

  @Override
  protected ComponentRenderer<LineGraph> createDefaultRenderer() {
    return new ComponentRenderer<LineGraph>() {
      @Override
      public TerminalSize getPreferredSize(final LineGraph component) {
        return new TerminalSize(getColumns(), getRows());
      }

      private int getRows() {
        return getData().size() / 2;
      }

      private int getColumns() {
        return getData().size();
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final LineGraph component) {
        final BasicTextImage image = new BasicTextImage(getColumns(), getRows());

        final int startingColumn = Math.max(0, data.size() - getColumns());
        // Keep xAxisRow within range [0, rows]
        final int xAxisRow = Math.max(
            0,
            Math.min((int) this.computeColumn(0), getRows()));

        IntStream.range(startingColumn, data.size())
            .forEach(currentColumn -> {
              Double number = data.get(currentColumn);
              final double rowValue = this.computeColumn(number);
              if (number > 0d) {
                IntStream.rangeClosed(xAxisRow, (int) Math.ceil(rowValue))
                    .forEach(row -> {
                      double ratio = ((double) getRows() - row) / ((double) getRows() - 0);
                      image.setCharacterAt(
                          currentColumn, getRows() - row,
                          getLine().apply(row > rowValue ? rowValue : row)
                              .withForegroundColor(lineColor.apply(ratio)));
                    });
              } else {
                IntStream.rangeClosed((int) Math.floor(rowValue), xAxisRow)
                    .forEach(row -> {
                      double ratio = ((double) getRows() - row) / ((double) getRows() - 0);
                      image.setCharacterAt(
                          currentColumn, getRows() - row,
                          getLine().apply(row > rowValue ? row : -rowValue)
                              .withForegroundColor(lineColor.apply(ratio)));
                    });
              }
            });
        graphics.drawImage(new TerminalPosition(0, 0), image);
      }

      private double computeColumn(final Number value) {
        return getRows() * ((value.doubleValue() - getMin())
            / (getMax() - getMin()));
      }
    };
  }
}
