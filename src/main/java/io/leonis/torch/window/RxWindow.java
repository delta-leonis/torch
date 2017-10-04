package io.leonis.torch.window;

import com.googlecode.lanterna.gui2.*;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author jeroen.dejong.
 */
public final class RxWindow extends BasicWindow {
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
