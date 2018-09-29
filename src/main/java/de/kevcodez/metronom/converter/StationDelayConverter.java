package de.kevcodez.metronom.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import de.kevcodez.metronom.model.delay.Departure;
import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.provider.StationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts the JSON response from Metronom to a {@link StationDelay}.
 *
 * @author Kevin Grüneberg
 *
 */
@Component
public class StationDelayConverter {

    private static final Logger LOG = LoggerFactory.getLogger(StationDelayConverter.class);

    private static final Pattern PATTERN_TRACK = Pattern.compile("Gleis (\\d+)");

    @Autowired
    private StationProvider stationProvider;

    public StationDelay convert(Station station, JsonNode node) {
        LOG.debug("converting json node {}", node);

        // the content is false if there is an error on the metronom endpoint
        if ("false".equals(node.toString())) {
            return null;
        }

        String timeAsString = node.get("stand").asText();
        LocalTime time = LocalTime.parse(timeAsString);

        StationDelay stationDelay = new StationDelay(station, time);

        JsonNode nodeDeparture = node.get("abfahrt");

        if (nodeDeparture == null) {
            return null;
        }

        if (nodeDeparture.getNodeType() == JsonNodeType.OBJECT) {
            // Parse single object
            Departure departure = parseDeparture(nodeDeparture);

            stationDelay.addDeparture(departure);
        } else {
            // Parse JSON array
            for (JsonNode singleDeparture : nodeDeparture) {
                Departure departure = parseDeparture(singleDeparture);

                stationDelay.addDeparture(departure);
            }
        }

        return stationDelay;
    }

    private Departure parseDeparture(JsonNode singleDeparture) {
        String time = singleDeparture.get("zeit").asText();
        String train = singleDeparture.get("zug").asText();
        String targetStationName = singleDeparture.get("ziel").asText();
        int delayInMinutes = singleDeparture.get("prognosemin").asInt();


        Station targetStation = stationProvider.findStationByName(targetStationName);
        Departure departure = new Departure(train, targetStation, LocalTime.parse(time), delayInMinutes);

        String prognose = singleDeparture.get("prognose").asText();

        Matcher matcher = PATTERN_TRACK.matcher(prognose);
        if (matcher.find()) {
            departure.setTrack(matcher.group(1));
        }

        if (prognose.toLowerCase().contains("fällt")) {
            departure.setCancelled(true);
        }

        return departure;
    }

}
