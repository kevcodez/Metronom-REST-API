package de.kevcodez.metronom.utility

import org.springframework.stereotype.Component
import java.net.URL
import java.net.URLConnection
import java.util.stream.Collectors

@Component
class WebsiteSourceDownloader {

    fun getSource(websiteUrl: String, cookies: Map<String, String> = emptyMap()): String {
        val url = URL(websiteUrl)
        val urlConn = url.openConnection()

        if (!cookies.isEmpty()) {
            val cookie = cookies.entries.stream()
                .map { e -> e.key + "=" + e.value }
                .collect(Collectors.joining(";"))
            urlConn.setRequestProperty("Cookie", cookie)
        }

        return getContentFromUrlConn(urlConn)
    }

    private fun getContentFromUrlConn(urlConn: URLConnection): String {
        return urlConn.getInputStream().bufferedReader().use { it.readText() }
    }

    fun getCookiesFromUrl(uri: String): Map<String, String> {
        val url = URL(uri)
        val conn = url.openConnection()

        val headerFields = conn.headerFields

        val headerFieldsSet = headerFields.keys
        val hearerFieldsIter = headerFieldsSet.iterator()

        while (hearerFieldsIter.hasNext()) {
            val headerFieldKey = hearerFieldsIter.next()

            if ("Set-Cookie".equals(headerFieldKey, ignoreCase = true)) {
                val headerFieldValue = headerFields[headerFieldKey]
                return headerFieldValue!!.stream()
                    .map { header ->
                        header.split(";\\s*".toRegex())
                    }
                    .map { h ->
                        h[0].split("=".toRegex())
                    }
                    .collect(Collectors.toMap({ f -> f[0] }, { f -> f[1] }))
            }
        }

        return emptyMap()
    }

}
