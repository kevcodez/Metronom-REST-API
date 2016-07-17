package de.kevcodez.metronom.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures a JAX-RS endpoint under {@code /api/v1}.
 *
 * @author Kevin Grüneberg
 */
@ApplicationPath("api/v1")
public class JAXRSConfiguration extends Application {

}
