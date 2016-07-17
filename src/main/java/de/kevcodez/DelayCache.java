package de.kevcodez;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

@Singleton
public class DelayCache {

  private List<Delay> delays;

  public void setDelays(List<Delay> delays) {
    this.delays = delays;
  }

  public List<Delay> getDelays() {
    if (delays == null) {
      delays = new ArrayList<>();
    }

    return delays;
  }

}
