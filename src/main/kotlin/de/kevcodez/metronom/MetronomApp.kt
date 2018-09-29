package de.kevcodez.metronom

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class MetronomApp

fun main(args: Array<String>) {
    runApplication<MetronomApp>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}