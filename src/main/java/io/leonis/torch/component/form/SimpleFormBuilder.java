package io.leonis.torch.component.form;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

/**
 * The Class SimpleFormBuilder.
 *
 * @author Jeroen de Jong
 */
@Slf4j
public class SimpleFormBuilder implements FormBuilder {

  private Map<Class<?>, Class<? extends FormElement>> supportedTypes;
  private Map<String, FormElement<?>> elements;
  private Map<String, Consumer<List<FormElement<?>>>> buttons;

  /**
   * Instantiates a new Simple form builder.
   */
  public SimpleFormBuilder() {
    this.buttons = new LinkedHashMap<>();
    this.elements = new LinkedHashMap<>();
    this.supportedTypes = new Reflections(this.getClass().getPackage().getName() + ".fields")
        .getSubTypesOf(FormElement.class).stream()
        .filter(aClass -> !Modifier.isAbstract(aClass.getModifiers()))
        .filter(aClass -> {
          try { aClass.getDeclaredConstructor(); return true;
          } catch(Exception e) { return false; } })
        .collect(Collectors.toMap(
            SimpleFormBuilder::getGenericType,
            Function.identity()));
  }

  private static Class<?> getGenericType(Class clazz) {
    // Catch the first superclass with an generic
    Type type = clazz.getGenericSuperclass();
    // if the superclass is equal, the superclass didn't have an generic
    if (type == clazz.getSuperclass()) {
    // thus grab the first Generic interface (FormElement)
    // Note: In the future we may need to search through the list to find FormElement
      type = clazz.getGenericInterfaces()[0];
    }
    // and grab the first actual type argument
    return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];

  }

  @Override
  public boolean isSupported(final Class<?> type) {
    return this.supportedTypes.containsKey(type);
  }

  @Override
  public <T> FormElement<T> createFormElement(final Class<T> type) throws NoSuchFormElementException {
    try {
      return (FormElement<T>) this.supportedTypes.get(type).newInstance();
    } catch (final IllegalAccessException | InstantiationException e) {
      throw new NoSuchFormElementException(e);
    }
  }

  @Override
  public <T> SimpleFormBuilder addFormElement(final String label, final FormElement<T> element) {
    this.elements.put(label, element);
    return this;
  }

  @Override
  public SimpleFormBuilder addButton(final String label, final Consumer<List<FormElement<?>>> onSelect) {
    this.buttons.put(label, onSelect);
    return this;
  }

  @Override
  public Form build() {
    return Form.of(this.elements, this.buttons);
  }
}
