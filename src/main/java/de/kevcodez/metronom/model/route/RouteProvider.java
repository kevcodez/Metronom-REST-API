package de.kevcodez.metronom.model.route;

import de.kevcodez.metronom.model.stop.StationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * The route provider provides all routes statically.
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class RouteProvider {

  @Inject
  private StationProvider stationProvider;

  private List<Route> routes = new ArrayList<>();

  @PostConstruct
  public void constructRoutes() {
    addRouteElbeTakt();
    addRouteNordseeTakt();
    addRouteWeserTakt();
    addRouteAllerTakt();
    addRouteLeinetalTakt();
  }

  public List<Route> getRoutes() {
    return routes;
  }

  private void addRouteElbeTakt() {
    Route route = new Route("Elbe-Takt");
    route.getTrains().addAll(Arrays.asList(new String[] { "RB 31", "RE 3" }));

    String[] stations = { "Hamburg HBF", "Hamburg-Harburg", "Meckelfeld", "Maschen", "Stelle",
      "Aushausen", "Winsen (Luhe)", "Radbruch", "Bardowick", "Lüneburg", "Bienenbüttel",
      "Bad Bevensen", "Uelzen" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addStations(String[] stops, Route route) {
    Arrays.stream(stops).map(stationProvider::findStationByName).forEach(route.getStations()::add);
  }

  private void addRouteNordseeTakt() {
    Route route = new Route("Nordsee-Takt");
    route.getTrains().add("RE 5");

    String[] stations = { "Hamburg Hbf", "Hamburg-Harburg", "Buxtehude", "Horneburg", "Stade",
      "Hammah", "Himmepforten", "Hechthausen", "Hemmoor", "Wingst", "Cadenberge", "Cuxhaven" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addRouteWeserTakt() {
    Route route = new Route("Weser-Takt");
    route.getTrains().addAll(Arrays.asList(new String[] { "RB41", "RE 4" }));

    String[] stations = { "Hamburg Hbf", "Hamburg-Harburg", "Hittfeld", "Klecken",
      "Buchholz (Nordheide)", "Sprötze", "Tostedt", "Lauenbrück", "Scheeßel", "Rotenburg (Wümme)",
      "Sottrum", "Ottersberg (Han)", "Sagehorn", "Bremen-Oberneuland", "Bremen Hbf" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addRouteAllerTakt() {
    Route route = new Route("Aller-Takt");
    route.getTrains().add("RE 2");

    String[] stations = { "Uelzen", "Suderburg", "Unterlüß", "Eschede", "Celle", "Großburgwedel",
      "Isernhagen", "Langenhagen Mitte", "Hannover Hbf" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addRouteLeinetalTakt() {
    Route route = new Route("Leinetal-Takt");
    route.getTrains().add("RE 2");

    String[] stations = { "Hannover Hbf", "Sarstedt", "Nordstemmen", "Elze (Han)", "Banteln",
      "Alfeld (Leine)", "Freden (Leine)", "Kreiensen", "Einbeck Salzderhelden", "Northeim (Han)",
      "Nörten-Hardenberg", "Göttigen" };

    addStations(stations, route);
    routes.add(route);
  }

}
