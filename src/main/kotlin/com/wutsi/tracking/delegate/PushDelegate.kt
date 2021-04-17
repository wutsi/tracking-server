package com.wutsi.tracking.`delegate`

import com.wutsi.tracking.domain.Track
import com.wutsi.tracking.dto.PushTrackRequest
import com.wutsi.tracking.dto.PushTrackResponse
import com.wutsi.tracking.service.pipeline.Pipeline
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
public class PushDelegate(
    @Autowired private val pipeline: Pipeline
) {
    public fun invoke(request: PushTrackRequest): PushTrackResponse {
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
        return PushTrackResponse(
            transactionId = transactionId
        )
    }
}
