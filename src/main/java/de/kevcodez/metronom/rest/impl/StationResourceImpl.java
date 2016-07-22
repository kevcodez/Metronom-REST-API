package de.kevcodez.metronom.rest.impl;

import de.kevcodez.metronom.model.stop.Station;
import de.kevcodez.metronom.model.stop.StationProvider;
import de.kevcodez.metronom.rest.StationResource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implements {@link StationResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class StationResourceImpl implements StationResource {

  @Inject
  private StationProvider stationProvider;

  @Override
  public List<Station> findAll() {
    return stationProvider.getStations();
  }

  @Override
  public Station findByName(String name) {
    return stationProvider.findStationByName(name);
  }

  @Override
  public Station findByCode(String code) {
    return stationProvider.findStationByCode(code);
  }

}
