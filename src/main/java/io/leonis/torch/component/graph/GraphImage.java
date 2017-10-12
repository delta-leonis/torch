package io.leonis.torch.component.graph;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;

/**
 * The class GraphImage
 *
 * Provides {@link BasicTextImage} that represents a graph based on a list of data.
 *
 * @author Thomas Hakkers
 * @since 7-5-17
 */
@AllArgsConstructor
public final class GraphImage implements Supplier<TextImage> {

  private final int columns;
  private final int rows;
  private final Double minimumY;
  private final Double maximumY;
  private final List<Double> data;
  private final Function<Double, TextCharacter> lineSupplier;
  private final Function<Double, TextColor.RGB> lineColor;

  @Override
  public TextImage get() {
    final BasicTextImage image = new BasicTextImage(this.columns, this.rows);

    final int startingColumn = Math.max(0, data.size() - columns);
    // Keep xAxisRow within range [0, rows]
    final int xAxisRow = Math.max(
        0,
        Math.min((int) this.computeColumn(0), this.rows));

    IntStream.range(startingColumn, data.size())
        .forEach(currentColumn -> {
          Double number = data.get(currentColumn);
          final double rowValue = this.computeColumn(number);
          if (number > 0d) {
            IntStream.rangeClosed(xAxisRow, (int) Math.ceil(rowValue))
                .forEach(row -> {
                  double ratio = ((double) rows - row) / ((double) rows - 0);
                  image.setCharacterAt(
                      currentColumn, rows - row,
                      lineSupplier.apply(row > rowValue ? rowValue : row)
                          .withForegroundColor(lineColor.apply(ratio)));
                });
          } else {
            IntStream.rangeClosed((int) Math.floor(rowValue), xAxisRow)
                .forEach(row -> {
                  double ratio = ((double) rows - row) / ((double) rows - 0);
                  image.setCharacterAt(
                      currentColumn, rows - row,
                      lineSupplier.apply(row > rowValue ? row : -rowValue)
                          .withForegroundColor(lineColor.apply(ratio)));
                });
          }
        });

    return image;
  }

  private double computeColumn(final Number value) {
    return this.rows * ((value.doubleValue() - this.minimumY)
        / (this.maximumY - this.minimumY));
  }
}