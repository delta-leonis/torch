package io.leonis.torch.component.graph;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.RGB;
import java.awt.Color;
import java.util.function.Function;
import lombok.AllArgsConstructor;

/**
 * The Class Gradient
 *
 * Provides an {@link TextColor.RGB} between two provided color at a given ratio.
 *
 * @author Thomas Hakkers
 */
@AllArgsConstructor
public final class Gradient implements Function<Double, TextColor.RGB> {

  private final Color minColor, maxColor;

  /**
   * Controls what color a value on the graph should have.
   *
   * @param ratio ratio between 0-1
   * @return The {@link TextColor} for the specified value.
   */
  public TextColor.RGB apply(final Double ratio) {
    return new RGB(
        ratioForChannel(Color::getRed, ratio),
        ratioForChannel(Color::getGreen, ratio),
        ratioForChannel(Color::getBlue, ratio)
    );
  }

  /**
   * Calculate the ratio for a given color channel
   * @param channel channel to determine color for
   * @param ratio ratio between 0-1
   * @return color value between 0-255
   */
  private int ratioForChannel(final Function<? super Color, Integer> channel, final Double ratio) {
    return (int) ((1 - ratio) * channel.apply(minColor) + ratio * channel.apply(maxColor));
  }
}
