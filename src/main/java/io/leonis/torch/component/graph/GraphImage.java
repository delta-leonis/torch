package io.leonis.torch.component.graph;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import io.leonis.torch.Gradient;
import lombok.Value;

/**
 * A {@link BasicTextImage} that represents a graph based on a list of data.
 *
 * @author Thomas Hakkers
 * @since 7-5-17
 * //TODO Ensure amount of workingness is above 0
 */
@Value
public final class GraphImage implements Function<List<? extends Number>, TextImage> {
  private final int columns;
  private final int rows;
  private final Double minimumY;
  private final Double maximumY;
  private final LineType lineType;
  private final Gradient gradient;

  @Override
  public TextImage apply(final List<? extends Number> data) {
    final BasicTextImage image = new BasicTextImage(this.columns, this.rows);
    image.setAll(new TextCharacter(' '));

    final int startingColumn = Math.max(0, data.size() - columns);
    // Keep xAxisRow within range [0, rows]
    final int xAxisRow = Math.max(
        0,
        Math.min(this.computeColumn(0), this.rows));

    IntStream.range(startingColumn, data.size())
        .forEach(currentColumn -> {
          final int rowValue = this.computeColumn(data.get(currentColumn));
          IntStream.rangeClosed(this.computeRow(rowValue, xAxisRow, true),
              this.computeRow(rowValue, xAxisRow, false))
              .map(currentRow ->
                  rowValue > xAxisRow
                      ? this.computeRow(currentRow, rowValue, true)
                      : this.computeRow(currentRow, rowValue, false))
              .forEach(row ->
                  image.setCharacterAt(
                      currentColumn - startingColumn, rows - row,
                      lineType.getCharacter(((double)rows - row) / ((double)rows - 0))));
        });

    return image;
  }

  private int computeRow(final int currentRow, final int rowValue, final boolean positive) {
    if (positive)
      return currentRow > rowValue ? rowValue : currentRow;
    else
      return currentRow > rowValue ? currentRow : rowValue;
  }

  private int computeColumn(final Number value) {
    return this.rows * ((int) ((value.doubleValue() - this.minimumY)
        / (this.maximumY - this.minimumY)));
  }
}