package de.kevcodez.metronom.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kevcodez.metronom.converter.StationDelayConverter;
import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.provider.StationProvider;
import de.kevcodez.metronom.utility.Exceptions;
import de.kevcodez.metronom.utility.WebsiteSourceDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static de.kevcodez.metronom.utility.WebsiteSourceDownloader.getCookiesFromUrl;

/**
 * Parses the station delays from the Metronom station information endpoint.
 *
 * @author Kevin Grüneberg
 */
@Component
public class StationDelayParser {

    private static final String BASE_URL = "https://www.der-metronom.de/livedata/etc?type=stationsauskunft";

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private StationProvider stationProvider;

    @Autowired
    private StationDelayConverter stationDelayConverter;

    @Autowired
    private WebsiteSourceDownloader websiteSourceDownloader;

    public StationDelay findDelays(String stationName) {
        Station station = stationProvider.findStationByName(stationName);

        return findDelays(station);
    }

    /**
     * Finds the delay for the given station.<br>
     * To avoid getting an access denied exception when retrieving the station delays, we have to get the cookies from any
     * other URL first. We retrieve a CSRF token and a CraftSessionID that are used for authentication.
     *
     * @param station station to search for
     * @return station delay
     */
    public StationDelay findDelays(Station station) {
        if (station == null) {
            return null;
        }

        String uri = buildStationDelayUri(station);
        // Get cookies from any URL to avoid access denied exception
        Map<String, String> cookies = getCookiesFromUrl("https://www.der-metronom.de/ueber-metronom/wer-wir-sind");
        String result = websiteSourceDownloader.getSource(uri, cookies);

        try {
            JsonNode jsonNode = objectMapper.readTree(result);

            return stationDelayConverter.convert(station, jsonNode);
        } catch (IOException exc) {
            throw Exceptions.unchecked(exc);
        }
    }

    private String buildStationDelayUri(Station station) {
        return BASE_URL + "&bhf=" + station.getCode();
    }

}
