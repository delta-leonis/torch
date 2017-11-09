package io.leonis.torch.window;

import com.googlecode.lanterna.gui2.*;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * The Class RxPanel.
 *
 * An wrapper for {@link Panel} which adds the functionality to add {@link Publisher} as
 * components to the panel.
 *
 * @author Jeroen de Jong
 */
public final class RxPanel extends Panel {

  /**
   * Add components from the publisher to the panel, removing the previous component on next.
   *
   * @param publisher component publisher
   * @return panel with added component
   */
  public RxPanel addComponent(final Publisher<? extends Component> publisher) {
    Flux.from(publisher)
        .subscribeOn(Schedulers.single())
        .transform(componentFlux -> {
          componentFlux.next().subscribe(this::addComponent);
          return componentFlux;
        })
        .buffer(2, 1)
        .subscribe(apparentlyNotAnTuple -> {
          this.removeComponent(apparentlyNotAnTuple.get(0));
          this.addComponent(apparentlyNotAnTuple.get(1));
        });
    return this;
  }

  /**
   * Add component from the publisher to the panel containing specific {@link LayoutData}, removing
   * the previous component on next
   *
   * @param publisher component publisher
   * @param layoutData layout data for the component
   * @return panel with added component
   */
  public RxPanel addComponent(final Publisher<? extends Component> publisher,
      final LayoutData layoutData) {
    return this.addComponent(
        Flux.from(publisher).map(component -> component.setLayoutData(layoutData))
    );
  }
}
