package de.kevcodez.metronom.provider

import de.kevcodez.metronom.model.station.Station
import java.util.*

/**
 * Provides all stations statically.
 *
 * @author Kevin Grüneberg
 */
object StationProvider {

    private val stations = ArrayList<Station>()

    init {
        addStation("Hamburg", "AH", "Hamburg Hbf", "Hamburger Hauptbahnhof", "Hauptbahnhof Hamburg")
        addStation("Harburg", "AHAR", "Hamburg-Harburg", "Harburg", "Hamburg- Harburg", "Hamburg Harburg")
        addStation("Meckelfeld", "AMDH", "Meckelfeld HP")
        addStation("Maschen", "AMA")
        addStation("Stelle", "ASTE")
        addStation("Ashausen", "AASN")
        addStation("Winsen", "AWI", "Winsen (Luhe)")
        addStation("Radbruch", "ARH")
        addStation("Bardowick", "ABAD")
        addStation("Lüneburg", "ALBG", "Lueneburg")
        addStation("Bienenbüttel", "ABIL", "Bienenbuettel")
        addStation("Bevensen", "ABVS", "Bad Bevensen")
        addStation("Uelzen", "HU")
        addStation("Hannover", "HH", "Hannover Hbf")
        addStation("Sarstedt", "HSRD")
        addStation("Nordstemmen", "HNOS")
        addStation("Elze", "HELZ", "Elze (Han)")
        addStation("Banteln", "HBAN")
        addStation("Alfeld", "HALF", "Alfeld (Leine)")
        addStation("Freden", "HFRE", "Freden (Leine)")
        addStation("Kreiensen", "HK")
        addStation("Salzderhelden", "HEB", "Einbeck", "Einbeck Salzderhelden")
        addStation("Northeim", "HN", "Northeim (Han)")
        addStation("Nörten-Hardenberg", "HNTH", "Hardenberg", "Nörten", "Noerten-Hardenberg", "Noerten")
        addStation("Göttingen", "HG", "Goettingen", "Götingen")
        addStation("Buxtehude", "ABX")
        addStation("Horneburg", "AHOG")
        addStation("Stade", "AST")
        addStation("Hammah", "AHAM")
        addStation("Himmelpforten", "AHPF")
        addStation("Hechthausen", "AHEN")
        addStation("Hemmoor", "AHEM")
        addStation("Wingst", "AWG")
        addStation("Cadenberge", "ACD")
        addStation("Cuxhaven", "ACV")
        addStation("Hittfeld", "AHIF")
        addStation("Klecken", "AKC")
        addStation("Buchholz", "ABLZ", "Buchholz (Nordheide)")
        addStation("Sprötze", "ASP", "Sproetze")
        addStation("Tostedt", "ATST")
        addStation("Lauenbrück", "ALUB", "Lauenbrueck")
        addStation("Scheeßel", "ASL")
        addStation("Rotenburg", "AROG", "Rotenburg (Wümme)")
        addStation("Sottrum", "AS")
        addStation("Ottersberg", "AOBG", "Ottersberg (Han)")
        addStation("Sagehorn", "ASAG")
        addStation("Bremen-Oberneuland", "HBON", "Oberneuland", "Bremen Oberland")
        addStation("Bremen", "HB", "Bremen Hbf", "Bremen Hauptbahnhof")
        addStation("Suderburg", "HSUD")
        addStation("Unterlüß", "HUNL", "Unterlüss", "Unterlueß")
        addStation("Eschede", "HESD")
        addStation("Celle", "HC")
        addStation("Großburgwedel", "HGBW", "Grossburgwedel")
        addStation("Isernhagen", "HIHG")
        addStation("Langenhagen", "HLGM", "Langenhagen Mitte")
    }

    fun findStationByName(name: String?): Station? {
        return if (name == null) {
            null
        } else stations.firstOrNull { station ->
            stationNameMatches(name, station) || alternativeNameMatches(
                name,
                station
            )
        }
    }

    fun findStationByCode(code: String): Station {
        return stations.stream().filter { station -> station.code == code }.findFirst()
            .orElse(null)
    }

    fun getStations(): List<Station> {
        return Collections.unmodifiableList(stations)
    }

    private fun addStation(name: String, code: String, vararg names: String) {
        val station = Station(name, code)
        station.addAlternativeNames(*names)
        stations.add(station)
    }

    fun stationNameMatches(stationName: String, station: Station): Boolean {
        return station.name.replace(" ", "").equals(stationName.replace(" ", ""), ignoreCase = true)
    }

    fun alternativeNameMatches(stationName: String, station: Station): Boolean {
        return station.alternativeNames.any {
            it.replace(" ", "").equals(stationName.replace(" ", ""), ignoreCase = true)
        }
    }

}
