package io.leonis.torch.component.graph;

import com.googlecode.lanterna.TextCharacter;
import io.leonis.torch.Gradient;
import java.util.function.Function;
import lombok.AllArgsConstructor;

/**
 * The Class GradientLine
 *
 * Transforms a ratio (0-1) to a character with foreground color based on the provided
 * {@link LineType} and {@link Gradient}.
 *
 * @author jeroen.dejong.
 */
@AllArgsConstructor
public class GradientLine implements Function<Double, TextCharacter> {
  private final Gradient gradient;
  private final LineType line;

  @Override
  public TextCharacter apply(final Double ratio) {
    return line.apply(ratio).withForegroundColor(gradient.apply(ratio));
  }
}
