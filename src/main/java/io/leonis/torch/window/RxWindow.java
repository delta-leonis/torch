package io.leonis.torch.window;

import com.googlecode.lanterna.gui2.*;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * The Class RxWindow.
 *
 * A simple {@link Window} accepting a {@link Publisher} providing it's {@link Component}.
 *
 * @author Jeroen de Jong
 */
public final class RxWindow extends BasicWindow {

  /**
   * Create a new RxWindow
   *
   * @param title The title of the window
   * @param publisher The publisher for the components, removing the previous component
   */
  public RxWindow(
      final String title,
      final Publisher<Component> publisher
  ) {
    super(title);
    Flux.from(publisher)
        .subscribeOn(Schedulers.single())
        .subscribe(this::setComponent);
  }
}
