package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.provider.StationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST resource that provides stations.
 *
 * @author Kevin Grüneberg
 */
@RequestMapping(value = "station", produces = "application/json")
@RestController
public class StationResource {

    @Autowired
    private StationProvider stationProvider;

    @GetMapping
    public List<Station> findAll() {
        return stationProvider.getStations();
    }

    @GetMapping("name/{name}")
    public Station findByName(@PathVariable(value = "name") String name) {
        return stationProvider.findStationByName(name);
    }

    @GetMapping("code/{code}")
    public Station findByCode(@PathVariable(value = "code") String code) {
        return stationProvider.findStationByCode(code);
    }

}
