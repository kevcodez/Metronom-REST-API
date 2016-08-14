package de.kevcodez.metronom.model.station;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finds the start and target stations by a alert in a text form.
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class StationFinder {

  private static final Logger LOG = LoggerFactory.getLogger(StationFinder.class);

  @Inject
  private StationProvider stationProvider;

  private List<Pattern> alertPatterns = new ArrayList<>();

  /**
   * Initializes the station finder by defining the regular expression patterns for finding stations.
   */
  @PostConstruct
  public void init() {
    String stationPattern = buildStationPattern();

    addPattern(format("(on|in|hinter) (?<start>%1$s)(.+)? nach (?<target>%1$s)", stationPattern));
    addPattern(format("nach (?<target>%1$s)(.+)ab (?<start>%1$s)", stationPattern));
    addPattern(format("Strecke (?<start>%1$s)\\/(?<target>%1$s)", stationPattern));
    addPattern(format("zwischen (?<start>%1$s) und (?<target>%1$s)", stationPattern));
    addPattern(format("(Strecke|Streckensperrung) (?<start>%1$s) - (?<target>%1$s)", stationPattern));
    addPattern(format("(ab|on) (?<start>%1$s)(.+)? Richtung (?<target>%1$s)", stationPattern));
    addPattern(format("Bahnhof (?<start>%1$s)(.+)? nach (?<target>%1$s)", stationPattern));
    addPattern(format("Strecke (?<start>%1$s) nach (?<target>%1$s)", stationPattern));

    // Unsafe patterns (may need to be optimized, if they happen to match faulty)
    addPattern(format("(\\d+) (?<start>%1$s)(.+)? nach (?<target>%1$s)", stationPattern));
    addPattern(format("in (?<start>%1$s)(.+)? Richtung (?<target>%1$s)", stationPattern));
    addPattern(format("nach (?<target>%1$s)(.+)? in (?<start>%1$s)", stationPattern));

    // Only start station
    addPattern(format("ab (?<start>%1$s)", stationPattern));
    addPattern(format("von (?<start>%1$s)", stationPattern));
    addPattern(format("Ankunft (.*) in (?<start>%1$s)", stationPattern));
  }

  /**
   * Finds the start and the target station from the given alert.
   * 
   * @param alert alert as text
   * @return start and target station
   */
  public StartAndTargetStation findStartAndTarget(String alert) {
    String alertWithoutAlternativeNames = replaceAlternativeNames(alert);

    for (Pattern pattern : alertPatterns) {
      Matcher matcher = pattern.matcher(alertWithoutAlternativeNames);

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

  private String buildStationPattern() {
    String innerRegex = stationProvider.getStations().stream().map(Station::getName).collect(Collectors.joining("|"));
    return String.format("(%s)", innerRegex);
  }

  private void addPattern(String pattern) {
    alertPatterns.add(Pattern.compile(pattern));
  }

  private String replaceAlternativeNames(String alert) {
    String alertWithoutAlternativeNames = alert;
    for (Station station : stationProvider.getStations()) {
      for (String alternativeName : station.getAlternativeNames()) {
        alertWithoutAlternativeNames = alertWithoutAlternativeNames.replace(alternativeName, station.getName());
      }
    }

    return alertWithoutAlternativeNames;
  }

  private StartAndTargetStation findStartAndTarget(String start, String target, String originalAlert) {
    Station startStation = null;
    Station targetStation = null;
    if (start != null) {
      startStation = stationProvider.findStationByName(start);

      // If the regex pattern matched, but the station provider cannot find any station, log it
      logMissingStation(start, originalAlert, startStation);
    }

    if (target != null) {
      targetStation = stationProvider.findStationByName(target);

      logMissingStation(target, originalAlert, targetStation);
    }

    if (targetStation == null && startStation == null) {
      return null;
    }

    return new StartAndTargetStation(startStation, targetStation);
  }

  private void logMissingStation(String start, String originalAlert, Station station) {
    if (station == null) {
      LOG.warn("station not found {}, original alert {}", start, originalAlert);
    }
  }

}
