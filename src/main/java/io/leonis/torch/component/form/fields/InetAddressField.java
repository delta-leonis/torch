package io.leonis.torch.component.form.fields;

import com.googlecode.lanterna.gui2.TextBox;
import java.net.InetAddress;
import java.util.function.Function;
import java.util.regex.Pattern;
import io.leonis.torch.component.form.FormElement;

/**
 * The Class InetAddressField.
 *
 * @author Jeroen de Jong
 */
public class InetAddressField extends TextBox implements FormElement<InetAddress> {

  private static final String IPADDRESS_PATTERN =
      "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
          "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
          "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
          "([01]?\\d\\d?|2[0-4]\\d|25[0-5])";

  /**
   * Instantiates a new Inet address field.
   */
  public InetAddressField() {
    super();
    this.setValidationPattern(Pattern.compile(IPADDRESS_PATTERN));
  }

  @Override
  public InetAddress getValue() {
    return getParser().apply(this.getText());
  }

  @Override
  public InetAddressField setValue(final InetAddress value) {
    this.setText(value.getHostAddress());
    return this;
  }

  @Override
  public Function<String, InetAddress> getParser() {
    return (string) -> {
      try {
        return InetAddress.getByName(string);
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    };
  }
}
