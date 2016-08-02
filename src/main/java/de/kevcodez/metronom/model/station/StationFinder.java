package de.kevcodez.metronom.model.station;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finds the start and target stations by a alert in a text form.
 * 
 * @author Kevin Grüneberg
 *
 */
public class StationFinder {

  private static final Logger LOG = LoggerFactory.getLogger(StationFinder.class);

  private static final String PATTERN_WORD = "[a-zA-ZäöüßÄÖÜ-]+";

  private static List<Pattern> alertPatterns = new ArrayList<>();

  static {
    addPattern(format("nach (?<target>%1$s)(.+)ab (?<start>%1$s)", PATTERN_WORD));
    addPattern(format("Strecke (?<start>%1$s)\\/(?<target>%1$s)", PATTERN_WORD));
    addPattern(format("zwischen (?<start>%1$s) und (?<target>%1$s)", PATTERN_WORD));
    addPattern(format("Strecke (?<start>%1$s) - (?<target>%1$s)", PATTERN_WORD));
    addPattern(format("(on|in|hinter) (?<start>(?!ME)%1$s)(.+)? nach (?<target>%1$s)", PATTERN_WORD));
    addPattern(format("(ab|on) (?<start>(?!ME)%1$s)(.+)? Richtung (?<target>%1$s)", PATTERN_WORD));
    addPattern(format("Bahnhof (?<start>%1$s)(.+)? nach (?<target>%1$s)", PATTERN_WORD));

    // Unsafe patterns (may need to be optimized, if they happen to match faulty)
    addPattern(format("in (?<start>%1$s)(.+)? Richtung (?<target>%1$s)", PATTERN_WORD));
    addPattern(format("nach (?<target>%1$s)(.+)? in (?<start>%1$s)", PATTERN_WORD));

    // Only start station
    addPattern(format("ab (?<start>%1$s)", PATTERN_WORD));
    addPattern(format("von (?<start>(?!ME)%1$s)", PATTERN_WORD));
    addPattern(format("Ankunft (.*) in (?<start>(?!ME)%1$s)", PATTERN_WORD));
  }

  @Inject
  private StationProvider stationProvider;

  /**
   * Finds the start and the target station from the given alert.
   * 
   * @param alert alert as text
   * @return start and target station
   */
  public StartAndTargetStation findStartAndTarget(String alert) {
    String alertNoSpacesInNames = replaceSpacesInStationNames(alert);

    for (Pattern pattern : alertPatterns) {
      Matcher matcher = pattern.matcher(alertNoSpacesInNames);

      if (matcher.find()) {
        String start = matcher.group("start");

        String target = null;

        try {
          target = matcher.group("target");
        } catch (IllegalArgumentException exc) {
          // Ignore, target station is not always available
        }

        return findStartAndTarget(start, target, alert);
      }
    }

    return null;
  }

  private static void addPattern(String pattern) {
    alertPatterns.add(Pattern.compile(pattern));
  }

  private String replaceSpacesInStationNames(String alert) {
    List<Station> stations = stationProvider.getStations();

    String replacedSpaces = alert;

    for (Station station : stations) {
      List<String> namesWithSpace = station.getAlternativeNames().stream().filter(name -> name.contains(" "))
        .collect(Collectors.toList());

      for (String name : namesWithSpace) {
        replacedSpaces = replacedSpaces.replace(name, station.getName());
      }
    }

    return replacedSpaces;
  }

  private StartAndTargetStation findStartAndTarget(String start, String target, String originalAlert) {
    Station startStation = null;
    Station targetStation = null;
    if (start != null) {
      startStation = stationProvider.findStationByName(start);

      // If the regex pattern matched, but the station provider cannot find any station, log it
      if (startStation == null) {
        LOG.warn("station not found {}, original alert {}", start, originalAlert);
      }
    }

    if (target != null) {
      targetStation = stationProvider.findStationByName(target);

      if (targetStation == null) {
        LOG.warn("station not found {}, original alert {}", target, originalAlert);
      }
    }

    if (targetStation == null && startStation == null) {
      return null;
    }

    return new StartAndTargetStation(startStation, targetStation);
  }

}
