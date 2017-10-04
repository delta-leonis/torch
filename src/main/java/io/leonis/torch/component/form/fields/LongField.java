package io.leonis.torch.component.form.fields;

/**
 * The Class LongField.
 *
 * @author Jeroen de Jong
 */
public class LongField extends NumberField<Long> {

  /**
   * Instantiates a new Long field.
   */
  public LongField() {
    super(Long::valueOf);
  }
}
