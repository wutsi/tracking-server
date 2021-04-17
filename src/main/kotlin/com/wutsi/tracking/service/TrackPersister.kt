package com.wutsi.tracking.service

import com.wutsi.storage.StorageService
import com.wutsi.tracking.domain.Track
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.UUID

@Service
class TrackPersister(
    private val writer: TrackWriter,
    private val storage: StorageService
) {
    fun persist(items: List<Track>) {
        if (items.isEmpty())
            return

        val out = ByteArrayOutputStream()
        writer.write(items, out)
        return persist(ByteArrayInputStream(out.toByteArray()))
    }

    private fun persist(input: InputStream) {
        val fmt = SimpleDateFormat("yyyy/MM/dd")
        fmt.timeZone = TimeZone.getTimeZone("UTC")

        val folder = fmt.format(Date())
        val file = UUID.randomUUID().toString() + ".csv"
        storage.store("$folder/$file", input, "text/csv", Int.MAX_VALUE)
    }
}
