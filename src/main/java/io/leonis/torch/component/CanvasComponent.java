package io.leonis.torch.component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;

/**
 * The class CanvasComponent.
 *
 * Draws a rectangle of a specified size using a provided {@link BiFunction backgroundSupplier} to
 * determine the color at each coordinate.
 *
 * @author Jeroen de Jong
 */
@AllArgsConstructor
public class CanvasComponent extends AbstractComponent<CanvasComponent> {

  /**
   * Number of columns and rows of the canvas.
   */
  private final int columns, rows;

  /**
   * Function to determine background color at a specific position.
   */
  private final BiFunction<Integer, Integer, TextColor> backgroundSupplier;

  @Override
  protected ComponentRenderer<CanvasComponent> createDefaultRenderer() {
    return new ComponentRenderer<CanvasComponent>() {
      @Override
      public TerminalSize getPreferredSize(final CanvasComponent component) {
        return new TerminalSize(columns, rows);
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final CanvasComponent component) {
        IntStream.range(0, columns).forEach(column ->
            IntStream.range(0, rows).forEach(row -> {
              graphics.setBackgroundColor(backgroundSupplier.apply(column, row));
              graphics.setCharacter(column, row, ' ');
            }));
      }
    };
  }
}
