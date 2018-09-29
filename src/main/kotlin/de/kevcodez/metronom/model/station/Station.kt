package de.kevcodez.metronom.model.station

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

/**
 * Represents a single station. The station also contains a list of alternative names to resolve the station more
 * easily. For example the station Hannover Hbf also has the alternative name Hannover.
 *
 * @author Kevin Grüneberg
 */
data class Station(
    val name: String,
    val code: String
) {

    @JsonIgnore
    val alternativeNames: MutableList<String> = ArrayList()

    fun addAlternativeNames(vararg names: String) {
        alternativeNames.addAll(Arrays.asList(*names))
    }

}
