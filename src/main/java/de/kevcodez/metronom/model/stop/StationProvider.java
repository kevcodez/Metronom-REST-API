package de.kevcodez.metronom.model.stop;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Provides all stations statically.
 * 
 * @author Kevin Grüneberg
 *
 */
@Startup
@Singleton
public class StationProvider {

  private List<Station> stations = new ArrayList<>();

  @PostConstruct
  public void constructStations() {
    addStation("Hamburg Hbf", "AH");
    addStation("Hamburg-Harburg", "AHAR");
    addStation("Meckelfeld", "AMDH");
    addStation("Maschen", "AMA");
    addStation("Stelle", "ASTE");
    addStation("Ashausen", "AASN");
    addStation("Winsen (Luhe)", "AWI");
    addStation("Radbruch", "ARH");
    addStation("Bardowick", "ABAD");
    addStation("Lüneburg", "ALBG");
    addStation("Bienenbüttel", "ABIL");
    addStation("Bad Bevensen", "ABVS");
    addStation("Uelzen", "HU");
    addStation("Hannover Hbf", "HH");
    addStation("Sarstedt", "HSRD");
    addStation("Nordstemmen", "HNOS");
    addStation("Elze (Han)", "HELZ");
    addStation("Banteln", "HBAN");
    addStation("Alfeld (Leine)", "HALF");
    addStation("Freden (Leine)", "HFRE");
    addStation("Kreiensen", "HK");
    addStation("Einbeck Salzderhelden", "HEB");
    addStation("Northeim (Han)", "HN");
    addStation("Nörten-Hardenberg", "HNTH");
    addStation("Göttigen", "HG");
    addStation("Buxtehude", "ABX");
    addStation("Horneburg", "AHOG");
    addStation("Stade", "AST");
    addStation("Hammah", "AHAM");
    addStation("Himmelpforten", "AHPF");
    addStation("Hechthausen", "AHEN");
    addStation("Hemmoor", "AHEM");
    addStation("Wingst", "AWG");
    addStation("Cadenberge", "ACD");
    addStation("Cuxhaven", "ACV");
    addStation("Hittfeld", "AHIF");
    addStation("Klecken", "AKC");
    addStation("Buchholz (Nordheide)", "ABLZ");
    addStation("Sprötze", "ASP");
    addStation("Tostedt", "ATST");
    addStation("Lauenbrück", "ALUB");
    addStation("Scheeßel", "ASL");
    addStation("Rotenburg (Wümme)", "AROG");
    addStation("Sottrum", "AS");
    addStation("Ottersberg (Han)", "AOBG");
    addStation("Sagehorn", "ASAG");
    addStation("Bremen-Oberneuland", "HBON");
    addStation("Bremen Hbf", "HB");
    addStation("Suderburg", "HSUD");
    addStation("Unterlüß", "HUNL");
    addStation("Eschede", "HESD");
    addStation("Celle", "HC");
    addStation("Großburgwedel", "HGBW");
    addStation("Isernhagen", "HIHG");
    addStation("Langenhagen Mitte", "HLGM");
  }

  /**
   * Finds the station with the given name.
   * 
   * @param name name to search for
   * @return matching station, or null
   */
  public Station findStationByName(String name) {
    return stations.stream().filter(station -> station.getName().equals(name)).findFirst()
      .orElse(null);
  }

  /**
   * Finds the station with the given code.
   * 
   * @param code code to search for
   * @return matching station, or null
   */
  public Station findStationByCode(String code) {
    return stations.stream().filter(station -> station.getCode().equals(code)).findFirst()
      .orElse(null);
  }

  public List<Station> getStations() {
    return stations;
  }

  private void addStation(String name, String code) {
    Station station = new Station(name, code);
    stations.add(station);
  }

}
