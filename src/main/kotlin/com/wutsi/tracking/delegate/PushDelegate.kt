package com.wutsi.tracking.`delegate`

import com.wutsi.stream.EventStream
import com.wutsi.tracking.dto.PushTrackRequest
import com.wutsi.tracking.dto.PushTrackResponse
import com.wutsi.tracking.dto.Track
import com.wutsi.tracking.event.TrackProcessedEventPayload
import com.wutsi.tracking.event.TrackingEventType
import com.wutsi.tracking.service.pipeline.Pipeline
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
public class PushDelegate(
    private val pipeline: Pipeline,
    private val eventStream: EventStream
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(PushDelegate::class.java)
    }

    public fun invoke(request: PushTrackRequest): PushTrackResponse {
        LOGGER.info("processing event=${request.event} productId=${request.pid} user=${request.uid} page=${request.page}")
        val transactionId = UUID.randomUUID().toString()
        val track = Track(
            transactionId = transactionId,
            time = request.time,
            page = request.page,
            event = request.event,
            productId = request.pid,
            userId = request.uid,
            value = request.value,
            deviceId = request.duid,
            referer = request.referer,
            ip = request.ip,
            latitude = request.lat,
            longitude = request.long,
            userAgent = request.ua,
            hitId = request.hid,
            url = request.url
        )
        pipeline.process(track)
        eventStream.publish(TrackingEventType.TRACK_PROCESSED.urn, TrackProcessedEventPayload(track))
        return PushTrackResponse(
            transactionId = transactionId
        )
    }
}
