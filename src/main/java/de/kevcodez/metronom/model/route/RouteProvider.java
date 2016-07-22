package de.kevcodez.metronom.model.route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * The route provider provides all routes statically.
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class RouteProvider {

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

    String[] stops = { "Hamburg HBF", "Hamburg-Harburg", "Meckelfeld", "Maschen", "Stelle",
      "Aushausen", "Winsen (Luhe)", "Radbruch", "Bardowick", "Lüneburg", "Bienenbüttel",
      "Bad Bevensen", "Uelzen" };

    Arrays.stream(stops).forEach(route.getStops()::add);
    routes.add(route);
  }

  private void addRouteNordseeTakt() {
    Route route = new Route("Nordsee-Takt");
    route.getTrains().add("RE 5");

    String[] stops = { "Hamburg Hbf", "Hamburg-Harburg", "Buxtehude", "Horneburg", "Stade",
      "Hammah", "Himmepforten", "Hechthausen", "Hemmoor", "Wingst", "Cadenberge", "Cuxhaven" };

    Arrays.stream(stops).forEach(route.getStops()::add);
    routes.add(route);
  }

  private void addRouteWeserTakt() {
    Route route = new Route("Weser-Takt");
    route.getTrains().addAll(Arrays.asList(new String[] { "RB41", "RE 4" }));

    String[] stops = { "Hamburg Hbf", "Hamburg-Harburg", "Hittfeld", "Klecken",
      "Buchholz (Nordheide)", "Sprötze", "Tostedt", "Lauenbrück", "Scheeßel", "Rotenburg (Wümme)",
      "Sottrum", "Ottersberg (Han)", "Sagehorn", "Bremen-Oberneuland", "Bremen Hbf" };

    Arrays.stream(stops).forEach(route.getStops()::add);
    routes.add(route);
  }

  private void addRouteAllerTakt() {
    Route route = new Route("Aller-Takt");
    route.getTrains().add("RE 2");

    String[] stops = { "Uelzen", "Suderburg", "Unterlüß", "Eschede", "Celle", "Großburgwedel",
      "Isernhagen", "Langenhagen Mitte", "Hannover Hbf" };

    Arrays.stream(stops).forEach(route.getStops()::add);
    routes.add(route);
  }

  private void addRouteLeinetalTakt() {
    Route route = new Route("Leinetal-Takt");
    route.getTrains().add("RE 2");

    String[] stops = { "Hannover Hbf", "Sarstedt", "Nordstemmen", "Elze (Han)", "Banteln",
      "Alfeld (Leine)", "Freden (Leine)", "Kreiensen", "Einbeck Salzderhelden", "Northeim (Han)",
      "Nörten-Hardenberg", "Göttigen" };

    Arrays.stream(stops).forEach(route.getStops()::add);
    routes.add(route);
  }

}
