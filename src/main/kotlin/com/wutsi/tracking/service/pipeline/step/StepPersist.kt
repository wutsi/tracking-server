package com.wutsi.tracking.service.pipeline.step

import com.wutsi.tracking.dto.Track
import com.wutsi.tracking.service.TrackPersister
import com.wutsi.tracking.service.pipeline.Step
import java.util.Collections
import javax.annotation.PreDestroy

open class StepPersist(
    private val persister: TrackPersister,
    private val bufferSize: Int
) : Step {
    private val buffer = Collections.synchronizedList(mutableListOf<Track>())

    @PreDestroy
    fun destroy() {
        flush()
    }

    fun flush() {
        val copy = mutableListOf<Track>()
        copy.addAll(buffer)

        persister.persist(copy)
        buffer.removeAll(copy)
    }

    override fun process(track: Track) {
        buffer.add(track)
        if (shouldFlush()) {
            flush()
        }
    }

    private fun shouldFlush(): Boolean {
        return buffer.size >= bufferSize
    }
}
