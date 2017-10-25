package io.leonis.torch.component.graph;

import com.googlecode.lanterna.TextCharacter;
import java.util.*;
import java.util.function.Function;

/**
 * The Class LineType
 *
 * Provides a {@link TextCharacter} appropriate for a provided ratio.
 *
 * @author Thomas Hakkers
 */
public enum LineType implements Function<Double, TextCharacter> {
  THICK(
      '█',
      Arrays.asList('▄'),
      Arrays.asList('▀')
  ),
  THIN(
      '|',
      Arrays.asList('.', ':', '¡'),
      Arrays.asList('\'', ':', '!')
  );

  private final Character defaultCharacter;
  private final List<Character> positiveCharacters, negativeCharacters;

  /**
   * Create a new LineType
   *
   * @param defaultCharacter character if the provided {@link Double} == 1
   * @param positiveCharacters list of characters to choose, range from 0-.99
   * @param negativeCharacters list of characters to choose, range from -0.99-0
   */
  LineType(
      final Character defaultCharacter,
      final List<Character> positiveCharacters,
      final List<Character> negativeCharacters
  ) {
    this.defaultCharacter = defaultCharacter;
    this.positiveCharacters = positiveCharacters;
    this.negativeCharacters = negativeCharacters;
  }

  /**
   * Returns the {@link TextCharacter} for the given values.
   *
   * @param ratio the represented value between -1 and 1. (negative characters chosen if value is
   * negative)
   * @return The {@link TextCharacter} that belongs to the given value.
   */
  public TextCharacter apply(final Double ratio) {
    if (ratio % 1 == 0) {
      return new TextCharacter(defaultCharacter);
    }
    return new TextCharacter(
        this.valueToCharacter((ratio < 0) ? negativeCharacters : positiveCharacters, ratio)
    );
  }

  /**
   * Helper to determine character
   *
   * @param characterList list to choose from
   * @param value ratio in list to grab character from
   * @return applicable character
   */
  private Character valueToCharacter(final List<Character> characterList, final double value) {
    return characterList.get((int) (characterList.size() * (Math.abs(value) % 1)));
  }
}
