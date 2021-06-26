package com.wutsi.tracking.service

import com.opencsv.CSVWriter
import com.wutsi.tracking.dto.Track
import org.springframework.stereotype.Service
import java.io.OutputStream
import java.io.OutputStreamWriter

@Service
class TrackWriter {
    fun write(items: List<Track>, out: OutputStream) {
        val writer = OutputStreamWriter(out)
        val csv = CSVWriter(
            writer,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.DEFAULT_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END
        )
        csv.use {
            headers(csv)
            data(items, csv)
        }
    }

    private fun headers(csv: CSVWriter) {
        csv.writeNext(
            arrayOf(
                "time",
                "hitid",
                "deviceid",
                "userid",
                "page",
                "event",
                "productid",
                "value",
                "os",
                "osversion",
                "devicetype",
                "browser",
                "ip",
                "long",
                "lat",
                "traffic",
                "referer",
                "bot",
                "ua",
                "source",
                "medium",
                "campaign",
                "url",
                "siteid",
                "impressions"
            )
        )
    }

    private fun data(items: List<Track>, csv: CSVWriter) {
        items.forEach { data(it, csv) }
    }

    private fun data(track: Track, csv: CSVWriter) {
        csv.writeNext(
            arrayOf(
                string(track.time),
                string(track.hitId),
                string(track.deviceId),
                string(track.userId),
                string(track.page),
                string(track.event),
                string(track.productId),
                string(track.value),
                string(track.os.name),
                string(track.os.version),
                string(track.device.type),
                string(track.browser.name),
                string(track.ip),
                string(track.longitude),
                string(track.latitude),
                track.trafficType.name,
                string(track.referer),
                string(track.bot),
                string(track.userAgent),
                string(track.source),
                string(track.medium),
                string(track.campaign),
                string(track.url),
                string(track.siteid),
                string(track.impressions)
            )
        )
    }

    private fun string(s: Long?) = if (s == null) "" else s.toString()

    private fun string(s: Double?) = if (s == null) "" else s.toString()

    private fun string(s: String?) = if (s == null) "" else s

    private fun string(s: Boolean) = s.toString()
}
