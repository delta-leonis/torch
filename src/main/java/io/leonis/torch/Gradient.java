package io.leonis.torch;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.RGB;
import java.awt.Color;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Describes the gradient between one or multiple colors.
 *
 * @author Thomas Hakkers
 */
public final class Gradient implements Function<Double, TextColor.RGB> {
  private final List<Color> colors;

  public Gradient(Color... colors) {
    this.colors = Arrays.asList(colors);
  }

  /**
   * Controls what color a value on the graph should have.
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

  private int ratioForChannel(final Function<? super Color, Integer> channel, final Double ratio) {
    return (int) ((1 - ratio) * valuesForChannel(channel).min().orElse(0) + ratio * valuesForChannel(channel).max().orElse(0));
  }

  private IntStream valuesForChannel(final Function<? super Color, Integer> grab_em_by_the) {
    return colors.stream().mapToInt(grab_em_by_the::apply);
  }
}
