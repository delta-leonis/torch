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
    return this.data.stream().mapToDouble(Number::doubleValue).max().orElse(1);
  }

  private double getMin() {
    return this.data.stream().mapToDouble(Number::doubleValue).min().orElse(0);
  }

  @Override
  protected ComponentRenderer<LineGraph> createDefaultRenderer() {
    return new ComponentRenderer<LineGraph>() {
      @Override
      public TerminalSize getPreferredSize(final LineGraph component) {
        return new TerminalSize(this.getColumns(), this.getRows());
      }

      private int getRows() {
        return LineGraph.this.getData().size() / 2;
      }

      private int getColumns() {
        return LineGraph.this.getData().size();
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final LineGraph component) {
        final BasicTextImage image = new BasicTextImage(this.getColumns(), this.getRows());

        final int startingColumn = Math.max(0, LineGraph.this.data.size() - this.getColumns());
        // Keep xAxisRow within range [0, rows]
        final int xAxisRow = Math.max(
            0,
            Math.min((int) this.computeColumn(0), this.getRows()));

        IntStream.range(startingColumn, LineGraph.this.data.size())
            .forEach(currentColumn -> {
              final Double number = LineGraph.this.data.get(currentColumn);
              final double rowValue = this.computeColumn(number);
              if (number > 0d) {
                IntStream.rangeClosed(xAxisRow, (int) Math.ceil(rowValue))
                    .forEach(row -> {
                      final double ratio = ((double) this.getRows() - row) / ((double) this.getRows() - 0);
                      image.setCharacterAt(
                          currentColumn, this.getRows() - row,
                          LineGraph.this.getLine().apply(row > rowValue ? rowValue : row)
                              .withForegroundColor(LineGraph.this.lineColor.apply(ratio)));
                    });
              } else {
                IntStream.rangeClosed((int) Math.floor(rowValue), xAxisRow)
                    .forEach(row -> {
                      final double ratio = ((double) this.getRows() - row) / ((double) this.getRows() - 0);
                      image.setCharacterAt(
                          currentColumn, this.getRows() - row,
                          LineGraph.this.getLine().apply(row > rowValue ? row : -rowValue)
                              .withForegroundColor(LineGraph.this.lineColor.apply(ratio)));
                    });
              }
            });
        graphics.drawImage(new TerminalPosition(0, 0), image);
      }

      private double computeColumn(final Number value) {
        return this.getRows() * ((value.doubleValue() - LineGraph.this.getMin())
            / (LineGraph.this.getMax() - LineGraph.this.getMin()));
      }
    };
  }
}
