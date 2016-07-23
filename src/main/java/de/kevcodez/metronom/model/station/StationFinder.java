package de.kevcodez.metronom.model.station;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  private static final String PATTERN_WORD = "[a-zA-ZäöüßÄÖÜ]+";

  private static List<Pattern> alertPatterns = new ArrayList<>();

  static {
    alertPatterns.add(Pattern.compile(format("on (?<start>%1$s)(.+)? nach (?<target>%1$s)", PATTERN_WORD)));
    alertPatterns.add(Pattern.compile(format("nach (?<target>%1$s)(.+)ab (?<start>%1$s)", PATTERN_WORD)));
    alertPatterns.add(Pattern.compile(format("Strecke (?<start>%1$s)\\/(?<target>%1$s)", PATTERN_WORD)));
    alertPatterns.add(Pattern.compile(format("zwischen (?<start>%1$s) und (?<target>%1$s)", PATTERN_WORD)));
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
  
    for (Pattern pattern : alertPatterns) {
      Matcher matcher = pattern.matcher(alert);

      if (matcher.find()) {
        String start = matcher.group("start");
        String target = matcher.group("target");

        return findStartAndTarget(start, target);
      }
    }

    return null;
  }

  private StartAndTargetStation findStartAndTarget(String start, String target) {
    if (start != null && target != null) {
      Station startStation = stationProvider.findStationByName(start);
      Station targetStation = stationProvider.findStationByName(target);

      // If the regex pattern matched, but the station provider cannot find any station, log it
      if (startStation == null) {
        LOG.warn("station not found {}", start);
      }

      if (targetStation == null) {
        LOG.warn("station not found {}", target);
      }

      return new StartAndTargetStation(startStation, targetStation);
    }

    return null;
  }

}
