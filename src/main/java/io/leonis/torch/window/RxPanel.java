package io.leonis.torch.window;

import com.googlecode.lanterna.gui2.*;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author jeroen.dejong
 */
public final class RxPanel extends Panel {

  public RxPanel() {
    super();
  }

  public RxPanel addComponent(final Publisher<? extends Component> publisher) {
    Flux.from(publisher)
        .subscribeOn(Schedulers.single())
        .transform(componentFlux -> {
          componentFlux.next().subscribe(this::addComponent);
          return componentFlux;
        })
        .buffer(2,1)
        .subscribe(apparentlyNotAnTuple -> {
          this.removeComponent(apparentlyNotAnTuple.get(0));
          this.addComponent(apparentlyNotAnTuple.get(1));
        });
    return this;
  }

  public RxPanel addComponent(final Publisher<? extends Component> publisher, final LayoutData layoutData) {
    return this.addComponent(
        Flux.from(publisher).map(component -> component.setLayoutData(layoutData))
    );
  }
}
